package top.yeonon.adgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author yeonon
 * @date 2019/3/19 0019 15:45
 **/
@Slf4j
@Component
public class LogRequestPostFilter extends ZuulFilter {

    private static final String FILTER_TYPE = "post";

    private static final boolean SHOULD_FILTER = true;

    private static final int FILTER_ORDER = FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;


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
        HttpServletRequest request = requestContext.getRequest();
        long startTime = (long) requestContext.get(START_TIME);

        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;

        log.info("uri {} duration {} ms", uri, duration / 100);
        return null;
    }
}
