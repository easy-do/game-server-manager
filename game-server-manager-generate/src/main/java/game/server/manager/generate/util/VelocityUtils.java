package game.server.manager.generate.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSONObject;
import game.server.manager.generate.constant.GenConstants;
import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 模板工具类
 *
 * @author ruoyi
 */
public class VelocityUtils {
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";

    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /**
     * 默认上级菜单，系统工具
     */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 设置模板变量信息
     *
     * @param genTable genTable
     * @return 模板列表
     * @author laoyu
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();
        Integer isQuery = genTable.getIsQuery();
        Integer isInsert = genTable.getIsInsert();
        Integer isUpdate = genTable.getIsUpdate();
        Integer isRemove = genTable.getIsRemove();
        Integer isWrapper = genTable.getIsWrapper();
        Integer isManager = genTable.getIsManager();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", CharSequenceUtil.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", CharSequenceUtil.lowerFirst(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", CharSequenceUtil.upperFirst(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtil.date());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        velocityContext.put("isQuery", isQuery);
        velocityContext.put("isInsert", isInsert);
        velocityContext.put("isUpdate", isUpdate);
        velocityContext.put("isRemove", isRemove);
        velocityContext.put("isWrapper", isWrapper);
        velocityContext.put("isManager", isManager);

        setMenuVelocityContext(velocityContext, genTable);
        if (GenConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, genTable);
        }
        if (GenConstants.TPL_SUB.equals(tplCategory)) {
            setSubVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }


    /**
     * 设置模板变量信息
     *
     * @param genTables genTables
     * @return 模板列表
     * @author laoyu
     */
    public static VelocityContext prepareContext(List<GenTable> genTables) {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tables", genTables);
        return velocityContext;
    }


    public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);
        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
        }
    }

    public static void setSubVelocityContext(VelocityContext context, GenTable genTable) {
        GenTable subTable = genTable.getSubTable();
        String subTableName = genTable.getSubTableName();
        String subTableFkName = genTable.getSubTableFkName();
        String subClassName = genTable.getSubTable().getClassName();
        String subTableFkClassName = CharSequenceUtil.toCamelCase(subTableFkName);
        context.put("subTable", subTable);
        context.put("subTableName", subTableName);
        context.put("subTableFkName", subTableFkName);
        context.put("subTableFkClassName", subTableFkClassName);
        context.put("subTableFkclassName", CharSequenceUtil.lowerFirst(subTableFkClassName));
        context.put("subClassName", subClassName);
        context.put("subclassName", CharSequenceUtil.lowerFirst(subClassName));
        context.put("subImportList", getImportList(genTable.getSubTable()));
    }

    /**
     * 获取模板信息
     *
     * @param isManager   isManager
     * @param tplCategory tplCategory
     * @return java.util.List
     * @author laoyu
     */
    @Deprecated
    public static List<String> getTemplatePathList(Integer isManager, String tplCategory) {
        List<String> templates = new ArrayList<>();
        templates.add("vm/mp/java/entity.java.vm");
        templates.add("vm/mp/java/qo.java.vm");
        templates.add("vm/mp/java/vo.java.vm");
        templates.add("vm/mp/java/dto.java.vm");
        templates.add("vm/mp/java/mapper.java.vm");
        templates.add("vm/mp/java/controller.java.vm");
        templates.add("vm/mp/java/service.java.vm");
        templates.add("vm/mp/java/serviceImpl.java.vm");
        if (isManager == 1) {
            templates.add("vm/mp/java/manager.java.vm");
        }
        templates.add("vm/mp/xml/mapper.xml.vm");
        templates.add("vm/mp/sql/sql.vm");
        templates.add("vm/mp/js/api.js.vm");
        if (GenConstants.TPL_CRUD.equals(tplCategory)) {
            templates.add("vm/mp/vue/index.vue.vm");
        } else if (GenConstants.TPL_TREE.equals(tplCategory)) {
            templates.add("vm/mp/vue/index-tree.vue.vm");
        } else if (GenConstants.TPL_SUB.equals(tplCategory)) {
            templates.add("vm/mp/vue/index.vue.vm");
            templates.add("vm/mp/java/sub-domain.java.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     *
     * @param template template
     * @param genTable genTable
     * @return java.lang.String
     * @author laoyu
     */
    @Deprecated
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + CharSequenceUtil.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = CharSequenceUtil.format("{}/domain/{}.java", javaPath, className);
        }
        if (template.contains("entity.java.vm")) {
            fileName = CharSequenceUtil.format("{}/entity/{}.java", javaPath, className);
        } else if (template.contains("qo.java.vm")) {
            fileName = CharSequenceUtil.format("{}/qo/{}Qo.java", javaPath, className);
        } else if (template.contains("vo.java.vm")) {
            fileName = CharSequenceUtil.format("{}/vo/{}Vo.java", javaPath, className);
        } else if (template.contains("dto.java.vm")) {
            fileName = CharSequenceUtil.format("{}/dto/{}Dto.java", javaPath, className);
        }
        if (template.contains("sub-domain.java.vm") && CharSequenceUtil.equals(GenConstants.TPL_SUB, genTable.getTplCategory())) {
            fileName = CharSequenceUtil.format("{}/domain/{}.java", javaPath, genTable.getSubTable().getClassName());
        } else if (template.contains("mapper.java.vm")) {
            fileName = CharSequenceUtil.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = CharSequenceUtil.format("{}/service/I{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = CharSequenceUtil.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("manager.java.vm")) {
            fileName = CharSequenceUtil.format("{}/manager/{}Manager.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = CharSequenceUtil.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = CharSequenceUtil.format("{}/{}Mapper.xml", mybatisPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains("api.js.vm")) {
            fileName = CharSequenceUtil.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        } else if (template.contains("index.vue.vm")) {
            fileName = CharSequenceUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains("index-tree.vue.vm")) {
            fileName = CharSequenceUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return CharSequenceUtil.sub(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     *
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static Set<String> getImportList(GenTable genTable) {
        List<GenTableColumn> columns = genTable.getColumns();
        GenTable subGenTable = genTable.getSubTable();
        HashSet<String> importList = new HashSet<>();
        if (Objects.nonNull(subGenTable)) {
            importList.add("java.util.List");
        }
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn() && GenConstants.LOCAL_DATE_TIME.equals(column.getJavaType())) {
                importList.add("java.time.LocalDateTime");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return CharSequenceUtil.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(JSONObject paramsObj) {
        if (Objects.nonNull(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)) {
            return paramsObj.getString(GenConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
            return CharSequenceUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
        }
        return CharSequenceUtil.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            return CharSequenceUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        return CharSequenceUtil.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            return CharSequenceUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
        }
        return CharSequenceUtil.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeName = paramsObj.getString(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }

    /**
     * 生成路径
     *
     * @param table    table
     * @param filePath filePath
     * @return java.lang.String
     * @author laoyu
     */
    public static String generatePath(GenTable table, String filePath) {
        String packageName = table.getPackageName();
        String className = table.getClassName();
        String businessName = table.getBusinessName();
        String moduleName = table.getModuleName();
        if ("1".equals(table.getGenType())) {
            filePath = table.getGenPath() + File.separator + filePath;
        }
        filePath = filePath.replace("#{packageName}", packageName);
        filePath = filePath.replace("#{className}", className);
        filePath = filePath.replace("#{businessName}", businessName);
        filePath = filePath.replace("#{moduleName}", moduleName);
        return filePath;
    }

    /**
     * 生成路径
     *
     * @param filePath      filePath
     * @param Json          Json
     * @param fileNameFiled fileNameFiled
     * @return java.lang.String
     * @author laoyu
     */
    public static String generatePath(String filePath, JSONObject Json, String fileNameFiled) {
        String fileName = Json.getString(fileNameFiled);
        if (CharSequenceUtil.isNotBlank(fileName)) {
            filePath = filePath.replace("#{fileName}", fileName);
        }
        return filePath;
    }


}
