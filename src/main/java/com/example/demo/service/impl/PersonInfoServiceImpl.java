package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Dao.entity.BookInventory;
import com.example.demo.Dao.entity.BookOrder;
import com.example.demo.Dao.entity.Customer;
import com.example.demo.Dao.entity.CustomerBookOrder;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.BookInventoryMapper;
import com.example.demo.mapper.auto.BookOrderMapper;
import com.example.demo.mapper.auto.CustomerBookOrderMapper;
import com.example.demo.mapper.auto.CustomerMapper;
import com.example.demo.service.BookInventoryService;
import com.example.demo.service.PersonInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/7&11:25
 */
@Service
public class PersonInfoServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements PersonInfoService
{
    @Resource
    CustomerMapper customermapper;

    @Resource
    CustomerBookOrderMapper customerbookordermapper;

    @Override
    public Result updateInfo(Customer customer) {

        if (customer.getEmail()!=null&&!("").equals(customer.getEmail())){
            if(!customer.getEmail().matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"))
            return Result.error("1","邮箱格式不正确");
        }
        if(customer.getTelephone()!=null&&!("").equals(customer.getTelephone()))
        {
            if(customer.getTelephone().matches("1^1(3\\\\d|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8\\\\d|9[0-35-9])\\\\d{8}$"))
            return Result.error("1","手机号格式不正确");
        }
        customermapper.updateById(customer);
        return Result.success(customer);
    }
    //用户删除
    @Override
    public Result delById(String ordernumber) {
        customerbookordermapper.deleteById(ordernumber);
        return Result.success("删除成功");
    }

    @Override
    public Result getOrderInfo(int customerid) {
        QueryWrapper<CustomerBookOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("customerid",customerid);
        List<CustomerBookOrder> list = customerbookordermapper.selectList(wrapper);
        return Result.success(list);
    }
}
