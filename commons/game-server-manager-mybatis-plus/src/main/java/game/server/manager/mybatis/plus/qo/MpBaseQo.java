package game.server.manager.mybatis.plus.qo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import game.server.manager.common.exception.BizException;
import game.server.manager.mybatis.plus.enums.SearchTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/20
 */

@Data
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MpBaseQo<E> {

    private Class<E> clazz;

    /**
     * 当前页
     */
    protected Integer currentPage = 1;

    /**
     * 每页显示条数
     */
    protected Integer pageSize = 10;

    /**
     * 自定义要查询的字段
     */

    protected List<String> columns;

    /**
     * 排序字段集合
     */
    protected List<OrderItem> orders;

    /**
     * 自定义查询参数
     */
    protected List<SearchParam> params;

    private static int arrayValueSize = 2;


    public <T> IPage<T> startPage() {
        Page<T> page = Page.of(this.currentPage, this.pageSize);
        buildOrders(page);
        return page;
    }

    /**
     * 构建排序条件、
     * 使用基类提供的方法就必须符合规范、以免引起sql报错
     *
     * @param page page
     * @author laoyu
     * @date 2022/9/5
     */
    private <T> void buildOrders(Page<T> page) {
        if (Objects.nonNull(orders) && !orders.isEmpty()) {
            //校验排序字段是否符合规范
            List<String> orderColumns = orders.stream().map(OrderItem::getColumn).toList();
            checkParamColumns(orderColumns);
            page.setOrders(orders.stream().peek(orderItem -> {
                String column = orderItem.getColumn();
                orderItem.setColumn(CharSequenceUtil.toSymbolCase(column, '_'));
            }).toList());
        }
    }

    /**
     * 构建Wrapper查询对象
     * 使用基类提供的方法就必须符合规范、以免引起sql报错
     *
     * @return com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<T>
     * @author laoyu
     * @date 2022/9/5
     */
    public <T> LambdaQueryWrapper<T> buildSearchWrapper() {
        QueryWrapper<T> wrapper = Wrappers.query();
        if (Objects.nonNull(params) && !params.isEmpty()) {
            List<String> searchParams = params.stream().map(SearchParam::getColumn).toList();
            checkParamColumns(searchParams);
            params.forEach(searchParam -> {
                String searChType = searchParam.getSearChType();
                String column = searchParam.getColumn();
                Object value = searchParam.getValue();
                switch (SearchTypeEnum.matchValue(searChType)) {
                    case EQ -> wrapper.eq(column, value);
                    case NE -> wrapper.ne(column, value);
                    case LT -> wrapper.lt(column, value);
                    case GT -> wrapper.gt(column, value);
                    case GE -> wrapper.ge(column, value);
                    case LE -> wrapper.le(column, value);
                    case BETWEEN -> {
                        if (value.getClass().isArray()) {
                            Object[] arrayValue = (Object[]) value;
                            if (arrayValue.length == arrayValueSize) {
                                wrapper.between(column, arrayValue[0], arrayValue[1]);
                            }
                        }
                        if (value instanceof Collection<?>) {
                            List<?> collectionValue = ((Collection<?>) value).stream().toList();
                            if (collectionValue.size() == 2) {
                                wrapper.between(column, collectionValue.get(0), collectionValue.get(1));
                            }
                        }
                    }
                    case NOT_BETWEEN -> {
                        if (value.getClass().isArray()) {
                            Object[] arrayValue = (Object[]) value;
                            if (arrayValue.length == arrayValueSize) {
                                wrapper.notBetween(column, arrayValue[0], arrayValue[1]);
                            }
                        }
                        if (value instanceof Collection<?>) {
                            List<?> collectionValue = ((Collection<?>) value).stream().toList();
                            if (collectionValue.size() == 2) {
                                wrapper.notBetween(column, collectionValue.get(0), collectionValue.get(1));
                            }
                        }
                    }
                    case LIKE -> wrapper.like(column, value);
                    case NOT_LIKE -> wrapper.notLike(column, value);
                    case LIKE_LEFT -> wrapper.likeLeft(column, value);
                    case LIKE_RIGHT -> wrapper.likeRight(column, value);
                    default -> {
                    }
                }
            });
        }
        buildSelect(wrapper);
        return wrapper.lambda();
    }

    /**
     * 校验参数字段是否合法
     *
     * @param columns columns
     * @author laoyu
     * @date 2022/9/5
     */
    private void checkParamColumns(List<String> columns){
        Field[] fields = ClassUtil.getDeclaredFields(clazz);
        List<String> filedNames = Arrays.stream(fields).map(Field::getName).toList();
        if (!CollUtil.containsAll(filedNames, columns)) {
            throw new BizException("具有非法字段");
        }
    }

    private <T> void buildSelect(QueryWrapper<T> wrapper) {
        if (Objects.nonNull(columns) && !columns.isEmpty()) {
            checkParamColumns(columns);
            wrapper.select(columns.stream().map(column -> CharSequenceUtil.toSymbolCase(column, '_')).toArray(String[]::new));
        }
    }

}
