package plus.easydo.api;

import plus.easydo.common.result.R;
import plus.easydo.common.vo.SysDictDataVo;
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
     * @return result.plus.easydo.common.R<java.lang.Object>
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
     * @return result.plus.easydo.common.R<vo.plus.easydo.common.SysDictDataVo>
     * @author laoyu
     * @date 2022/8/31
     */
    @GetMapping("/getSingleDictData/{dictCode}/{dictDataKey}")
    R<SysDictDataVo> getSingleDictData(@PathVariable("dictCode")String dictCode, @PathVariable("dictDataKey")String dictDataKey);

}
