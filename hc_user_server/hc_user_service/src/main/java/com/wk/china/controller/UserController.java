package com.wk.china.controller;

import com.hchina.common.pojo.PageResult;
import com.wk.china.service.UserService;
import com.wk.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/check")
    public ResponseEntity<User> check(){
        User user = new User();
        user.setUsername("wlkg");
        User user1 = userService.check(user);
        return ResponseEntity.ok(user1);
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/code")
    public ResponseEntity<Void> sendVertifyCode(String phone){
        Boolean boo = this.userService.sendVertifyCode(phone);
        return ResponseEntity.ok(null);
    }

    /**
     * 注册
     *
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code) {
        //1、校验user对象的数据
        System.out.println(user);
        //2、实现用户注册
        this.userService.register(user, code);
        return ResponseEntity.ok(null);
    }


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        User user = this.userService.queryUser(username,password);
        return ResponseEntity.ok(user);
    }


    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<User>> queryUserByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "row",defaultValue = "5") Integer rows
    ){
        PageResult<User> result = this.userService.queryUserByPage(page,rows);
        return ResponseEntity.ok(result);
    }

}
