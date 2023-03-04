package game.server.manager.uc.qo;

import lombok.Builder;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 菜单查询条件
 * @date 2023/3/4
 */
@Data
@Builder
public class MenuTreeQo {

    /** 父级ID */
    private String pid;

    /** 菜单名称（模糊匹配） */
    private String menuName;

    /** 菜单编码 */
    private String menuCode;

    /** 是否需要授权访问：1-是、0-否 */
    private Integer authFlag;

    /** 嵌入方式：0-iFame（默认）、1-微前端 */
    private Integer embeddedType;

}
