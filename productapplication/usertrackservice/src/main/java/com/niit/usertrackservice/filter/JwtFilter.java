package com.niit.usertrackservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        String authorizationheader=httpServletRequest.getHeader("authorization");
        if("OPTIONS".equals(httpServletRequest.getMethod())){
            httpServletResponse.setStatus(httpServletResponse.SC_OK);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        else if(authorizationheader==null||!authorizationheader.startsWith("Bearer")){
            throw new ServletException("Missing or it is invalid Exception so Please check");
        }
        String token=authorizationheader.substring(7);
        Claims claims =Jwts.parser().setSigningKey("secretekeyid").parseClaimsJws(token).getBody();
        httpServletRequest.setAttribute("claims",claims);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
