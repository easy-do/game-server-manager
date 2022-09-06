package game.server.manager.mybatis.plus.qo;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import game.server.manager.mybatis.plus.enums.SearchTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/5/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MpBaseQo<T> {

    private static int arrayValueSize = 2;
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
     * 查询参数
     */
    protected Map<String, Object> params;
    /**
     * 查询参数类型配置
     */
    protected Map<String, String> searchConfig;

    public IPage<T> startPage() {
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
    private void buildOrders(Page<T> page) {
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
    public LambdaQueryWrapper<T> buildSearchWrapper() {
        QueryWrapper<T> wrapper = Wrappers.query();
        if (Objects.nonNull(params) && !params.isEmpty()) {
            checkParamColumns(params.keySet());
            params.forEach((column, value) -> {
                if (Objects.isNull(searchConfig)) {
                    wrapper.eq(toSymbolCase(column), value);
                } else {
                    String searChType = searchConfig.get(column);
                    switch (SearchTypeEnum.matchValue(searChType)) {
                        case EQ -> wrapper.eq(toSymbolCase(column), value);
                        case NE -> wrapper.ne(toSymbolCase(column), value);
                        case LT -> wrapper.lt(toSymbolCase(column), value);
                        case GT -> wrapper.gt(toSymbolCase(column), value);
                        case GE -> wrapper.ge(toSymbolCase(column), value);
                        case LE -> wrapper.le(toSymbolCase(column), value);
                        case BETWEEN -> {
                            if (value.getClass().isArray()) {
                                Object[] arrayValue = (Object[]) value;
                                if (arrayValue.length == arrayValueSize) {
                                    wrapper.between(toSymbolCase(column), arrayValue[0], arrayValue[1]);
                                }
                            }
                            if (value instanceof Collection<?>) {
                                List<?> collectionValue = ((Collection<?>) value).stream().toList();
                                if (collectionValue.size() == arrayValueSize) {
                                    wrapper.between(toSymbolCase(column), collectionValue.get(0), collectionValue.get(1));
                                }
                            }
                        }
                        case NOT_BETWEEN -> {
                            if (value.getClass().isArray()) {
                                Object[] arrayValue = (Object[]) value;
                                if (arrayValue.length == arrayValueSize) {
                                    wrapper.notBetween(toSymbolCase(column), arrayValue[0], arrayValue[1]);
                                }
                            }
                            if (value instanceof Collection<?>) {
                                List<?> collectionValue = ((Collection<?>) value).stream().toList();
                                if (collectionValue.size() == arrayValueSize) {
                                    wrapper.notBetween(toSymbolCase(column), collectionValue.get(0), collectionValue.get(1));
                                }
                            }
                        }
                        case LIKE -> wrapper.like(toSymbolCase(column), value);
                        case NOT_LIKE -> wrapper.notLike(toSymbolCase(column), value);
                        case LIKE_LEFT -> wrapper.likeLeft(toSymbolCase(column), value);
                        case LIKE_RIGHT -> wrapper.likeRight(toSymbolCase(column), value);
                        default -> wrapper.eq(toSymbolCase(column), value);
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
    private void checkParamColumns(Collection<String> columns) {
//        Field[] fields = ClassUtil.getDeclaredFields(clazz.getClass());
//        List<String> filedNames = Arrays.stream(fields).map(Field::getName).toList();
//        if (!CollUtil.containsAll(filedNames, columns)) {
//            throw new BizException("具有非法字段");
//        }
    }

    private void buildSelect(QueryWrapper<T> wrapper) {
        if (Objects.nonNull(columns) && !columns.isEmpty()) {
            checkParamColumns(columns);
            wrapper.select(columns.stream().map(column -> CharSequenceUtil.toSymbolCase(column, '_')).toArray(String[]::new));
        }
    }


    private String toSymbolCase(String column){
        return CharSequenceUtil.toSymbolCase(column, '_');
    }


}
