package Util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public JwtAuthenticationFilter(JwtService jwtService, TokenBlacklistService tokenBlacklistService) {
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//
//            if (tokenBlacklistService.isTokenBlacklisted(token)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
//                return;
//            }
//
//            String email = jwtService.extractEmail(token);
//            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = jwtService.loadUserByUsername(email);
//                jwtService.setAuthentication(userDetails, request);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
}
