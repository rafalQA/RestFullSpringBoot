package com.possessor.interceptor;

import com.possessor.model.helperModel.RequestInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by rpiotrowicz on 2017-05-18.
 */

public class RequestLimitInterceptor implements HandlerInterceptor {

    private final int reqNumberLimit = 5;
    private final long reqNumberDuration = 20;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String attributeName = "RequestNumber";
        List requestsInfo;

        ServletContext servletContext = request.getServletContext();
        Object requestsInfoAttribute = servletContext.getAttribute(attributeName);

        if (requestsInfoAttribute != null) {
            requestsInfo = (List) requestsInfoAttribute;
            int requestsInfoSize = requestsInfo.size();
            int startReqIndex = requestsInfoSize - reqNumberLimit;

            if (startReqIndex == 0) {
                if (isReqLimitExceeded(requestsInfo, startReqIndex)) {
                    return false;
                }
                clearUnnecessaryRequestsInfo(requestsInfo, requestsInfoSize, startReqIndex);
            }
            addNewReqInfo(requestsInfo);
        } else {
            addRequestsInfoWithFirstReqInfo(attributeName, servletContext);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isReqLimitExceeded(List requestsInfo, int startReqIndex) {
        RequestInfo startReqInfo = (RequestInfo) requestsInfo.get(startReqIndex);

        return calcDurationStarRequestToNow(startReqInfo) <= reqNumberDuration;
    }

    private long calcDurationStarRequestToNow(RequestInfo firstReqInfo) {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() -
                firstReqInfo.getRequestTime());
    }

    private void addRequestsInfoWithFirstReqInfo(String attributeName, ServletContext servletContext) {
        List requestsInfo;

        requestsInfo = new ArrayList();
        requestsInfo.add(getRequestInfoWithCurrentTime());

        servletContext.setAttribute(attributeName, requestsInfo);
    }

    private void addNewReqInfo(List requestsInfo) {
        requestsInfo.add(getRequestInfoWithCurrentTime());
    }

    private RequestInfo getRequestInfoWithCurrentTime() {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequestTime(System.currentTimeMillis());

        return requestInfo;
    }

    private void clearUnnecessaryRequestsInfo(List requestsInfo, int requestsInfoSize, int startReqIndex) {
        List clearRequestsInfo = requestsInfo.subList(startReqIndex,
                requestsInfoSize);

        requestsInfo.removeAll(clearRequestsInfo);
    }
}
