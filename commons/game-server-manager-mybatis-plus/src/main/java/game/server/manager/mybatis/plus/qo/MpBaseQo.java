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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
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
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class MpBaseQo<T> {

    /**
     * between参数数组长度
     */
    protected static int arrayValueSize = 2;
    /**
     * 校验字段缓存
     */
    protected List<String> filedNames;
    /**
     * 校验字段的目标class类型
     */
    protected Class<T> clazz;
    /**
     * 分页对象
     */
    protected Page<T> page;
    /**
     * wrapper
     */
    protected QueryWrapper<T> wrapper;
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
    protected Map<String, Object> searchParam;
    /**
     * 查询参数类型配置
     */
    protected Map<String, String> searchConfig;

    /**
     * 初始化实例
     *
     * @param clazz clazz
     * @author laoyu
     * @date 2022/9/7
     */
    public void initInstance(Class<T> clazz){
        this.clazz = clazz;
        startPage();
        buildSearchWrapper();
        buildSelect();
        buildOrders();
    }

    /**
     * 构建分页对象
     *
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     * @author laoyu
     * @date 2022/9/7
     */
    public IPage<T> startPage() {
        page = Page.of(this.currentPage, this.pageSize);
        return page;
    }

    /**
     * 构建排序条件、
     * 使用基类提供的方法就必须符合规范、以免引起sql报错
     *
     * @author laoyu
     * @date 2022/9/5
     */
    private void buildOrders() {
        if (Objects.isNull(page)) {
            log.warn("this page is null,break buildOrders.");
            return;
        }
        if (Objects.isNull(wrapper)) {
            log.warn("this wrapper is null ,break buildSelect.");
            return;
        }
        if (Objects.isNull(orders) || orders.isEmpty()) {
            log.warn("this orders is null or empty,break buildOrders.");
            return;
        }
        //校验排序字段是否符合规范
        List<String> orderColumns = orders.stream().map(OrderItem::getColumn).toList();
        checkParamColumns(orderColumns);
        orders.forEach(orderItem -> {
            String column = orderItem.getColumn();
            if(orderItem.isAsc()){
                wrapper.orderByAsc(toSymbolCase(column));
            }else {
                wrapper.orderByDesc(toSymbolCase(column));
            }
        });
    }

    /**
     * 构建查询语句
     * 使用基类提供的方法就必须符合规范、以免引起sql报错
     *
     * @author laoyu
     * @date 2022/9/7
     */
    private void buildSelect() {
        if (Objects.isNull(columns) || columns.isEmpty()) {
            log.warn("this columns is null or empty,break buildSelect.");
            return;
        }
        if (Objects.isNull(wrapper)) {
            log.warn("this wrapper is null ,break buildSelect.");
            return;
        }
        checkParamColumns(columns);
        wrapper.select(columns.stream().map(this::toSymbolCase).toArray(String[]::new));
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
        wrapper = Wrappers.query();
        if (Objects.isNull(searchParam) || searchParam.isEmpty()) {
            log.info("this params is null or empty,break buildSearchWrapper.");
            return wrapper.lambda();
        }

        //校验字段是否符合规范
        checkParamColumns(searchParam.keySet());

        for (Map.Entry<String, Object> param : searchParam.entrySet()) {
            String column = param.getKey();
            Object value = param.getValue();
            if (CharSequenceUtil.isBlank(column) || Objects.isNull(value)) {
                log.info("{},{},column or value is null , break buildSearchParam.", column, value);
            }else if (Objects.isNull(searchConfig)) {
                log.info("{},{},searchConfig is not config , set default eq.", column, value);
                wrapper.eq(toSymbolCase(column), value);
            }else {
                String searChType = searchConfig.get(column);
                switch (SearchTypeEnum.matchValue(searChType)) {
                    case EQ -> wrapper.eq(toSymbolCase(column), value);
                    case NE -> wrapper.ne(toSymbolCase(column), value);
                    case IN -> wrapper.in(toSymbolCase(column), value);
                    case NOT_IN -> wrapper.notIn(toSymbolCase(column), value);
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
                    default -> {
                        log.info("{},{},searchType {} not statement , set default eq.", column, value, searChType);
                        wrapper.eq(toSymbolCase(column), value);
                    }
                }
            }
        }
        return wrapper.lambda();
    }

    /**
     * 校验参数字段是否合法
     *
     * @param columns columns
     * @author laoyu
     * @date 2022/9/5
     */
    public void checkParamColumns(Collection<String> columns) {
        if (Objects.nonNull(clazz)) {
            if (Objects.isNull(filedNames)) {
                Field[] fields = ClassUtil.getDeclaredFields(clazz);
                this.filedNames = Arrays.stream(fields).map(Field::getName).toList();
            }
            if (!CollUtil.containsAll(filedNames, columns)) {
                throw new BizException("具有非法字段");
            }
        }
    }

    private String toSymbolCase(String column) {
        return CharSequenceUtil.toSymbolCase(column, '_');
    }


}
