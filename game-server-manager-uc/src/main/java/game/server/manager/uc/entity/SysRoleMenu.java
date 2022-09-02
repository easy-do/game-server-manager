package game.server.manager.uc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * 角色和菜单关联表
 * @author yuzhanfeng
 * @TableName sys_role_menu
 */
@TableName(value ="sys_role_menu")
@Data
@Builder
public class SysRoleMenu implements Serializable {


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}