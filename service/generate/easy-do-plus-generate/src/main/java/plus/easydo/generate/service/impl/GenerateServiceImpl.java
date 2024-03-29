package plus.easydo.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import plus.easydo.common.enums.StatusEnum;
import plus.easydo.common.exception.BizException;
import plus.easydo.generate.constant.GenConstants;
import plus.easydo.generate.service.GenerateService;
import plus.easydo.generate.dto.GenerateDatabaseDocDto;
import plus.easydo.generate.dto.JsonGenerateDto;
import plus.easydo.generate.entity.GenTable;
import plus.easydo.generate.entity.GenTableColumn;
import plus.easydo.generate.entity.GenTableIndex;
import plus.easydo.generate.service.DataSourceDbService;
import plus.easydo.generate.service.GenTableService;
import plus.easydo.generate.service.TemplateManagementService;
import plus.easydo.generate.util.GenUtils;
import plus.easydo.generate.util.VelocityInitializer;
import plus.easydo.generate.util.VelocityUtils;
import plus.easydo.generate.util.WordPdfUtils;
import plus.easydo.generate.vo.CodePathTree;
import plus.easydo.generate.vo.CodePreviewVo;
import plus.easydo.generate.vo.DbListVo;
import plus.easydo.generate.vo.TemplateManagementVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.TreeNode;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/1/17
 */
@Service
public class GenerateServiceImpl implements GenerateService {

    private static final Logger log = LoggerFactory.getLogger(GenerateServiceImpl.class);

    @Autowired
    private TemplateManagementService templateManagementService;

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private DataSourceDbService dataSourceDbService;


    /**
     * 从数据库获取模板
     *
     * @param templateIds templateIds
     * @return java.util.List
     * @author laoyu
     */
    Map<TemplateManagementVo, Template> getTemplatesForDb(String templateIds) {
        VelocityInitializer.initVelocityStringResourceLoader();
        if (CharSequenceUtil.isEmpty(templateIds)) {
            throw new BizException("500","未选择模板");
        }
        String[] ids = templateIds.split(",");
        List<TemplateManagementVo> templateManagementVos = templateManagementService.selectByIds(ids);
        Map<TemplateManagementVo, Template> templates = Maps.newHashMapWithExpectedSize(templateManagementVos.size());
        //临时文件夹
        for (TemplateManagementVo vo : templateManagementVos) {
            String code = vo.getTemplateCode();
            if (CharSequenceUtil.isNotBlank(code)) {
                try {
                    //生成临时模板文件
                    String fileName = vo.getFileName();
                    StringResourceRepository repo = StringResourceLoader.getRepository();
                    repo.putStringResource(fileName, vo.getTemplateCode());
                    Template template = Velocity.getTemplate(fileName);
                    templates.put(vo, template);
                } catch (Exception e) {
                    throw new BizException("从数据库读取模板文件异常：", e.getMessage());
                }
            }
        }
        return templates;
    }

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    @Override
    public CodePreviewVo previewCode(Long tableId) {
        Map<String, CodePreviewVo> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = genTableService.selectGenTableById(tableId);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);
        VelocityContext context = VelocityUtils.prepareContext(table);
        String templateIds = table.getTemplateIds();
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateIds);
        List<CodePreviewVo.Code> codeList = new ArrayList<>(templates.size());
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo templateManagementVo = entry.getKey();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            String filePath = VelocityUtils.generatePath(table, templateManagementVo.getFilePath());
            List<String> paths = CharSequenceUtil.split(filePath, "/");
            String fileName = paths.get(paths.size()-1);
            CodePreviewVo.Code code = CodePreviewVo.Code.builder()
                    .templateName(templateManagementVo.getTemplateName())
                    .templateType(templateManagementVo.getTemplateType())
                    .filePath(filePath)
                    .fileName(fileName)
                    .code(stringWriter.toString()).build();
            codeList.add(code);
        }
        return CodePreviewVo.builder().filePathTree(buildFilePathTree(codeList)).codes(codeList).build();
    }

    /**
     * 构建预览代码的文件夹树结构
     *
     * @param codeList codeList
     * @return java.util.List<cn.hutool.core.lang.tree.Tree<java.lang.Integer>>
     * @author laoyu
     * @date 2023/5/28
     */
    private List<Tree<Integer>> buildFilePathTree(List<CodePreviewVo.Code> codeList) {
        Map<String,CodePathTree> treeKeyMap = new HashMap<>();
        List<CodePathTree> allTree = new ArrayList<>();
        Integer currentMaxId = 1;
        //先加入根节点
        allTree.add(CodePathTree.builder().id(currentMaxId).parentId(0).path("/").build());
        //构建tree集合
        for (int i = 0; i < codeList.size(); i++) {
            CodePreviewVo.Code code = codeList.get(i);
            List<String> paths = CharSequenceUtil.split(code.getFilePath(), "/");
            for (int j = 0; j < paths.size(); j++) {
                String path = paths.get(j);
                //如果已经存在则不再加入
                if(!treeKeyMap.containsKey(path)){
                    Integer parentId;
                    if(j == 0){
                        //如果是根目录第一个设计父节点为更目录
                        parentId = 1;
                    }else {
                        //查找上一级目录
                        String parentPath = paths.get(j - 1);
                        CodePathTree parentTree = treeKeyMap.get(parentPath);
                        parentId = parentTree.getId();
                    }
                    currentMaxId++;
                    //存储信息
                    CodePathTree tree = CodePathTree.builder().id(currentMaxId).parentId(parentId).path(path).build();
                    treeKeyMap.put(path,tree);
                    allTree.add(tree);
                }
            }
        }

        NodeParser<CodePathTree, Integer> nodeParser = (codePathTree, treeNode) -> {
            treeNode.setId(codePathTree.getId());
            treeNode.setParentId(codePathTree.getParentId());
            treeNode.setName(codePathTree.getPath());
            treeNode.putExtra("title", codePathTree.getPath());
            treeNode.putExtra("key", codePathTree.getPath());;
        };

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        return TreeUtil.build(allTree, 1, treeNodeConfig, nodeParser);
    }

    /**
     * 生成代码（下载方式）
     *
     * @param ids ids
     * @return 数据
     */
    @Override
    public byte[] downloadCode(String ids) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        String[] idsArray = ids.split(",");
        for (int i = 0; i < idsArray.length; i++) {
            generatorCode(idsArray[i], zip);
        }
        try {
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     */
    @Override
    public void generatorCode(String tableName) {
        // 查询表信息
        GenTable table = genTableService.selectGenTableByName(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        String templateIds = table.getTemplateIds();
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateIds);
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            try {
                String path = VelocityUtils.generatePath(table, vo.getFilePath());
                FileUtils.writeStringToFile(new File(path), stringWriter.toString(), GenConstants.UTF8);
            } catch (IOException e) {
                throw new BizException("500","渲染模板失败,表名：" + table.getTableName());
            }
        }

    }

    /**
     * 批量生成代码（下载方式）
     *
     * @param id id
     * @return 数据
     */
    @Override
    public byte[] downloadCode(Long id) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
            generatorCode(id, zip);
        try {
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     *
     * @param id id
     * @param zip       zip
     * @author laoyu
     */
    private void generatorCode(Serializable id, ZipOutputStream zip) {
        // 查询表信息
        GenTable table = genTableService.selectGenTableById(Long.parseLong((String) id));

        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        String templateIds = table.getTemplateIds();
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateIds);
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.generatePath(table, vo.getFilePath())));
                IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
                IOUtils.closeQuietly(stringWriter);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }

    }

    /**
     * 生成数据库文档-zip
     *
     * @param response response
     * @param dto      dto
     * @return byte[]
     * @author laoyu
     */
    @Override
    public byte[] generateDatabaseDoc(HttpServletResponse response, GenerateDatabaseDocDto dto) {
        String dataSourceId = dto.getDataSourceId();
        String templateId = dto.getTemplateId();
        String tables = dto.getTables();
        List<DbListVo> dbList = dataSourceDbService.selectDbTableListByNames(dataSourceId, tables.split(","));
        try {
            List<GenTable> genTables = BeanUtil.copyToList(dbList, GenTable.class);
            processTableListForDataBase(dataSourceId, genTables);
            //开始生成文档
            return generateDataBaseDocZipByte(templateId, genTables);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("500","生成数据库文档失败：" + e.getMessage());
        }
    }

    /**
     * 预览数据库文档-docx
     *
     * @param response response
     * @param dto      dto
     * @author laoyu
     */
    @Override
    public byte[] generateDataBaseDocx(HttpServletResponse response, GenerateDatabaseDocDto dto) {
        try {
            String dataSourceId = dto.getDataSourceId();
            String templateId = dto.getTemplateId();
            String tables = dto.getTables();
            List<DbListVo> dbList = dataSourceDbService.selectDbTableListByNames(dataSourceId, tables.split(","));
            List<GenTable> genTables = BeanUtil.copyToList(dbList, GenTable.class);
            processTableListForDataBase(dataSourceId, genTables);
            return generateDataBaseDocByte(templateId, genTables);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 预览数据库文档-pdf
     *
     * @param response response
     * @param dto      dto
     * @return byte[]
     * @author laoyu
     */
    @Override
    public byte[] showDatabasePdf(HttpServletResponse response, GenerateDatabaseDocDto dto) {
        byte[] wordByte = generateDataBaseDocx(response, dto);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(wordByte);
        WordPdfUtils.toPdf(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] jsonGenerate(HttpServletResponse response, JsonGenerateDto dto) {
        JSONObject contentJson = dto.getContentJson();
        JSONArray contentArray = dto.getContentArray();
        String templateId = dto.getTemplateId();
        String fileNameField = dto.getFileNameField();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateId);
        VelocityContext velocityContext = new VelocityContext();
        if (Objects.nonNull(contentArray)) {
            velocityContext.put("context", contentArray);
            contentArray.forEach(value -> {
                velocityContext.put("content", value);
                execJsonTemplateForArray(value, fileNameField, zip, templates, velocityContext);
            });
        }
        if (Objects.nonNull(contentJson)) {
            velocityContext.put("context", contentJson);
            contentJson.forEach((key, value) -> {
                velocityContext.put("key", key);
                velocityContext.put("value", value);
                execJsonTemplateForJson(key, value, zip, templates, velocityContext);
            });
        }
        try {
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private void execJsonTemplateForArray(Object content, String fileNameField, ZipOutputStream zip, Map<TemplateManagementVo, Template> templates, VelocityContext velocityContext) {
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(velocityContext, stringWriter);
            try {
                // 添加到zip
                JSONObject jsonContent = (JSONObject) JSON.toJSON(content);
                zip.putNextEntry(new ZipEntry(VelocityUtils.generatePath(vo.getFilePath(), jsonContent, fileNameField)));
                IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException("500","渲染模板失败" + e.getMessage());
            }
        }
    }

    private void execJsonTemplateForJson(String key, Object content, ZipOutputStream zip, Map<TemplateManagementVo, Template> templates, VelocityContext velocityContext) {
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(velocityContext, stringWriter);
            try {
                // 添加到zip
                String filePath = vo.getFilePath().replace("#{fileName}", key);
                zip.putNextEntry(new ZipEntry(filePath));
                IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException("500","渲染模板失败" + e.getMessage());
            }
        }
    }

    /**
     * 生成数据库文档 docx文件返回 byte数组
     *
     * @param templateId templateId
     * @param genTables  genTables
     * @return byte[]
     * @author laoyu
     */
    private byte[] generateDataBaseDocByte(String templateId, List<GenTable> genTables) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        VelocityContext context = VelocityUtils.prepareContext(genTables);
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateId);
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            IOUtils.write(stringWriter.toString(), outputStream, GenConstants.UTF8);
            return outputStream.toByteArray();
        }
        return outputStream.toByteArray();
    }

    /**
     * 生成数据库文档 docx文件并打包为zip 返回 byte数组
     *
     * @param templateId templateId
     * @param genTables  genTables
     * @return byte[]
     * @author laoyu
     */
    private byte[] generateDataBaseDocZipByte(String templateId, List<GenTable> genTables) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        VelocityContext context = VelocityUtils.prepareContext(genTables);
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateId);
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            // 添加到zip
            zip.putNextEntry(new ZipEntry(vo.getFileName().replace(".vm", "")));
            IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
            IOUtils.closeQuietly(stringWriter);
            zip.flush();
            zip.closeEntry();
            IOUtils.closeQuietly(zip);
        }
        return outputStream.toByteArray();
    }

    /**
     * 处理生成数据库文档所需的表信息
     *
     * @param dataSourceId dataSourceId
     * @param genTables    genTables
     * @author laoyu
     */
    private void processTableListForDataBase(String dataSourceId, List<GenTable> genTables) {
        for (int i = 0; i < genTables.size(); i++) {
            GenTable table = genTables.get(i);
            String tableName = table.getTableName();
            GenUtils.initTable(table, "gebilaoyu");
            // 处理列信息
            List<GenTableColumn> genTableColumns = dataSourceDbService.selectDbTableColumnsByName(dataSourceId, tableName);
            for (int j = 0; j < genTableColumns.size(); j++) {
                GenTableColumn column = genTableColumns.get(j);
                GenUtils.initColumnField(column, table);
                genTableColumns.set(j, column);
            }
            table.setColumns(genTableColumns);
            // 设置主键列信息
            setPkColumn(table);
            //获取索引信息
            List<GenTableIndex> indexList = dataSourceDbService.selectIndexByTableName(dataSourceId, tableName);
            table.setIndex(indexList);
            table.setIndexNames(indexList.stream().map(GenTableIndex::getIndexName).distinct().collect(Collectors.toList()));
            genTables.set(i, table);
        }
    }

    /**
     * 设置主键列信息
     *
     * @param table 业务表信息
     */
    public void setPkColumn(GenTable table) {
        for (GenTableColumn column : table.getColumns()) {
            if (column.isPk()) {
                table.setPkColumn(column);
                break;
            }
        }
        if (Objects.isNull(table.getPkColumn())) {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
            for (GenTableColumn column : table.getSubTable().getColumns()) {
                if (column.isPk()) {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (Objects.isNull(table.getSubTable().getPkColumn())) {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    /**
     * 设置主子表信息
     *
     * @param table 业务表信息
     */
    public void setSubTable(GenTable table) {
        String subTableName = table.getSubTableName();
        if (CharSequenceUtil.isNotEmpty(subTableName)) {
            table.setSubTable(genTableService.selectGenTableByName(subTableName));
        }
    }

}
