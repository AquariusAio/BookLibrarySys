package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Dao.entity.*;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.*;
import com.example.demo.service.BookInventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @auther:Helen
 * @date 2022/6/6&18:04
 */
@Service
public class BookInventoryServiceImpl extends ServiceImpl<BookInventoryMapper, BookInventory>  implements BookInventoryService
{
    @Resource
    BookInventoryMapper bookInventoryMapper;
    @Resource
    BookSellingMapper bookSellingMapper;
    @Resource
    BookBuyOrderNumberMapper bookBuyOrderNumberMapper;
    @Resource
    BookBuyRecordMapper bookBuyRecordMapper;


    @Override
    public List<BookInventory> findBookRecord(String category,String booklabel,String bookname) {
        QueryWrapper<BookInventory> wrapper = new QueryWrapper<>();
        if(!"".equals(category)){
            wrapper.like("category",category);
        }
        if(!"".equals(booklabel)){
            wrapper.like("booklabel",booklabel);
        }
        if(!"".equals(bookname)){
            wrapper.like("bookname",bookname);
        }
        List<BookInventory> booklist = bookInventoryMapper.selectList(wrapper);
        return booklist;
    }


    @Override
    public Result saveBook(BookInventory bookInventory) {
        bookInventoryMapper.insert(bookInventory);
        return Result.success();
    }

    @Override
    public Boolean saveBatch(List<BookInventory> books,List<BookBuyRecord> bookrecords, BookBuyOrderNumber record) {

        for (BookInventory book: books) {
            bookInventoryMapper.insert(book);
        }
        for (BookBuyRecord bookrecord: bookrecords) {
            bookBuyRecordMapper.insert(bookrecord);
        }
        bookBuyOrderNumberMapper.insert(record);
        return true;
    }

    @Override
    public Result updateBookRecord(BookInventory bookInventory) {
        bookInventoryMapper.updateById(bookInventory);
        return Result.success();
    }

    @Override
    public Result delById(String id) {
        bookInventoryMapper.deleteById(id);
        return Result.success("删除成功");
    }
    @Override
    public Result returnById(String id) {
        BookInventory book = bookInventoryMapper.selectById(id);
        book.setStatus("未上架");
        bookInventoryMapper.updateById(book);
        return Result.success("恢复成功");
    }

    @Override
    public Result findBuyRecord(String time1, String time2, String supplier, String ordernumber) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<BookBuyRecord> booklists = new ArrayList<>();
        QueryWrapper<BookBuyOrderNumber> wrapper = new QueryWrapper<>();


        if(!"".equals(time1)){
            LocalDateTime Time1 = LocalDateTime.parse(time1,df);
            wrapper.ge("buytime",Time1);
        }
        if(!"".equals(time2)){
            LocalDateTime Time2 = LocalDateTime.parse(time2,df);
            wrapper.le("buytime",Time2);
        }
        if(!"".equals(supplier)){
            wrapper.like("supplier",supplier);
        }
        if(!"".equals(ordernumber)){
            wrapper.eq("buyrecordordernumber",ordernumber);
        }
        List<BookBuyOrderNumber> ordernumbers = bookBuyOrderNumberMapper.selectList(wrapper);
        for(BookBuyOrderNumber item :ordernumbers){
            QueryWrapper<BookBuyRecord> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("ordernumber",item.getBuyrecordordernumber());
            List<BookBuyRecord> booklist = bookBuyRecordMapper.selectList(wrapper1);
            booklists.addAll(booklist);
        }
//        QueryWrapper<BookBuyRecord> wrapper1 = new QueryWrapper<>();
//        List<BookBuyRecord> booklists = bookBuyRecordMapper.selectList(wrapper1);
        return Result.success(booklists);
    }

    @Override
    public Result removeBatchByIds(List<String> bookids) {

        int num  = bookInventoryMapper.deleteBatchIds(bookids);
        return Result.success(num);
    }
}
