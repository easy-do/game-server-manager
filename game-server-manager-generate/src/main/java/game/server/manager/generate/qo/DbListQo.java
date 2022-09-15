package game.server.manager.generate.qo;

import game.server.manager.generate.entity.GenTable;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import lombok.Data;


/**
 * @author yuzhanfeng
 * @Date 2022/9/15 16:28
 * @Description 查询数据库列表qo封装
 */
@Data
public class DbListQo extends MpBaseQo<GenTable> {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 数据源
     */
    private String dataSourceId;
}
