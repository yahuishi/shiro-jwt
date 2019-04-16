package com.zei.shiro.shirojwt.controller;

import com.zei.shiro.shirojwt.entity.ResponseEntity;
import com.zei.shiro.shirojwt.entity.User;
import com.zei.shiro.shirojwt.service.UserService;
import com.zei.shiro.shirojwt.JWT.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserJWTController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("username") String username, @RequestParam("password") String password){
        User user = userService.getUser(username);
        if(user!=null&&user.getPassword().equals(password)){
            return new ResponseEntity(666,"login success", JWTUtil.sign(username, password));
        }else{
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/welcome")
    public ResponseEntity welcome(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return new ResponseEntity(666,"you are vip",null);
        }else {
            return new ResponseEntity(666,"you are guest",null);
        }
    }

    @GetMapping("/auth")
    @RequiresAuthentication
    public ResponseEntity auth(){
        return new ResponseEntity(666,"you are auth",null);
    }

    @GetMapping("/admin")
    @RequiresRoles("admin")
    public ResponseEntity admin(){
        return new ResponseEntity(666,"you are admin",null);
    }

    @GetMapping("/permission")
    @RequiresPermissions(logical = Logical.AND,value = {"view","edit"})
    public ResponseEntity permission(){
        return new ResponseEntity(666,"you have permission",null);
    }


    @GetMapping("/error")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity error(){
        return new ResponseEntity(401,"no",null);
    }
}
