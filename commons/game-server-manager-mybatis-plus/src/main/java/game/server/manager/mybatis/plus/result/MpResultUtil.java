package game.server.manager.mybatis.plus.result;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 */
public class MpResultUtil {

    private MpResultUtil() {
    }

    public static <T> MpDataResult buildPageData(IPage<T> page, List<T> list) {
        long pageNum = page.getCurrent();
        long pageSize = page.getSize();
        long startRow = (page.getCurrent() * page.getSize());
        long endRow = (page.getCurrent() + page.getSize());
        Long total = page.getTotal();
        long pages = page.getPages();
        return buildPageData(pageNum, pageSize, startRow, endRow, total, pages, list);
    }


    public static <T> MpDataResult buildPageData(IPage<T> page) {
        List<T> list = page.getRecords();
        return buildPageData(page, list);
    }

    public static <T> MpDataResult buildPageData(List<T> pageList, Integer pageNum, Integer pageSize) {
        if (CollectionUtils.isEmpty(pageList)) {
            return buildPageData(pageNum, pageSize, 0, 0, 0L, 0, pageList);
        } else {
            Integer count = pageList.size();
            int pageCount;
            if (count % pageSize == 0) {
                pageCount = count / pageSize;
            } else {
                pageCount = count / pageSize + 1;
            }

            int fromIndex;
            int toIndex;
            if (!pageNum.equals(pageCount)) {
                fromIndex = (pageNum - 1) * pageSize;
                toIndex = fromIndex + pageSize;
            } else {
                fromIndex = (pageNum - 1) * pageSize;
                toIndex = count;
            }

            List<T> list = pageList.subList(fromIndex, toIndex);
            return buildPageData(pageNum, pageSize, fromIndex, toIndex, (long)count, pageCount, list);
        }
    }

    public static <T> MpDataResult buildPageData(long current, long pageSize, long startRow, long endRow, Long total, long pages, List<T> list) {
        MpDataResult dataResult = new MpDataResult();
        dataResult.setCurrentPage(current);
        dataResult.setPageSize(pageSize);
        dataResult.setPages(pages);
        dataResult.setStartRow(startRow);
        dataResult.setEndRow(endRow);
        dataResult.setTotal(total);
        dataResult.setCount(total);
        dataResult.setData(list);
        dataResult.setSuccess(true);
        return dataResult;
    }

    public static <T> MpDataResult buildPage(IPage<?> page, Class<T> clazz) {
        long current = page.getCurrent();
        long pageSize = page.getSize();
        long startRow = ((page.getCurrent() - 1L) * page.getSize());
        long endRow = (page.getCurrent() * page.getSize());
        Long total = page.getTotal();
        long pages = page.getPages();
        return buildPageData(current, pageSize, startRow, endRow, total, pages, BeanUtil.copyToList(page.getRecords(),clazz));
    }
    public static <T> MpDataResult buildPage(IPage<T> page) {
        long current = page.getCurrent();
        long pageSize = page.getSize();
        long startRow = ((page.getCurrent() - 1L) * page.getSize());
        long endRow = (page.getCurrent() * page.getSize());
        Long total = page.getTotal();
        long pages = page.getPages();
        return buildPageData(current, pageSize, startRow, endRow, total, pages, page.getRecords());
    }

    public static MpDataResult empty() {
;       MpDataResult dataResult = new MpDataResult();
        dataResult.setCurrentPage(0);
        dataResult.setPageSize(0);
        dataResult.setPages(0);
        dataResult.setStartRow(0);
        dataResult.setEndRow(0);
        dataResult.setTotal(0);
        dataResult.setCount(0);
        dataResult.setSuccess(true);
        return dataResult;
    }

}
