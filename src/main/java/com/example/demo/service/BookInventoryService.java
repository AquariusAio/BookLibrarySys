package com.example.demo.service;

import com.example.demo.Dao.entity.BookBuyOrderNumber;
import com.example.demo.Dao.entity.BookBuyRecord;
import com.example.demo.Dao.entity.BookInventory;
import com.example.demo.Dao.entity.BookSelling;
import com.example.demo.conf.Result;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;
import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/6&18:01
 */
public interface BookInventoryService
{
    public List<BookInventory> findBookRecord(String category,String booklabel,String bookname);
    public Result saveBook(BookInventory bookInventory);
    public Boolean saveBatch(List<BookInventory> books, List<BookBuyRecord> bookrecords, BookBuyOrderNumber record);
    public Result updateBookRecord(BookInventory bookInventory);

    public Result delById(String id);
    public Result returnById(String id);
    public Result findBuyRecord(String time1,String time2,String supplier,String ordernumber);
    public Result removeBatchByIds(List<String> bookids);
}
