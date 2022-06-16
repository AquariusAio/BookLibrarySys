package com.example.demo.service;

import com.example.demo.Dao.entity.BookOrder;
import com.example.demo.conf.Result;

import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/5&20:52
 */
public interface BookOrderService {
    public String getOrderNumber();
    public Result getAllOrder();
    public Result saveOrder(BookOrder bookorder);
    public Result removeBatchByIds(List<String> ordernumbers);
}
