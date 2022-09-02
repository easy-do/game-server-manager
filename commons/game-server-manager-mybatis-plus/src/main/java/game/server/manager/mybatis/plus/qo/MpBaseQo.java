package game.server.manager.mybatis.plus.qo;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
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
public class MpBaseQo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**当前页*/
    protected Integer currentPage = 1;

    /** 每页显示条数 */
    protected Integer pageSize = 10;

    /** 自定义要查询的字段*/
    protected List<String> columns;

    /** 排序字段集合*/
    protected List<OrderItem> orders;

    /** 自定义查询参数*/
    protected Map<String,Object> params;


    public <T> IPage<T> startPage(){
        Page<T> page = null;
        PageSupport pageSupport = SpringUtil.getBean(PageSupport.class);
        if(Objects.nonNull(pageSupport) && pageSupport.getEnable()){
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String pageNumFiled = pageSupport.getPageNumFiled();
            String pageSizeFiled = pageSupport.getPageSizeFiled();
            String current = request.getParameter(pageNumFiled);
            String size = request.getParameter(pageSizeFiled);
            if(Objects.nonNull(current) && Objects.nonNull(size)){
                page = new Page<>(Long.parseLong(current),Long.parseLong(size));
            }else {
                page = new Page<>(this.currentPage, this.pageSize);
            }
        }else {
            page = new Page<>(this.currentPage, this.pageSize);
        }
        buildOrders(page);
        return page;
    }

    private  <T> void buildOrders(Page<T> page){
        if(Objects.nonNull(orders) && !orders.isEmpty()){
            page.setOrders(orders.stream().peek(orderItem -> {
                String column = orderItem.getColumn();
                orderItem.setColumn(CharSequenceUtil.toSymbolCase(column, '_'));
            }).collect(Collectors.toList()));
        }
    }


    public <T> QueryWrapper<T> buildSearchWrapper(){
        QueryWrapper<T> wrapper = Wrappers.query();
        if(Objects.nonNull(params) && !params.isEmpty()){
            HashMap<String, Object> columMap = MapUtil.newHashMap(params.size());
            params.forEach((key,value)-> columMap.put(CharSequenceUtil.toSymbolCase(key, '_'),value));
            wrapper.allEq(columMap);
        }
        buildSelect(wrapper);
        return wrapper;
    }

    private  <T> void buildSelect(QueryWrapper<T> wrapper){
        if(Objects.nonNull(columns) && !columns.isEmpty()){
            wrapper.select(columns.stream().map(column->CharSequenceUtil.toSymbolCase(column, '_')).toArray(String[]::new));
        }
    }



}
