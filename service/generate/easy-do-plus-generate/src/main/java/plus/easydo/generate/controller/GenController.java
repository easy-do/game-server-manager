package plus.easydo.generate.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.generate.entity.GenTable;
import plus.easydo.generate.entity.GenTableColumn;
import plus.easydo.generate.qo.DbListQo;
import plus.easydo.generate.service.DataSourceDbService;
import plus.easydo.generate.service.GenTableService;
import plus.easydo.generate.service.GenerateService;
import plus.easydo.generate.util.WordPdfUtils;
import plus.easydo.generate.dto.GenerateDatabaseDocDto;
import plus.easydo.generate.dto.JsonGenerateDto;
import plus.easydo.generate.service.GenTableColumnService;
import plus.easydo.generate.vo.CodePreviewVo;
import plus.easydo.generate.vo.DbListVo;
import plus.easydo.dao.qo.MpBaseQo;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 *
 * @author laoyu
 */
@RequestMapping("/genTable")
@RestController
public class GenController {

    private static final String OCTET_STREAM = "application/octet-stream; charset=UTF-8";

    private static final String PDF = "application/pdf; charset=UTF-8";

    private static final String MSWORD = "application/maword; charset=UTF-8";

    @Autowired
    private GenTableService genTableService;

    @Autowired
    private GenTableColumnService genTableColumnService;

    @Autowired
    private DataSourceDbService dataSourceDbService;

    @Autowired
    private GenerateService generateService;

    /**
     * 查询代码生成列表
     *
     * @param genTable genTable
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping("/list")
    public R<Object> genList(GenTable genTable) {
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return DataResult.ok(list);
    }

    /**
     * 分页查询代码生成列表
     *
     * @param mpBaseQo mpBaseQo
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @PostMapping("/page")
    public MpDataResult page(@RequestBody MpBaseQo<GenTable> mpBaseQo) {
        IPage<GenTable> page = genTableService.pageGenTableList(mpBaseQo);
        return MpResultUtil.buildPage(page);
    }

    /**
     * 获取表详情
     *
     * @param tableId talbleId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping(value = "/info/{tableId}")
    public R<GenTable> getInfo(@PathVariable Long tableId) {
        GenTable table = genTableService.selectGenTableById(tableId);
        return DataResult.ok(table);
    }

    /**
     * 查询数据库列表
     *
     * @param dbListQo dbListQo
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @PostMapping("/db/list")
    public MpDataResult dataList(@RequestBody DbListQo dbListQo) {
        List<DbListVo> list = dataSourceDbService.selectDbTableList(dbListQo);
        long total = dataSourceDbService.countDbTableList(dbListQo);
        MpDataResult result = MpResultUtil.empty();
        result.setData(list);
        result.setCount(list.size());
        result.setTotal(total);
        result.setCurrentPage(dbListQo.getCurrentPage());
        result.setPages(dbListQo.getPageSize());
        return result;
    }

    /**
     * 查询数据表字段列表
     *
     * @param tableId tableId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping(value = "/column/{talbleId}")
    public R<Object> columnList(Long tableId) {
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        return DataResult.ok(list);
    }

    /**
     * 导入表结构（保存)
     *
     * @param tables       tables
     * @param dataSourceId dataSourceId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @PostMapping("/importTable")
    public R<Object> importTableSave(String tables, String dataSourceId) {
        String[] tableNames = tables.split(",");
        // 查询表信息
        List<DbListVo> tableList = dataSourceDbService.selectDbTableListByNames(dataSourceId, tableNames);
        genTableService.importGenTable(dataSourceId, tableList);
        return DataResult.ok();
    }

    /**
     * 修改保存代码生成业务
     *
     * @param genTable genTable
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @PostMapping("/update")
    public R<Object> editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return DataResult.ok();
    }

    /**
     * 删除代码生成
     *
     * @param tableId tableId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping("/delete/{tableId}")
    public R<Object> remove(@PathVariable Long tableId) {
        genTableService.deleteGenTableByIds(tableId);
        return DataResult.ok();
    }

    /**
     * 预览代码
     *
     * @param tableId tableId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping("/preview/{tableId}")
    public R<CodePreviewVo> preview(@PathVariable("tableId") Long tableId) {
        CodePreviewVo codePreviewVo = generateService.previewCode(tableId);
        return DataResult.ok(codePreviewVo);
    }

    /**
     * 生成代码（下载方式）
     *
     * @param response  response
     * @param tableName tableName
     * @throws IOException IOException
     * @author laoyu
     */
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = generateService.downloadCode(tableName);
        genCode(response, data, OCTET_STREAM, tableName + ".zip");
    }

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName tableName
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping("/genCode/{tableName}")
    public R<Object> genCode(@PathVariable("tableName") String tableName) {
        generateService.generatorCode(tableName);
        return DataResult.ok();
    }

    /**
     * 同步数据库
     *
     * @param tableName tableName
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping("/synchDb/{tableName}")
    public R<Object> synchDb(@PathVariable("tableName") String tableName) {
        genTableService.syncDb(tableName);
        return DataResult.ok();
    }

    /**
     * 批量生成代码
     *
     * @param response response
     * @param ids   ids
     * @throws IOException IOException
     * @author laoyu
     */
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, @RequestParam("ids") String ids) throws IOException {
        byte[] data = generateService.downloadCode(ids);
        genCode(response, data, OCTET_STREAM, ids + ".zip");
    }

    /**
     * 生成zip文件
     *
     * @param response response
     * @param data     data
     * @param fileName fileName
     * @throws IOException IOException
     * @author laoyu
     */
    private void genCode(HttpServletResponse response, byte[] data, String contentType, String fileName) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType(contentType);
        IoUtil.write(response.getOutputStream(),true, data);
    }

    /**
     * 生成数据库文档-zip
     *
     * @param response response
     * @param dto      dto
     * @author laoyu
     */
    @PostMapping("/generateDatabaseDocZip")
    public void generateDatabaseDoc(HttpServletResponse response, @RequestBody GenerateDatabaseDocDto dto) throws IOException {
        byte[] data = generateService.generateDatabaseDoc(response, dto);
        genCode(response, data, OCTET_STREAM, "databaseDoc.zip");
    }

    /**
     * 生成数据库文档-docx
     *
     * @param response response
     * @param dto      dto
     * @author laoyu
     */
    @PostMapping("/generateDataBaseDocx")
    public void previewDataBaseDoc(HttpServletResponse response, @RequestBody GenerateDatabaseDocDto dto) throws IOException {
        byte[] docByte = generateService.generateDataBaseDocx(response, dto);
        genCode(response, docByte, MSWORD, "databaseDoc.docx");
    }

    /**
     * 预览数据库文档-pdf
     *
     * @param response response
     * @param tables tables
     * @param dataSourceId dataSourceId
     * @param templateId templateId
     * @author laoyu
     */
    @GetMapping("/showDatabasePdf")
    public void showDatabasePdf(HttpServletResponse response,
                                @RequestParam(name = "tables") String tables,
                                @RequestParam(name = "dataSourceId") String dataSourceId,
                                @RequestParam(name = "templateId") Long templateId) throws IOException {
        GenerateDatabaseDocDto dto = new GenerateDatabaseDocDto();
        dto.setDataSourceId(dataSourceId);
        dto.setTables(tables);
        dto.setTemplateId(String.valueOf(templateId));
        byte[] docByte = generateService.showDatabasePdf(response, dto);
        genCode(response, docByte, PDF, "databaseDoc.pdf");
    }

    /**
     * word转pdf
     *
     * @param response response
     * @param file file
     * @author laoyu
     */
    @GetMapping("/wordToPdf")
    public void wordToPdf(HttpServletResponse response, @RequestParam(name = "file") MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        ServletOutputStream op = response.getOutputStream();
        WordPdfUtils.toPdf(in, op);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=R.pdf");
        response.setContentType(PDF);
    }


    /**
     * 根据http接口结果生成
     *
     * @param response response
     * @param dto      dto
     * @author laoyu
     */
    @PostMapping("/jsonGenerate")
    public void jsonGenerate(HttpServletResponse response, @RequestBody JsonGenerateDto dto) throws IOException {
        byte[] data = generateService.jsonGenerate(response, dto);
        genCode(response, data, OCTET_STREAM, "jonGenerate.zip");
    }

    /**
     * 根据openApi格式数据源生成模型
     *
     * @param response response
     * @param dto      dto
     * @author laoyu
     */
    @PostMapping("/openApiGenerate")
    public void openApiGenerate(HttpServletResponse response, @RequestParam("url") String url) throws IOException {
//        byte[] data = generateService.jsonGenerate(response, url);
//        genCode(response, data, OCTET_STREAM, "jonGenerate.zip");
    }




}
