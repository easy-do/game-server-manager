package game.server.manager.uc.vo;

import java.time.LocalDateTime;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统资源数据展示对象
 * 
 * @author yuzhanfeng
 * @date 2022-09-19 22:43:39
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SysResourceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 资源id路径，以/开始以/结束，从根节点ID到当点节点ID，节点ID之间以/连接
     */
    private String resourcePath;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源编码
     */
    private String resourceCode;

    /**
     * 资源类型 （M菜单 F功能）
     */
    private String resourceType;

    /**
     * 显示顺序
     */
    private Long orderNumber;

    /**
     * 状态 0-启用（默认），1-禁用
     */
    private Integer status;

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
     * 嵌入方式：0-iFame（默认）、1-微前端、2-弹出新窗口、3-页面自导航
     */
    private Integer embeddedType;

    /**
     * 路由集成模式：0-hash（默认），1-browser
     */
    private Integer routeMode;

    /**
     * 内部子路由
     */
    private String subRoutes;

    /**
     * 是否需要授权访问：1-是、0-否
     */
    private Integer authFlag;

    /**
     * 权限编码
     */
    private String permissions;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime updateTime;

    /**
     * 描述
     */
    private String resourceDesc;

    /**
     * 删除标记
     */
    private Long delFlag;

    /**
     * 子节点
     */
    List<SysResourceVo> children;


}
