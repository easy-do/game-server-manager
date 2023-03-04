package game.server.manager.uc.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 编辑菜单数据
 * @date 2023/3/4
 */
@Data
public class MenuModifyDto {


    /**
     * 主键ID"
     */
    @NotEmpty(message = "主键ID不能为空")
    private String id;


    /**
     * 操作人ID"
     */
    @NotEmpty(message = "操作人ID不能为空")
    private String operatorId;

    /**
     * 菜单名称", required = false
     */
    @Length(max = 60, message = "菜单名称长度不合法：最多{max}个字符")
    private String menuName;

    /**
     * 菜单编码", required = false
     */
    @Length(max = 60, message = "菜单编码长度不合法：最多{max}个字符")
    @Pattern(regexp = "^[-_0-9a-zA-Z.]*$", message = "菜单编码不合法：仅支持字母、数字、中划线、下划线和半角句号")
    private String menuCode;

    /**
     * 来源类型：0-自定义（默认），1-引用", required = false
     */
    private Integer sourceType;

    /**
     * 链接地址", required = false
     */
    @Length(max = 400, message = "链接地址长度不合法：最多{max}个字符")

    private String url;
    /**
     * 是否需要授权访问：1-是、0-否（默认）", required = false
     */
    private Integer authFlag;
    /**
     * 嵌入方式：0-iFame（默认）、1-微前端", required = false
     */
    private Integer embeddedType;
    /**
     * 主键图标类型：0-URL（默认）、1-图片ID", required = false
     */
    private Integer iconType;
    /**
     * 图标。图标类型是0时该值为图标URL，类型是1时该值为图片base64编码数据", required = false
     */
    private String icon;

    /**
     * 菜单描述", required = false
     */
    @Length(max = 400, message = "菜单描述长度不合法：最多{max}个字符")
    private String menuDesc;
    /**
     * 菜单排序", required = false
     */
    private Double menuOrder;
    /**
     * 路由集成模式：0-hash（默认），1-browser", required = false
     */
    private Integer routeMode;
    /**
     * 接口权限列表", required = false
     */
    private List<String> interfacePermissionList;
    /**
     * 内部子路由", required = false
     */
    private List<SubRouteDto> subRouteList;
    /**
     * 禁用标识，0-启用（默认），1-禁用", required = false
     */
    private Integer disabledFlag;

}
