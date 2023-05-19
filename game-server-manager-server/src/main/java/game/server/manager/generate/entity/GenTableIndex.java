package game.server.manager.generate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 索引信息数据库映射对象
 *
 * @author gebilaoyu
 * @date 2021-12-02 15:59:37
 */
@Data
@TableName("gen_table_index")
public class GenTableIndex {
    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 表名称
     */
    @TableField(value = "TABLE_NAME")
    private String tableName;

    /**
     * 该索引是否是唯一索引。若不是唯一索引，则该列的值为 1；若是唯一索引，则该列的值为 0
     */
    @TableField(value = "NON_UNIQUE")
    private String nonUnique;

    /**
     * 索引名称
     */
    @TableField(value = "INDEX_NAME")
    private String indexName;

    /**
     * 该列在索引中的位置
     */
    @TableField(value = "Seq_in_index")
    private String seqInIndex;

    /**
     * 索引的列字段
     */
    @TableField(value = "COLUMN_NAME")
    private String columnName;

    /**
     * 列以何种顺序存储在索引中。在 MySQL 中，升序显示值“A”（升序），若显示为 NULL，则表示无分类。
     */
    @TableField(value = "COLLATION")
    private String collation;

    /**
     * 索引中唯一值数目的估计值
     */
    @TableField(value = "CARDINALITY")
    private String cardinality;

    /**
     * 列中被编入索引的字符的数量。若列只是部分被编入索引，则该列的值为被编入索引的字符的数目；若整列被编入索引，则该列的值为 NULL
     */
    @TableField(value = "SUB_PART")
    private String subPart;

    /**
     * 关键字如何被压缩。若没有被压缩，值为 NULL
     */
    @TableField(value = "PACKED")
    private String packed;

    /**
     * 索引列中是否包含 NULL。若列含有 NULL，该列的值为 YES。若没有，则该列的值为 NO
     */
    @TableField(value = "NULLABLE")
    private String nullable;

    /**
     * 索引使用的类型和方法（BTREE、FULLTEXT、HASH、RTREE）
     */
    @TableField(value = "INDEX_TYPE")
    private String indexType;

    /**
     * 注释
     */
    @TableField(value = "COMMENT")
    private String comment;

    /**
     * 索引注释
     */
    @TableField(value = "INDEX_COMMENT")
    private String indexComment;

}
