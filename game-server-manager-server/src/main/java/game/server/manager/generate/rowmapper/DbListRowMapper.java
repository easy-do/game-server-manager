package game.server.manager.generate.rowmapper;

import game.server.manager.generate.vo.DbListVo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author laoyu
 * @version 1.0
 */
public class DbListRowMapper implements RowMapper<DbListVo> {
    @Override
    public DbListVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        DbListVo dbListVo = new DbListVo();
        dbListVo.setTableName(rs.getString("table_name"));
        dbListVo.setTableComment(rs.getString("table_comment"));
        dbListVo.setCreateTime(rs.getTimestamp("create_time"));
        dbListVo.setUpdateTime(rs.getTimestamp("update_time"));
        return dbListVo;
    }
}
