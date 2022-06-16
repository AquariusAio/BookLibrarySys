package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.Dao.entity.Customer;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.CustomerMapper;
import com.example.demo.service.LoginService;
import com.example.demo.utils.Constants;
import com.example.demo.utils.RedisUtil;
import com.example.demo.utils.TokenUtils;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther:Helen
 * @date 2022/5/30&23:54
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    CustomerMapper customermapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Result login(Customer customer)
    {

        Customer res = customermapper.selectOne(Wrappers.<Customer>lambdaQuery().eq(Customer::getCustomername,customer.getCustomername()));
        Customer res1 = customermapper.selectOne(Wrappers.<Customer>lambdaQuery().eq(Customer::getCustomername,customer.getCustomername()).eq(Customer::getPassword,customer.getPassword()));
        if(res == null)
        {
            return Result.error("-1","用户不存在，请注册账号");
        }
        if(res1 == null)
        {
            return Result.error("-1","密码错误");
        }
        //if(redisUtil.exists("customer_login:"+res.getId()));
        //存入redis
        redisUtil.set("customer_login:" + res.getId(), res, Constants.CACHE_1DAY);
        return Result.success(res);
    }

    @Override
    public Result logout(Customer customer)
    {
        redisUtil.remove("customer_login:" + customer.getId());
        return Result.success("成功退出登录");
    }

    @Override
    public Result register(Customer customer){

        Customer res = customermapper.selectOne(Wrappers.<Customer>lambdaQuery().eq(Customer::getCustomername,customer.getCustomername()));
        if(res != null)
        {
            return Result.error("-1","用户名重复");
        }

        System.out.println(customer.getPassword().length());
        if(customer.getPassword().length()<8||customer.getPassword().length()>16)
        {
            return Result.error("-1","密码长度应该为8-16位");
        }
        if(!customer.getPassword().matches("[a-zA-Z]+[0-9]"))
        {
            return Result.error("-1","密码长度应该为8-16位,必须包含数字、字母");
        }

        customer.setMembership("初等会员");
        customermapper.insert(customer);
        return Result.success();
    }
}
