package plus.easydo.generate.rowmapper;


import plus.easydo.generate.entity.GenTableIndex;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author laoyu
 * @version 1.0
 */
public class GenTableIndexRowMapper implements RowMapper<GenTableIndex> {
    @Override
    public GenTableIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenTableIndex genTableIndex = new GenTableIndex();
        genTableIndex.setTableName(rs.getString("Table"));
        genTableIndex.setColumnName(rs.getString("COLUMN_NAME"));
        genTableIndex.setNonUnique(rs.getString("NON_UNIQUE"));
        genTableIndex.setIndexName(rs.getString("Key_name"));
        genTableIndex.setSeqInIndex(rs.getString("Seq_in_index"));
        genTableIndex.setCollation(rs.getString("COLLATION"));
        genTableIndex.setCardinality(rs.getString("CARDINALITY"));
        genTableIndex.setSubPart(rs.getString("SUB_PART"));
        genTableIndex.setPacked(rs.getString("PACKED"));
        genTableIndex.setIndexType(rs.getString("INDEX_TYPE"));
        genTableIndex.setComment(rs.getString("COMMENT"));
        genTableIndex.setIndexComment(rs.getString("INDEX_COMMENT"));
        return genTableIndex;
    }
}
