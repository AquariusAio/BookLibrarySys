package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Dao.entity.BookInventory;
import com.example.demo.Dao.entity.BookSelling;
import com.example.demo.conf.Result;
import com.example.demo.service.impl.BookSellingSerivceImpl;
import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/11&18:49
 */
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/bookselling")
public class BookSellingController {

    @Resource
    BookSellingSerivceImpl booksellingservice;

    @PutMapping
    public Result updateBookRecord(@RequestBody BookSelling book)
    {
        return booksellingservice.updateSellingBookinfo(book);
    }

    //店内购买查询书籍信息
    @GetMapping("/onebookinfo")
    public Result checkonebookinfo(@RequestParam(defaultValue = "") String id){
        return booksellingservice.checkonebookinfo(id);
    }

//    //用户界面展示书籍
//    @GetMapping("/saleinfo")
//    public Result loadBookToUser(@RequestParam(defaultValue = "") String booklabel,
//                                 @RequestParam(defaultValue = "") String category,
//                                 @RequestParam(defaultValue = "") String bookname,
//                                 @RequestParam(defaultValue = "") Integer salenum,
//                                 @RequestParam(defaultValue = "") String author) {
//        return Result.success(booksellingservice.findSaleBook(category,booklabel,bookname,salenum,author));
//    }

    //管理员界面展示上架书籍信息
    @GetMapping("/page")
    public IPage<BookSelling> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") float discount,
                                       @RequestParam(defaultValue = "") String bookname,
                                       @RequestParam(defaultValue = "") String category,
                                       @RequestParam(defaultValue = "") String booklabel,
                                       @RequestParam(defaultValue = "") String id) {

        IPage<BookSelling> page = new Page<>(pageNum,pageSize);

        QueryWrapper<BookSelling> queryWrapper = new QueryWrapper<>();

        if(discount == 2)
        {
            queryWrapper.lt("discount",1.00);
        }
        if(discount == 1)
        {
            queryWrapper.eq("discount",1.00);
        }
        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(category)){
            queryWrapper.like("category",category);
        }
        if(!"".equals(booklabel)){
            queryWrapper.eq("booklabel",booklabel);
        }
        if(!"".equals(id)){
            queryWrapper.like("id",id);
        }
        return booksellingservice.page(page,queryWrapper);
    }

    //用户界面展示上架书籍信息
    @GetMapping("/booksale")
    public IPage<BookSelling> findBookSale(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String bookname,
                                       @RequestParam(defaultValue = "") String category,
                                       @RequestParam(defaultValue = "") String booklabel,
                                           @RequestParam(defaultValue = "") String author,
                                       @RequestParam Integer salenum) {

        IPage<BookSelling> page = new Page<>(pageNum,pageSize);

        QueryWrapper<BookSelling> queryWrapper = new QueryWrapper<>();

        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(category)){
            queryWrapper.like("category",category);
        }
        if(!"".equals(booklabel)){
            queryWrapper.eq("booklabel",booklabel);
        }
        if(salenum!=0)
        {
            queryWrapper.ge("salenum",salenum);
        }
        if(!"".equals(author)){
            queryWrapper.like("author",author);
        }
//        System.out.println(pageNum+"  "+pageSize +" " +category);
        return booksellingservice.page(page,queryWrapper);
    }

    @PostMapping("onsale")
    public Result addBookOnSale(@RequestBody BookSelling book){
        return booksellingservice.addBookOnSale(book);
    }

    //批量下架书籍
    @PostMapping("remove/batch")
    public Result deleteBatch(@RequestBody List<String> bookids){
        return booksellingservice.removeBatchByIds(bookids);
    }

    //单本下架书籍
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        System.out.println("delete....");
        return booksellingservice.removeById(id);
    }
}
