package top.yeonon.adgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author yeonon
 * @date 2019/3/19 0019 15:41
 **/

@Slf4j
@Component
public class LogRequestPreFilter extends ZuulFilter {

    private static final String FILTER_TYPE = "pre";

    private static final boolean SHOULD_FILTER = true;

    private static final int FILTER_ORDER = 0;

    private static final String START_TIME = "startTime";

    @Override
    public String filterType() {
        return FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.set(START_TIME, System.currentTimeMillis());
        return null;
    }
}
