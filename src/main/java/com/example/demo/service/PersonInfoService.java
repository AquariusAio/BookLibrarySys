package com.example.demo.service;

import com.example.demo.Dao.entity.Customer;
import com.example.demo.conf.Result;

/**
 * @auther:Helen
 * @date 2022/6/7&11:25
 */
public interface PersonInfoService {
    public Result updateInfo(Customer customer);
    public Result getOrderInfo(int customerid);
    public Result delById(String ordernumber);
}
