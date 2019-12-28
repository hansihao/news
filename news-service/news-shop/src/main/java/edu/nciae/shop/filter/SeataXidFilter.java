package edu.nciae.shop.filter;

import edu.nciae.common.constant.Constants;
import edu.nciae.common.utils.StringUtils;
import io.seata.core.context.RootContext;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Http Rest请求拦截器，用于把当前上下文中获取到的XID放到RootContext
 */
public class SeataXidFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xid = RootContext.getXID();
        String restXid = request.getHeader(Constants.XID_HEADER);
        System.out.println("拦截到的xid:" + restXid);
        boolean bind = false;
        if (StringUtils.isBlank(xid) && StringUtils.isNotBlank(restXid)) {
            RootContext.bind(restXid);
            bind = true;
            if (logger.isDebugEnabled()) {
                logger.debug("bind[" + restXid + "] to RootContext");
            }
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (bind) {
                String unbindXid = RootContext.unbind();
                if (logger.isDebugEnabled()) {
                    logger.debug("unbind[" + unbindXid + "] from RootContext");
                }
                if (!restXid.equalsIgnoreCase(unbindXid)) {
                    logger.warn("xid in change during http rest from " + restXid + " to " + unbindXid);
                    if (unbindXid != null) {
                        RootContext.bind(unbindXid);
                        logger.warn("bind [" + unbindXid + "] back to RootContext");
                    }
                }
            }
        }

    }
}
