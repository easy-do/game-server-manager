package game.server.manager.generate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import game.server.manager.common.exception.BizException;
import game.server.manager.generate.constant.GenConstants;
import game.server.manager.generate.dto.GenerateDatabaseDocDto;
import game.server.manager.generate.dto.JsonGenerateDto;
import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import game.server.manager.generate.entity.GenTableIndex;
import game.server.manager.generate.service.DataSourceDbService;
import game.server.manager.generate.service.GenTableService;
import game.server.manager.generate.service.GenerateService;
import game.server.manager.generate.service.TemplateManagementService;
import game.server.manager.generate.util.GenUtils;
import game.server.manager.generate.util.VelocityInitializer;
import game.server.manager.generate.util.VelocityUtils;
import game.server.manager.generate.util.WordPdfUtils;
import game.server.manager.generate.vo.DbListVo;
import game.server.manager.generate.vo.TemplateManagementVo;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * ????????????????????????
     *
     * @param templateIds templateIds
     * @return java.util.List
     * @author laoyu
     */
    Map<TemplateManagementVo, Template> getTemplatesForDb(String templateIds) {
        VelocityInitializer.initVelocityStringResourceLoader();
        if (CharSequenceUtil.isEmpty(templateIds)) {
            throw new BizException("500","???????????????");
        }
        String[] ids = templateIds.split(",");
        List<TemplateManagementVo> templateManagementVos = templateManagementService.selectByIds(ids);
        Map<TemplateManagementVo, Template> templates = Maps.newHashMapWithExpectedSize(templateManagementVos.size());
        //???????????????
        for (TemplateManagementVo vo : templateManagementVos) {
            String code = vo.getTemplateCode();
            if (CharSequenceUtil.isNotBlank(code)) {
                try {
                    //????????????????????????
                    String fileName = vo.getFileName();
                    StringResourceRepository repo = StringResourceLoader.getRepository();
                    repo.putStringResource(fileName, vo.getTemplateCode());
                    Template template = Velocity.getTemplate(fileName);
                    templates.put(vo, template);
                } catch (Exception e) {
                    throw new BizException("???????????????????????????????????????", e.getMessage());
                }
            }
        }
        return templates;
    }

    /**
     * ????????????
     *
     * @param tableId ?????????
     * @return ??????????????????
     */
    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // ???????????????
        GenTable table = genTableService.selectGenTableById(tableId);
        // ?????????????????????
        setSubTable(table);
        // ?????????????????????
        setPkColumn(table);
        VelocityContext context = VelocityUtils.prepareContext(table);
        String templateIds = table.getTemplateIds();
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateIds);
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            dataMap.put(vo.getTemplateName(), stringWriter.toString());
        }
        return dataMap;
    }

    /**
     * ??????????????????????????????
     *
     * @param ids ids
     * @return ??????
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
     * ?????????????????????????????????
     *
     * @param tableName ?????????
     */
    @Override
    public void generatorCode(String tableName) {
        // ???????????????
        GenTable table = genTableService.selectGenTableByName(tableName);
        // ?????????????????????
        setSubTable(table);
        // ?????????????????????
        setPkColumn(table);

        VelocityContext context = VelocityUtils.prepareContext(table);

        // ??????????????????
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
                throw new BizException("500","??????????????????,?????????" + table.getTableName());
            }
        }

    }

    /**
     * ????????????????????????????????????
     *
     * @param id id
     * @return ??????
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
     * ??????????????????????????????
     *
     * @param id id
     * @param zip       zip
     * @author laoyu
     */
    private void generatorCode(Serializable id, ZipOutputStream zip) {
        // ???????????????
        GenTable table = genTableService.selectGenTableById(Long.parseLong((String) id));

        // ?????????????????????
        setSubTable(table);
        // ?????????????????????
        setPkColumn(table);

        VelocityContext context = VelocityUtils.prepareContext(table);

        // ??????????????????
        String templateIds = table.getTemplateIds();
        Map<TemplateManagementVo, Template> templates = getTemplatesForDb(templateIds);
        for (Map.Entry<TemplateManagementVo, Template> entry : templates.entrySet()) {
            StringWriter stringWriter = new StringWriter();
            TemplateManagementVo vo = entry.getKey();
            Template template = entry.getValue();
            template.merge(context, stringWriter);
            try {
                // ?????????zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.generatePath(table, vo.getFilePath())));
                IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
                IOUtils.closeQuietly(stringWriter);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("??????????????????????????????" + table.getTableName(), e);
            }
        }

    }

    /**
     * ?????????????????????-zip
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
            //??????????????????
            return generateDataBaseDocZipByte(templateId, genTables);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("500","??????????????????????????????" + e.getMessage());
        }
    }

    /**
     * ?????????????????????-docx
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
     * ?????????????????????-pdf
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
                // ?????????zip
                JSONObject jsonContent = (JSONObject) JSON.toJSON(content);
                zip.putNextEntry(new ZipEntry(VelocityUtils.generatePath(vo.getFilePath(), jsonContent, fileNameField)));
                IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException("500","??????????????????" + e.getMessage());
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
                // ?????????zip
                String filePath = vo.getFilePath().replace("#{fileName}", key);
                zip.putNextEntry(new ZipEntry(filePath));
                IOUtils.write(stringWriter.toString(), zip, GenConstants.UTF8);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BizException("500","??????????????????" + e.getMessage());
            }
        }
    }

    /**
     * ????????????????????? docx???????????? byte??????
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
     * ????????????????????? docx??????????????????zip ?????? byte??????
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
            // ?????????zip
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
     * ?????????????????????????????????????????????
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
            // ???????????????
            List<GenTableColumn> genTableColumns = dataSourceDbService.selectDbTableColumnsByName(dataSourceId, tableName);
            for (int j = 0; j < genTableColumns.size(); j++) {
                GenTableColumn column = genTableColumns.get(j);
                GenUtils.initColumnField(column, table);
                genTableColumns.set(j, column);
            }
            table.setColumns(genTableColumns);
            // ?????????????????????
            setPkColumn(table);
            //??????????????????
            List<GenTableIndex> indexList = dataSourceDbService.selectIndexByTableName(dataSourceId, tableName);
            table.setIndex(indexList);
            table.setIndexNames(indexList.stream().map(GenTableIndex::getIndexName).distinct().collect(Collectors.toList()));
            genTables.set(i, table);
        }
    }

    /**
     * ?????????????????????
     *
     * @param table ???????????????
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
     * ?????????????????????
     *
     * @param table ???????????????
     */
    public void setSubTable(GenTable table) {
        String subTableName = table.getSubTableName();
        if (CharSequenceUtil.isNotEmpty(subTableName)) {
            table.setSubTable(genTableService.selectGenTableByName(subTableName));
        }
    }

}
