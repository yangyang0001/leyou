package com.inspur.user.controller;

import com.inspur.entity.User;
import com.inspur.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-19 12:12
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验数据是否唯一
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkDataType(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        Boolean checkFlag = userService.checkDataType(data, type);
        if(checkFlag == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(checkFlag);
    }

    @PostMapping("/code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) {
        this.userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestParam("code") String code, @Valid User user) {
        this.userService.register(code, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        User user = this.userService.queryUser(username, password);
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

}
