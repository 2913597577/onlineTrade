package com.situ.trade.commons.security;

import com.situ.trade.commons.domian.entity.Admin;
import com.situ.trade.commons.domian.entity.User;
import com.situ.trade.commons.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 取Token 生成登录信息
        String token = request.getHeader("token");
        // token不为空
        if (StringUtils.hasText(token)){
            // 解密
            try {
                UserDetails userDetails = JWTUtil.getUserDetailsList(token, User.class, Admin.class);
                if ( ! ObjectUtils.isEmpty(userDetails)) {
                    // 将这个用户注册到Security中
                    UsernamePasswordAuthenticationToken authenticationToken
                            = new UsernamePasswordAuthenticationToken(
                            userDetails, null,
                            userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Token无效，
            }
        }

        // 过滤器放行
        filterChain.doFilter(request, response);
    }

}