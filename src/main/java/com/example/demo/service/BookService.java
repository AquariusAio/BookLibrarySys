package com.example.demo.service;

import com.example.demo.Dao.entity.Book;
import com.example.demo.Dao.entity.Category;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    @Autowired
    private BookMapper bookDAO;
    @Autowired
    private CategoryService categoryService;

    public Result list() {
        List<Book> books;
        // String key = "booklist";
        // Object bookCache = redisService.get(key);
        //
        // if (bookCache == null) {
        //     Sort sort = new Sort(Sort.Direction.DESC, "id");
        //     books = bookDAO.findAll(sort);
        //     redisService.set(key, books);
        // } else {
        //     books = CastUtils.objectConvertToList(bookCache, Book.class);
        // }
        //Sort sort = new Sort(Sort.Direction.DESC, "id");
        books = bookDAO.findAll();
        System.out.println(books);
        return Result.success(books);
    }

//    直接用注解实现缓存
//    @Cacheable(value = RedisConfig.REDIS_KEY_DATABASE)
//    public List<Book> list() {
//        List<Book> books;
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        books = bookDAO.findAll(sort);
//        return books;
//    }

    public void addOrUpdate(Book book) {

        bookDAO.save(book);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        bookDAO.deleteById(id);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Result listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return Result.success(bookDAO.findAllByCategory(category));
    }

    public Result Search(String keywords) {
        System.out.println(Result.success(bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%')));
        return Result.success(bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%'));
    }

  public void deleteMulti(List<Integer> books) {
        System.out.println(books);
        for(int book:books){
            deleteById(book);
        }
        return ;
  }
}
