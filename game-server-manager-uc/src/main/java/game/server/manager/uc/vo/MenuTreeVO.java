package game.server.manager.uc.vo;

import lombok.Data;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 菜单api信息
 * @date 2023/3/4
 */
@Data
public class MenuTreeVO extends MenuResultVO {

   /** 下级列表 */
    private List<MenuTreeVO> children;

}
