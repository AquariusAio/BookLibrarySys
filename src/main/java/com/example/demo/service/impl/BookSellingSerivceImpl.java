package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Dao.entity.BookInventory;
import com.example.demo.Dao.entity.BookSelling;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.BookInventoryMapper;
import com.example.demo.mapper.auto.BookSellingMapper;
import com.example.demo.service.BookSellingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.print.Book;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/11&18:50
 */
@Service
public class BookSellingSerivceImpl extends ServiceImpl<BookSellingMapper, BookSelling>  implements BookSellingService {


    @Resource
    BookSellingMapper bookSellingMapper;
    @Resource
    BookInventoryMapper bookInventoryMapper;



    @Override
    public Result updateSellingBookinfo(BookSelling book){

        BookInventory Book = bookInventoryMapper.selectById(book.getId());
        Book.setSrc(book.getSrc());
        bookSellingMapper.updateById(book);
        bookInventoryMapper.updateById(Book);
        return Result.success();
    }

//    @Override
//    public List<BookSelling> findSaleBook(String category, String booklabel, String bookname,Integer salenum,String author) {
//
//        QueryWrapper<BookSelling> wrapper = new QueryWrapper<>();
//
//        if(salenum!=0)
//        {
//            wrapper.ge("salenum",salenum);
//        }
//        if(!"".equals(category)){
//            wrapper.like("category",category);
//        }
//        if(!"".equals(booklabel)){
//            wrapper.like("booklabel",booklabel);
//        }
//        if(!"".equals(bookname)){
//            wrapper.like("bookname",bookname);
//        }
//
//        if(!"".equals(author)){
//            wrapper.like("author",author);
//        }
//        List<BookSelling> booklist = bookSellingMapper.selectList(wrapper);
//        if(salenum!=0){
//            Collections.sort(booklist,new Comparator<BookSelling>() {
//                @Override
//                public int compare(BookSelling book1, BookSelling book2) {
//                    return book2.getSalenum()-book1.getSalenum();
//                }
//            });
//        }
//        return booklist;
//    }

    @Override
    public Result removeBatchByIds(List<String> bookids) {
        //QueryWrapper<BookSelling> wrapper = new QueryWrapper<>();
        List<BookInventory> booklist = bookInventoryMapper.selectBatchIds(bookids);

        for (BookInventory item: booklist)
        {
            item.setStatus("下架");
            bookInventoryMapper.updateById(item);
        }
        int num  = bookSellingMapper.deleteBatchIds(bookids);
        return Result.success(num);
    }

    @Override
    public Result removeById(String id) {
        BookSelling book = bookSellingMapper.selectById(id);
        BookInventory book1 = bookInventoryMapper.selectById(id);
        book1.setStatus("下架");
        bookInventoryMapper.updateById(book1);
        bookSellingMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result addBookOnSale(BookSelling book) {
        BookInventory book1 = bookInventoryMapper.selectById(book.getId());
        book1.setStatus("已上架");
        bookInventoryMapper.updateById(book1);
        bookSellingMapper.insert(book);
        return Result.success();
    }

    @Override
    public Result checkonebookinfo(String id) {
        BookSelling book = bookSellingMapper.selectById(id);
        return Result.success(book);
    }

}
