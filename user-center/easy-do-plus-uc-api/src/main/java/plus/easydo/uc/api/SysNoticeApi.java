package plus.easydo.uc.api;

import cn.zhxu.bs.SearchResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import plus.easydo.common.result.DataResult;
import plus.easydo.common.result.MpDataResult;
import plus.easydo.common.result.R;
import plus.easydo.common.vaild.Insert;
import plus.easydo.common.vaild.Update;
import plus.easydo.uc.dto.SysNoticeDto;
import plus.easydo.uc.vo.SysNoticeVo;

import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 系统公告相关api
 * @date 2023/6/24
 */
@HttpExchange("/notice")
public interface SysNoticeApi {

    String apiPath = "/notice";

    /**
     * 获取所有通知公告列表
     */
    @GetExchange("/list")
    public R<List<SysNoticeVo>> list(@RequestParam(required = false) Map<String, Object> queryParam);

    /**
     * 分页条件查询通知公告列表
     */
    @PostExchange("/page")
    public MpDataResult page(@RequestBody Map<String,Object> queryParam);

    /**
     * 获取通知公告详细信息
     */
    @GetExchange("/info/{id}")
    public R<SysNoticeVo> info(@PathVariable("id")Long id);

    /**
     * 新增通知公告
     */
    @PostExchange("/add")
    public R<Object> add(@RequestBody @Validated({Insert.class}) SysNoticeDto sysNoticeDto);

    /**
     * 修改通知公告
     */
    @PostExchange("/update")
    public R<Object> update(@RequestBody @Validated({Update.class}) SysNoticeDto sysNoticeDto);

    /**
     * 删除通知公告
     */
    @GetExchange("/remove/{id}")
    public R<Object> remove(@PathVariable("id")Long id);
}
