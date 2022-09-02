package game.server.manager.generate.rowmapper;

import game.server.manager.generate.entity.GenTable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author laoyu
 * @version 1.0
 */
public class GenTableRowMapper implements RowMapper<GenTable> {
    @Override
    public GenTable mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenTable genTable = new GenTable();
        genTable.setTableName(rs.getString("table_name"));
        genTable.setTableComment(rs.getString("table_comment"));
//        genTable.setCreateTime(rs.getTime("create_time"));
//        genTable.setUpdateTime(rs.getTime("update_time"));
        return genTable;
    }
}
