package io.project.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.project.model.Claim;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

    @Autowired
    private RestTemplate restTemplate;

    public String traceClaim(Claim claim) {
        return claim.toString();
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String userAgent = request.getHeader("User-Agent");

        String authToken = request.getHeader("AUTH-TOKEN");

        LOGGER.info("ZUUL report: User-Agent is " + userAgent);

        LOGGER.info("ZUUL report: authToken is " + authToken);

        try {

        } catch (RestClientException e) {
            LOGGER.info("ERROR Claim is null " + e.getLocalizedMessage());

            return null;
        }

        LOGGER.info("Processing incoming request for {}.", ctx.getRequest().getRequestURI());
        LOGGER.info("Adding ZUUL CORRELATION_ID " + FilterUtils.CORRELATION_ID);
        LOGGER.info("Adding ZUUL getCorrelationId" + filterUtils.getCorrelationId());

        if (isCorrelationIdPresent()) {
            LOGGER.info("zuul-correlation-id: {}. ", filterUtils.getCorrelationId());
        } else {
            filterUtils.setCorrelationId(generateCorrelationId());
            LOGGER.info("zuul-correlation-id else: {}.", filterUtils.getCorrelationId());
        }

        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());

        ctx.addZuulRequestHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());

        LOGGER.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
