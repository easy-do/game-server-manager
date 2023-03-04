package game.server.manager.uc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 菜单信息返回数据
 * @date 2023/3/4
 */
@Data
public class MenuDto {

    /** id */
    private String id;
    /**
     * "主键ID"
     */
    private String pid;
    /**
     * "菜单类型：0-菜单组（默认），1-菜单项，2-功能菜单"
     */
    private Integer menuType;
    /**
     * "菜单名称"
     */
    private String menuName;
    /**
     * "菜单编码"
     */
    private String menuCode;
    /**
     * "菜单id路径，以/开始以/结束，从根节点ID到当点节点ID，节点ID之间以/连接"
     */
    private String menuPath;
    /**
     * "来源类型：0-自定义（默认），1-引用"
     */
    private Integer sourceType;
    /**
     * "链接地址"
     */
    private String url;
    /**
     * "是否需要授权访问：1-是、0-否"
     */
    private Integer authFlag;
    /**
     * "嵌入方式：0-iFame（默认）、1-微前端"
     */
    private Integer embeddedType;
    /**
     * "主键图标类型：0-URL（默认）、1-图片ID"
     */
    private Integer iconType;
    /**
     * "图标。图标类型是0时该值为图标URL，类型是1时该值为图片base64编码数据"
     */
    private String icon;
    /**
     * "菜单描述"
     */
    private String menuDesc;
    /**
     * "菜单排序"
     */
    private Double menuOrder;
    /**
     * "路由集成模式：0-hash（默认），1-browser"
     */
    private Integer routeMode;
    /**
     * "接口权限列表"
     */
    private List<String> interfacePermissionList;
    /**
     * "内部子路由"
     */
    private List<SubRouteDto> subRouteList;
    /**
     * "禁用标识，0-启用（默认），1-禁用"
     */
    private Integer disabledFlag;

}
