package com.kevin.library.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kevin.library.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

@Autowired
private JwtService jwtService;

public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"Missing or invalid Authorization header.\"}");
        return false;
    }

    String token = authHeader.substring(7);

    //驗證Token是否有效
    if (!jwtService.validateToken(token)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"Invalid JWT token.\"}");
        return false;
    }

    return true; 
}
}
