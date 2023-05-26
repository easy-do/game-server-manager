package plus.easydo.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.R;
import plus.easydo.generate.dynamic.core.dto.DataSourceDto;
import plus.easydo.generate.dynamic.core.qo.DataSourceQo;
import plus.easydo.generate.dynamic.core.vo.DataSourceVo;
import plus.easydo.generate.service.DataSourceManagerService;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.dao.result.MpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据源管理Controller
 * 
 * @author gebilaoyu
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceManagerController {

    @Autowired
    private DataSourceManagerService dataSourceManagerService;

    /**
     * 分页条件查询数据源管理列表
     *
     * @param qo qo
     * @return plus.easydo.starter.mybatis.plus.R.MPDataR
     * @author laoyu
     */
    @PostMapping("/page")
    public MpDataResult page(@RequestBody DataSourceQo qo) {
        IPage<DataSourceVo> page = dataSourceManagerService.page(qo);
        return MpResultUtil.buildPage(page);
    }

    /**
     * 获取所有数据源管理列表
     *
     * @param qo qo
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/list")
    public R<List<DataSourceVo>> list(@RequestBody DataSourceQo qo) {
        return DataResult.ok(dataSourceManagerService.list(qo));
    }


    /**
     * 获取数据源管理详细信息
     *
     * @param id id
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @GetMapping("/info/{id}")
    public R<Object> getInfo(@PathVariable("id") String id) {
        return DataResult.ok(dataSourceManagerService.selectById(id));
    }


    /**
     * 新增或修改数据源管理
     *
     * @param dto dto
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/saveOrUpdate")
    public R<Object> saveOrUpdate(@RequestBody @Validated DataSourceDto dto) {
        return dataSourceManagerService.saveOrUpdate(dto)?DataResult.ok():DataResult.fail();
    }

    /**
     * 删除数据源管理
     *
     * @param id id
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
	@GetMapping("/remove/{id}")
    public R<Object> remove(@PathVariable("id") Long id) {
        return dataSourceManagerService.deleteById(id)?DataResult.ok():DataResult.fail();
    }

    /**
     * 测试数据源
     *
     * @param dto dto
     * @return plus.easydo.core.R.R
     * @author laoyu
     */
    @PostMapping("/test")
    public R<Object> test(@RequestBody @Validated DataSourceDto dto) {
        return dataSourceManagerService.test(dto);
    }
}
