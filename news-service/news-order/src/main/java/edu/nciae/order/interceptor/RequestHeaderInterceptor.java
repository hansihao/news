package edu.nciae.order.interceptor;

import edu.nciae.common.constant.Constants;
import edu.nciae.common.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Component;

/**
 * Feign拦截器，把RootContext中的XID（XID用于标识一个局部事务属于哪个全局事务，需要在调用链路的上下文中传递）传递到上层调用链路
 */
@Component
public class RequestHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            System.out.println("全局事务的xid:" + xid);
            requestTemplate.header(Constants.XID_HEADER, xid);
        }
    }
}
