package com.zei.shiro.shirojwt.controller;

import com.zei.shiro.shirojwt.entity.ResponseEntity;
import com.zei.shiro.shirojwt.util.UnauthorizedException;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseEntity handle401(ShiroException e){
        return new ResponseEntity(401,e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handle401(){
        return new ResponseEntity(401,"unauthorized",null);
    }

    /**
     * 捕获所有异常
     * @param request
     * @param ex
     * @return
     */
    public ResponseEntity allException(HttpServletRequest request, Throwable ex){
        return new ResponseEntity(getStatus(request).value(),ex.getMessage(),null);
    }

    public HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status.code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
