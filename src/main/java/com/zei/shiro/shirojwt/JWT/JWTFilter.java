package com.zei.shiro.shirojwt.JWT;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicHttpAuthenticationFilter{

    /**
     * 判断用户是否想登陆
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        return authorization!=null;
    }

    /**
     * 交给realm进行登陆
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");

        JWTToken token = new JWTToken(authorization);
        getSubject(request,response).login(token);

        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(isLoginAttempt(request, response)){
//        if(((HttpServletRequest)request).getHeader("Authorization") != null){
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                responseError(request, response);
            }
        }
        return true;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response,"/index");
    }

    /**
     * 跨域支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        resp.setHeader("Access-Control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));

        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            resp.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }


    /**
     * 处理非法请求
     * @param request
     * @param response
     */
    private void responseError(ServletRequest request, ServletResponse response){
        try {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect("/error");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
