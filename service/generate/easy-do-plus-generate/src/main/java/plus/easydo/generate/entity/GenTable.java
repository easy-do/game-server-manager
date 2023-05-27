package plus.easydo.generate.entity;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import plus.easydo.generate.constant.GenConstants;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 业务表 gen_table
 *
 * @author ruoyi
 */
@Data
@TableName("gen_table")
public class GenTable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Map<String, Object> params;

    @TableId(type = IdType.AUTO)
    private Long tableId;

    /**
     * 数据源
     */
    private String dataSourceId;


    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空")
    private String tableName;

    /**
     * 表描述
     */
    @NotBlank(message = "表描述不能为空")
    private String tableComment;

    /**
     * 关联父表的表名
     */
    private String subTableName;

    /**
     * 本表关联父表的外键名
     */
    private String subTableFkName;

    /**
     * 实体类名称(首字母大写)
     */
    @NotBlank(message = "实体类名称不能为空")
    private String className;

    /**
     * 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
     */
    private String tplCategory;

    /**
     * 生成包路径
     */
    @NotBlank(message = "生成包路径不能为空")
    private String packageName;

    /**
     * 生成模块名
     */
    @NotBlank(message = "生成模块名不能为空")
    private String moduleName;

    /**
     * 生成业务名
     */
    @NotBlank(message = "生成业务名不能为空")
    private String businessName;

    /**
     * 生成功能名
     */
    @NotBlank(message = "生成功能名不能为空")
    private String functionName;

    /**
     * 生成作者
     */
    @NotBlank(message = "作者不能为空")
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 主键信息
     */
    @TableField(exist = false)
    private GenTableColumn pkColumn;

    /**
     * 子表信息
     */
    @TableField(exist = false)
    private GenTable subTable;

    /**
     * 表列信息
     */
    @Valid
    @TableField(exist = false)
    private List<GenTableColumn> columns;

    /**
     * 其它生成选项
     */
    private String options;

    /**
     * 树编码字段
     */
    @TableField(exist = false)
    private String treeCode;

    /**
     * 树父编码字段
     */
    @TableField(exist = false)
    private String treeParentCode;

    /**
     * 树名称字段
     */
    @TableField(exist = false)
    private String treeName;

    /**
     * 上级菜单ID字段
     */
    @TableField(exist = false)
    private String parentMenuId;

    /**
     * 上级菜单名称字段
     */
    @TableField(exist = false)
    private String parentMenuName;

    /**
     * 是否生成查询
     */
    private Integer isQuery;

    /**
     * 是否生成插入
     */
    private Integer isInsert;

    /**
     * 是否生成更新
     */
    private Integer isUpdate;

    /**
     * 是否生成删除
     */
    private Integer isRemove;

    /**
     * 使用的模板id
     */
    private String templateIds;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 索引集合
     */
    @TableField(exist = false)
    private List<GenTableIndex> index;

    /**
     * 索引名称集合
     */
    @TableField(exist = false)
    private List<String> indexNames;

    /**
     * 搜索值
     */
    @TableField(exist = false)
    private String searchValue;


    public static boolean isSub(String tplCategory) {
        return tplCategory != null && CharSequenceUtil.equals(GenConstants.TPL_SUB, tplCategory);
    }

    public static boolean isTree(String tplCategory) {
        return tplCategory != null && CharSequenceUtil.equals(GenConstants.TPL_TREE, tplCategory);
    }

    public static boolean isCrud(String tplCategory) {
        return tplCategory != null && CharSequenceUtil.equals(GenConstants.TPL_CRUD, tplCategory);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField) {
        if (isTree(tplCategory)) {
            return CharSequenceUtil.equalsAnyIgnoreCase(javaField,
                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
        }
        return CharSequenceUtil.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }

}
