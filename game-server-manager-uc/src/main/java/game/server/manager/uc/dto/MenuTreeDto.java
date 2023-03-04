package game.server.manager.uc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 菜单树参数
 * @date 2023/3/4
 */
@Data
public class MenuTreeDto extends MenuDto{

   /** 下级列表 */
    private List<MenuTreeDto> children;


}
