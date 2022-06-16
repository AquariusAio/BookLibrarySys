package com.example.demo.controller;

import com.example.demo.Dao.entity.Book;
import com.example.demo.conf.Result;
import com.example.demo.service.BookService;
import com.example.demo.service.BookShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Aquarius
 * @Date $ $
 **/
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
public class BookshelfController {
  @Autowired
  BookService bookService;

  @Autowired
  BookShelfService bookShelfService;

  @PostMapping("bookshelfadd/{username}")
  public Result addBooks(@RequestBody Book book, @PathVariable String username) {
    bookShelfService.addBookToShelf(book,username);
    return Result.success();
  }
  @PostMapping("bookshelfremove/{username}")
  public Result deleteBooks(@RequestBody Book book, @PathVariable String username) {
    bookShelfService.removeBookFromShelf(book,username);
    return Result.success();
  }
}
