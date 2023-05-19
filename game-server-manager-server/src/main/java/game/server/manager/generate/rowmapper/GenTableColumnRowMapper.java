package game.server.manager.generate.rowmapper;

import game.server.manager.generate.entity.GenTableColumn;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author laoyu
 * @version 1.0
 */
public class GenTableColumnRowMapper implements RowMapper<GenTableColumn> {
    @Override
    public GenTableColumn mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setColumnName(rs.getString("column_name"));
        genTableColumn.setIsRequired(rs.getString("is_required"));
        genTableColumn.setIsPk(rs.getString("is_pk"));
        genTableColumn.setSort(rs.getInt("sort"));
        genTableColumn.setColumnComment(rs.getString("column_comment"));
        genTableColumn.setIsIncrement(rs.getString("is_increment"));
        genTableColumn.setColumnType(rs.getString("column_type"));
        genTableColumn.setDefaultValue(rs.getString("column_default"));
        genTableColumn.setMaximumLength(rs.getString("character_maximum_length"));
        return genTableColumn;
    }
}
