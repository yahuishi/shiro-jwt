package com.zei.shiro.shirojwt.util;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String msg){
        super(msg);
    }

    public UnauthorizedException(){
        super();
    }
}
