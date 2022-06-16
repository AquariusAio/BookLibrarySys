package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.Dao.entity.Customer;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.CustomerMapper;
import com.example.demo.service.LoginService;
import com.example.demo.service.impl.LoginServiceImpl;
import com.example.demo.service.impl.PersonInfoServiceImpl;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @auther:Helen
 * @date 2022/6/3&17:18
 */
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/customer")
public class UserController {

    @Resource
    LoginServiceImpl loginservice;
    @Resource
    PersonInfoServiceImpl personinfoservice;

    @Resource
    CustomerMapper customermapper;

    @PostMapping("/login")
    public Result login(@RequestBody Customer customer) throws AuthenticationException {
        return loginservice.login(customer);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Customer customer) throws AuthenticationException {
        return loginservice.register(customer);
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody Customer customer) throws AuthenticationException {
        return loginservice.logout(customer);
    }

    @PutMapping("/updateinfo")
    public Result updateInfo(@RequestBody Customer customer) throws AuthenticationException {
        return personinfoservice.updateInfo(customer);
    }

    //用户根据订单号删除订单
    @DeleteMapping("/{ordernumber}")
    public Result delete(@PathVariable String ordernumber) {

        return personinfoservice.delById(ordernumber);
    }
    @GetMapping("/orderinfo")
    public Result getOrderInfo(@RequestParam int customerid) throws AuthenticationException {
        return personinfoservice.getOrderInfo(customerid);
    }
}
