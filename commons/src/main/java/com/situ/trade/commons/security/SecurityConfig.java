package com.situ.trade.commons.security;

import com.situ.trade.commons.domian.vo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;

/**
 *SpringSecurity的配置文件
 */
@Configuration
@EnableWebSecurity//开启Security的支持
@EnableGlobalMethodSecurity(prePostEnabled =true) // 开启方法注解
public class SecurityConfig {

    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //这里可以对httpSecurity进行详细的配置   链式调用的配置方式
        http.formLogin(AbstractHttpConfigurer::disable)     // 方法引用，禁用表单登录
                .logout(AbstractHttpConfigurer::disable)    // 禁用默认退出
                .csrf(AbstractHttpConfigurer::disable)      // 禁用csrf的保护，分布式的前后端分离的项目
                // 设定CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 禁用Session,
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 用户未登录的处理
                .exceptionHandling( exception -> exception.authenticationEntryPoint(authenticationEntryPoint()))
                // 用户权限不足的处理
                .exceptionHandling( exception -> exception.accessDeniedHandler(accessDeniedHandler()))
                // 配置路径拦截
                .authorizeHttpRequests( request ->
                        request.requestMatchers( HttpMethod.GET,
                                        // 用户不登录可以访问的路径
                                        "/type",
                                        "/captcha",
                                        "/goods/**",
                                        "/upload/**").permitAll()
                                .requestMatchers( HttpMethod.POST,
                                        // 用户不登录就可以访问的路径
                                        "/user/login", "/user",
                                        "/admin/login").permitAll()
                                .anyRequest().authenticated()   // 其它路径，必须要登录后才能访问
                );
        // 配置Token过滤器, 将过滤器加入到执行链中
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 1.配置认证管理器
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    protected AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 2.密码的编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 3.用户未登陆时的错误处理
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ( request, response, authException)-> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  //401 用户未登录
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(Result.error("用户未登录！").toJson());
        };
    }

    /**
     * 4.权限不足时的处理
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ( request, response, authException)-> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);   //403 用户权限不足
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(Result.error("当前用户权限不足！").toJson());
        };
    }

    /**
     * 5.配置跨域请求
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");  //允许任何的源
        config.addAllowedMethod("*");         //允许任何的HTTP请求方式
        config.addAllowedHeader("*");         //允许任何的HTTP的头
        config.setAllowCredentials(true);     //允许证书
        config.setMaxAge(3600L);              //设置浏览器预检的时间

        //生成源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
/* public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  //401 用户未登录
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(Result.error("用户未登录").toJson());
            }
        };
    }*/

