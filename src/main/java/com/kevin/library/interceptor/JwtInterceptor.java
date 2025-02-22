package com.kevin.library.interceptor;

import java.io.IOException;

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

@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

   // 允許 OPTIONS，避免 CORS 被阻擋
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
        return true; 
    }

    String authHeader = request.getHeader("Authorization");

    // 沒有 Authorization，回傳 401 
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
    	System.out.println("沒有Authorization");
        sendUnauthorizedResponse(response, "Missing or invalid Authorization header.");
        return false;
    }

    String token = authHeader.substring(7);

    // Token 驗證失敗，回傳 401 
    if (!jwtService.validateToken(token)) {
    	System.out.println("Token 驗證失敗");
        sendUnauthorizedResponse(response, "Invalid JWT token.");
        return false;
    }
    
    if (jwtService.isTokenBlacklisted(token)) {
    	System.out.println("Token 驗證失敗");
        sendUnauthorizedResponse(response, "Out Dated JWT token.");
        return false;
    }
    

    Integer userId = jwtService.getUserIdFromToken(token);
    if (userId == null) {
        sendUnauthorizedResponse(response, "Invalid token.");
        return false;
    }

    // 把 userId 存入 request
    request.setAttribute("userId", userId);
    return true;
}

	//處理 401 Unauthorized`
	private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().write("{\"success\": false, \"message\": \"" + message + "\"}");
	}
}
