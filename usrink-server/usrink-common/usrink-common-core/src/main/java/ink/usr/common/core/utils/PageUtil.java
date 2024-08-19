package ink.usr.common.core.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import ink.usr.common.core.constants.Constants;

public class PageUtil {

    /**
     * 开始分页
     *
     * @param <E> 分页对象
     * @return 分页对象
     */
    public static <E> Page<E> startPage() {

        // 获取分页参数
        String pageNum = ServletUtil.getParameter(Constants.PAGE_NUM);
        // 获取分页大小
        String pageSize = ServletUtil.getParameter(Constants.PAGE_SIZE);

        return PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }

}
