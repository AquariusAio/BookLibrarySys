package com.example.demo.service;

import com.example.demo.Dao.entity.BookSelling;
import com.example.demo.conf.Result;

import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/11&18:50
 */
public interface BookSellingService {
    public Result updateSellingBookinfo(BookSelling book);
//    public List<BookSelling> findSaleBook(String category, String booklabel, String bookname,Integer salenum,String author);
    public Result removeBatchByIds(List<String> bookids);
    public Result removeById(String id);
    public Result addBookOnSale(BookSelling book);
    public Result checkonebookinfo(String id);
}
