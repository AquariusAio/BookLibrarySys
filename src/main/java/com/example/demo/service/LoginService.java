package com.example.demo.service;

import com.example.demo.conf.Result;
import org.apache.tomcat.websocket.AuthenticationException;
import com.example.demo.Dao.entity.Customer;

/**
 * @auther:Helen
 * @date 2022/5/30&23:53
 */
public interface LoginService {
    public Result login(Customer customer) throws AuthenticationException;
    public Result logout(Customer customer);
    public Result register(Customer customer);
}
