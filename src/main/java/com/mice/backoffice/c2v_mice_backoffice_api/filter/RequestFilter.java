package com.mice.backoffice.c2v_mice_backoffice_api.filter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RereadableRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper(request);
        filterChain.doFilter(rereadableRequestWrapper, response);
    }
}