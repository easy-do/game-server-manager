package game.server.manager.api;

import game.server.manager.common.result.R;
import game.server.manager.common.vo.SysDictDataVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/22
 */
public interface SysDictDataApi  {

    /**
     * 获得字典列表
     *
     * @param dictCode dictCode
     * @return game.server.manager.common.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/listByCode/{dictCode}")
    R<Object> listByCode(@PathVariable("dictCode") String dictCode);


    /**
     * 获得单个字典参数
     *
     * @param dictCode dictCode
     * @param dictDataKey dictDataKey
     * @return game.server.manager.common.result.R<game.server.manager.common.vo.SysDictDataVo>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/getSingleDictData/{dictCode}/{dictDataKey}")
    R<SysDictDataVo> getSingleDictData(@PathVariable("dictCode")String dictCode, @PathVariable("dictDataKey")String dictDataKey);

}
