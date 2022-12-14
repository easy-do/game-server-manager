package game.server.manager.generate.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.generate.dto.GenerateDatabaseDocDto;
import game.server.manager.generate.dto.JsonGenerateDto;
import game.server.manager.generate.entity.GenTable;
import game.server.manager.generate.entity.GenTableColumn;
import game.server.manager.generate.qo.DbListQo;
import game.server.manager.generate.service.DataSourceDbService;
import game.server.manager.generate.service.GenTableColumnService;
import game.server.manager.generate.service.GenTableService;
import game.server.manager.generate.service.GenerateService;
import game.server.manager.generate.util.WordPdfUtils;
import game.server.manager.generate.vo.DbListVo;
import game.server.manager.mybatis.plus.qo.MpBaseQo;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
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
 * ไปฃ็ ็ๆ
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
     * ๆฅ่ฏขไปฃ็ ็ๆๅ่กจ
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
     * ๅ้กตๆฅ่ฏขไปฃ็ ็ๆๅ่กจ
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
     * ่ทๅ่กจ่ฏฆๆ
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
     * ๆฅ่ฏขๆฐๆฎๅบๅ่กจ
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
     * ๆฅ่ฏขๆฐๆฎ่กจๅญๆฎตๅ่กจ
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
     * ๅฏผๅฅ่กจ็ปๆ๏ผไฟๅญ)
     *
     * @param tables       tables
     * @param dataSourceId dataSourceId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @PostMapping("/importTable")
    public R<Object> importTableSave(String tables, String dataSourceId) {
        String[] tableNames = tables.split(",");
        // ๆฅ่ฏข่กจไฟกๆฏ
        List<DbListVo> tableList = dataSourceDbService.selectDbTableListByNames(dataSourceId, tableNames);
        genTableService.importGenTable(dataSourceId, tableList);
        return DataResult.ok();
    }

    /**
     * ไฟฎๆนไฟๅญไปฃ็ ็ๆไธๅก
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
     * ๅ ้คไปฃ็ ็ๆ
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
     * ้ข่งไปฃ็ 
     *
     * @param tableId tableId
     * @return plus.easydo.core.R.DataR
     * @author laoyu
     */
    @GetMapping("/preview/{tableId}")
    public R<Map<String, String>> preview(@PathVariable("tableId") Long tableId) {
        Map<String, String> dataMap = generateService.previewCode(tableId);
        return DataResult.ok(dataMap);
    }

    /**
     * ็ๆไปฃ็ ๏ผไธ่ฝฝๆนๅผ๏ผ
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
     * ็ๆไปฃ็ ๏ผ่ชๅฎไน่ทฏๅพ๏ผ
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
     * ๅๆญฅๆฐๆฎๅบ
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
     * ๆน้็ๆไปฃ็ 
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
     * ็ๆzipๆไปถ
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
     * ็ๆๆฐๆฎๅบๆๆกฃ-zip
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
     * ็ๆๆฐๆฎๅบๆๆกฃ-docx
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
     * ้ข่งๆฐๆฎๅบๆๆกฃ-pdf
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
     * word่ฝฌpdf
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
     * ๆ นๆฎhttpๆฅๅฃ็ปๆ็ๆ
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


}
