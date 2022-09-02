package game.server.manager.mybatis.plus.result;

import lombok.Data;
import game.server.manager.common.result.DataResult;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 */
@Data
public class MpDataResult extends DataResult<List<?>> {

    /**当前页数*/
    private long currentPage;
    /**每页显示条数*/
    private long pageSize;

    private long pages;
    /**开始条数*/
    private long startRow;
    /**结束条数*/
    private long endRow;
    /**当前记录数*/
    private long count;
    /**总记录数*/
    private long total;

}
