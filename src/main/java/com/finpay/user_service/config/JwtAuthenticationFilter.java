package com.finpay.user_service.config;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
// Kế thừa OncePerRequest để đảm bỏa mỗi req gửi lên chỉ bị lọc duy nhất một lần
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("=== JWT FILTER RUNNING ===");
        System.out.println("URI = " + request.getRequestURI());
        // 1. Trích xuất token từ Header "Authorization"
        String token = getJwtFromRequest(request);
        System.out.println("TOKEN = " + token);
        // 2. Nếu token hợp lệ, tiến hành thiết lập thoogn tin xác thực vào hệ thống spring security
        if(StringUtils.hasText(token) && jwtProvider.validateToken(token)){
            String username = jwtProvider.getUsernameFromToken(token);
            // Tạo đối tượng xác thực cứa Username và Quyền hạn (Tạm thời gán ROLE_USER)
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            // Bỏ ông User này vào "két an ninh toàn cục" của spring security để các tầng sau nhận diện
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println(
                    SecurityContextHolder.getContext().getAuthentication()
            );
        }
        //3. Cho phép request tiếp tục hành trình đi sang các bộ lọc tieps theo hoặc vào thẳng Controller
        filterChain.doFilter(request,response);
    }

    // Hàm phụ trợ bóc tách chuỗi "Bearer <token_chuoi_dai>"
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7); //Cắt bỏ 7 ki tự đầu "Bearer " để lấy token
        }
        return  null;
    }
}
