package game.server.manager.generate.service;


import game.server.manager.generate.dto.GenerateDatabaseDocDto;
import game.server.manager.generate.dto.JsonGenerateDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 */
public interface GenerateService {


    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    Map<String, String> previewCode(Long tableId);

    /**
     * 生成代码（下载方式）
     *
     * @param ids ids
     * @return 数据
     */
    byte[] downloadCode(String ids);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     */
    void generatorCode(String tableName);


    /**
     * 批量生成代码（下载方式）
     *
     * @param id id
     * @return 数据
     */
    byte[] downloadCode(Long id);


    /**
     * 生成数据库文档-zip
     *
     * @param response response
     * @param dto      dto
     * @return byte[]
     * @author laoyu
     */
    byte[] generateDatabaseDoc(HttpServletResponse response, GenerateDatabaseDocDto dto);


    /**
     * 预览数据库文档-docx
     *
     * @param response response
     * @param dto      dto
     * @author laoyu
     */
    byte[] generateDataBaseDocx(HttpServletResponse response, GenerateDatabaseDocDto dto);

    /**
     * 预览数据库文档-pdf
     *
     * @param response response
     * @param dto      dto
     * @return byte[]
     * @author laoyu
     */
    byte[] showDatabasePdf(HttpServletResponse response, GenerateDatabaseDocDto dto);

    /**
     * json内容生成
     *
     * @param response response
     * @param dto      dto
     * @return byte[]
     * @author laoyu
     */
    byte[] jsonGenerate(HttpServletResponse response, JsonGenerateDto dto);

}
