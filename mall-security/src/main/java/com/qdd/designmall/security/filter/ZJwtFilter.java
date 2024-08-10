package com.qdd.designmall.security.filter;

import com.qdd.designmall.security.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ZJwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Value("${security.ignoreUrls}")
    String[] ignoreUrls;

    @Override
    public void doFilterInternal(@Nullable HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain) throws ServletException, IOException {

        assert request != null;
        String uri = request.getRequestURI();

        // 跳过白名单
        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (Arrays.stream(ignoreUrls).anyMatch(pattern -> pathMatcher.match(pattern, uri))) {
            assert filterChain != null;
            filterChain.doFilter(request, response);
            return;
        }

        // 如果token为null或者验证失败，告诉前端
        String jwt = getJwtFromRequest(request);
        if (jwt == null || !tokenProvider.validateJwtToken(jwt)) {
            assert response != null;
            failResponse(response, HttpStatus.UNAUTHORIZED, "Token验证失败，请重新登陆");
            return;
        }


        String username = tokenProvider.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assert filterChain != null;
        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        // 从请求头获取token
        String bearerToken = request.getHeader("Authorization");
        // 或从参数获取token
        if (bearerToken == null) {
            bearerToken = request.getParameter("Authorization");
        }
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    void failResponse(HttpServletResponse response, HttpStatus status, String data) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(status.value());
        try(PrintWriter writer = response.getWriter()){
            writer.write(data);
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


