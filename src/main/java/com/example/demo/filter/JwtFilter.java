package com.example.demo.filter;

import com.example.demo.entity.User;
import com.example.demo.service.CustomUserDetailService;
import com.example.demo.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
//    Bearer eyJhbGciOiJIUzI1NiJ9.
//    eyJzdWIiOiJzYW5kZWVwIiwiZXhwIjoxNjE4MDI4OTExLCJpYXQiOjE2MTc5OTI5MTF9.
//    qpUG4taysAhdrcSmmzDIuyplnIgu764MHSKnKpntiCc
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      username = jwtUtil.extractUsername(token);
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
      if (jwtUtil.validateToken(token, userDetails)) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
