package com.n19dccn112.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
//    response.sendRedirect(request.getContextPath() + "/login");
        log.error("Unauthorized error: {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
//    Đây là một class trong Spring Security để xử lý khi có lỗi xác thực đối với JWT (JSON Web Token).
//
//        - Dòng @Component trên class cho biết đây là một Spring bean được quản lý bởi Spring.
//        - Interface AuthenticationEntryPoint xác định một cách tổng quát cho cổng vào xác thực.
//        - Phương thức commence() được ghi đè để triển khai cách xử lý khi có request đến mà không có quyền truy cập.
//        - Trong trường hợp này, khi phát hiện có lỗi xác thực, log.error() được sử dụng để ghi log lỗi, và response.sendError() được sử dụng để trả về lỗi "Unauthorized" (HTTP 401).
