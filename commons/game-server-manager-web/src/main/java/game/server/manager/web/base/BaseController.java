package game.server.manager.web.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import game.server.manager.mybatis.plus.result.MpDataResult;
import game.server.manager.mybatis.plus.result.MpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import game.server.manager.common.result.DataResult;
import game.server.manager.common.result.R;
import game.server.manager.common.vaild.Insert;
import game.server.manager.common.vaild.Update;

import java.io.Serializable;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 基础controller
 * @date 2022/6/18
 */
public abstract class BaseController<SERVICE extends BaseService<Entity,QO,VO,DTO>, Entity, ID extends Serializable,QO, VO, DTO> {

    public static final String ADD_ACTION = "添加";

    public static final String EDIT_ACTION = "编辑";

    public static final String REMOVE_ACTION = "删除";

    public static final String LOG_TYPE = "操作日志";

    @Autowired
    protected SERVICE baseService;

    /**
     * 获取列表集合
     *
     * @return plus.easydo.core.result.R<java.util.List < VO>>
     * @author laoyu
     * @date 2022/7/6
     */
    @RequestMapping("/list")
    public R<List<VO>> list(){
       return DataResult.ok(baseService.voList());
    }

    /**
     * 分页查询
     *
     * @param qo qo
     * @return game.server.manager.server.result.MPDataResult
     * @author laoyu
     * @date 2022/7/6
     */
    @PostMapping("/page")
    public MpDataResult page(@RequestBody QO qo){
        IPage<VO> page = baseService.page(qo);
        return MpResultUtil.buildPage(page);
    }

    /**
     * 获取详情
     *
     * @param id 主键
     * @return plus.easydo.core.result.R<VO>
     * @author laoyu
     * @date 2022/7/6
     */
    @RequestMapping("/info/{id}")
    public R<VO> info(@PathVariable("id") ID id){
        return DataResult.ok(baseService.info(id));
    }

    /**
     * 添加
     *
     * @param dto dto
     * @return plus.easydo.core.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/7/6
     */
    @PostMapping("/add")
    public R<Object> add(@RequestBody @Validated({Insert.class}) DTO dto){
        return baseService.add(dto) ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 修改
     *
     * @param dto dto
     * @return plus.easydo.core.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/7/6
     */
    @PostMapping("/edit")
    public R<Object> edit(@RequestBody @Validated({Update.class}) DTO dto) {
    return baseService.edit(dto) ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return plus.easydo.core.result.R<java.lang.Object>
     * @author laoyu
     * @date 2022/7/6
     */
    @GetMapping("/delete/{id}")
    public R<Object> delete( @PathVariable("id") ID id){
        return baseService.delete(id) ? DataResult.ok() : DataResult.fail();
    }

    /**
     * 统计总数
     *
     * @return java.lang.Long
     * @author laoyu
     * @date 2022/7/6
     */
    @GetMapping("/count")
    public Long count(){
        return baseService.count();
    }

}

