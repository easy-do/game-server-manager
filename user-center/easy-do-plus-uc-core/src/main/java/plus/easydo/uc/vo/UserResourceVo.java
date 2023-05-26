package plus.easydo.uc.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 用户资源
 * @date 2023/3/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResourceVo {

    /**
     * 权限编码
     */
    private String key;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 状态 0-启用（默认），1-禁用
     */
    private Integer status;
    /**
     * 显示顺序
     */
    private Long orderNumber;
    /**
     * 图标
     */
    private String icon;
    /**
     * 图标类型：0-URL（默认）、1-图片
     */
    private Integer iconType;
    /**
     * 路径
     */
    private String url;
    /**
     * 拓展数据
     */
    private Map<String,Object> extMap;
    /**
     * 路由集成模式：0-hash（默认），1-browser
     */
    private Integer routeMode;
    /**
     * 嵌入方式：0-iFame（默认）、1-微前端、2-弹出新窗口、3-页面自导航
     */
    private Integer embedType;
    /**
     * 内部子路由
     */
    private List<SubRouteVO> subRoutes;
    /**
     * 功能权限
     */
    private List<FunctionAuthVo> functionAuths;
    /**
     * 子节点
     */
    private List<UserResourceVo> children;

}
