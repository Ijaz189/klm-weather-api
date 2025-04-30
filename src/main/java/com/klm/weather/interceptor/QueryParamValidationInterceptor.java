package com.klm.weather.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class QueryParamValidationInterceptor implements HandlerInterceptor {

    private static final Set<String> ALLOWED_PARAMS = new HashSet<>(Arrays.asList("date", "city", "sort"));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getRequestURI().equals("/weather")) {
            for (String param : request.getParameterMap().keySet()) {
                if (!ALLOWED_PARAMS.contains(param)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid query parameter: " + param);
                    return false;
                }
            }
        }
        return true;
    }
}
