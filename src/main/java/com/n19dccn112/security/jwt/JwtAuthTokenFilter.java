package com.n19dccn112.security.jwt;

import com.n19dccn112.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// định nghĩa một lớp filter để xác thực token JWT trong Spring Security.
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    //1-1login
    public JwtAuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    //lớp JwtAuthTokenFilter kiểm tra request headers để xác định xem có token JWt có trong request hay không
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                // TODO
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
//Code trên định nghĩa một lớp filter để xác thực token JWT trong Spring Security.
//
//Cụ thể, lớp JwtAuthTokenFilter là một subclass của OncePerRequestFilter, sẽ chạy một lần duy nhất cho mỗi request.
//
//Trong phương thức doFilterInternal(), lớp JwtAuthTokenFilter kiểm tra request headers để xác định xem có token JWT
// trong request hay không. Nếu tìm thấy, nó sẽ giải mã token và lấy username từ token, sau đó sử dụng thực thể
// UserDetailsServiceImpl để tìm chi tiết người dùng ứng với username này. Nếu chi tiết người dùng hợp lệ,
// lớp JwtAuthTokenFilter sẽ đặt thông tin xác thực của người dùng vào SecurityContextHolder để cho phép người dùng
// truy cập vào các tài nguyên được bảo vệ trên hệ thống.
//
//Phương thức parseJwt(), thực hiện việc truy xuất giá trị của Authorization header từ request và trích xuất token
// JWT từ chuỗi giá trị. Nếu không tìm thấy token JWT, phương thức sẽ trả về null.
