package edu.nciae.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nciae.common.constant.Constants;
import edu.nciae.common.domain.R;
import edu.nciae.common.page.PageDomain;
import edu.nciae.common.page.TableSupport;
import edu.nciae.common.utils.ServletUtils;
import edu.nciae.common.utils.SqlUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 */
@Slf4j
public class BaseController {
    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (null != pageNum && null != pageSize) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     *
     * @param list
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected R result(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", list);
        m.put("pageNum", pageInfo.getPageNum());
        m.put("total", pageInfo.getTotal());
        return R.ok(m);
    }

    public String getLoginName() {
        return getRequest().getHeader(Constants.CURRENT_USERNAME);
    }
}
