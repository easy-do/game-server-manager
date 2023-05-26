package plus.easydo.uc.dto;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 内部子路由
 * @date 2023/3/4
 */
@Data
public class SubRouteDto {

    /** 访问地址，可以是绝对路径或相对路径 */
    private String url;
     /** 路由集成模式：0-hash（默认），1-browser */
    private Integer routeMode;
     /** 嵌入方式：0-iFame、1-微前端（默认）、2-弹出新窗口、3-页面自导航" */
    private Integer embeddedType;

}
