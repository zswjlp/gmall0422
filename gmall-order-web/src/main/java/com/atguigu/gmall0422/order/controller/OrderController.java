package com.atguigu.gmall0422.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0422.bean.UserAddress;
import com.atguigu.gmall0422.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

//    @Autowired
    @Reference
    private UserService userService;

    @RequestMapping("trade")
    public List<UserAddress> trade(String userId){

        return userService.findUserAddressByUserId(userId);
    }
}
