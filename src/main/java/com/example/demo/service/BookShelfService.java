package com.example.demo.service;

import com.example.demo.Dao.entity.Book;
import com.example.demo.Dao.entity.BookShelf;
import com.example.demo.Dao.entity.User;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.BookMapper;
import com.example.demo.mapper.auto.BookShelfMapper;
import com.example.demo.mapper.auto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Aquarius
 * @Date $ $
 **/
@Service
public class BookShelfService {
  @Autowired
  UserService userService;
  @Autowired
  BookShelfMapper bookShelfMapper;
  @Autowired
  BookMapper bookDAO;
  public Result listBookShelfByUser(String username){

    System.out.println(username);
    User user = userService.findByUsername(username);
    System.out.println(user);
    List<BookShelf> books= bookShelfMapper.findAllByUid(user.getId());
    System.out.println(books);
    List<Book> bookshelf = new ArrayList<>();
    for(BookShelf book:books) {
      for(Book item:bookDAO.findAllById(book.getBid()))
      {
        bookshelf.add(item);
      }
    }
    System.out.println(bookshelf);
    return Result.success(bookshelf);

  }

  public void addBookToShelf(Book book,String username) {
    User user=userService.findByUsername(username);
    List<BookShelf> current=bookShelfMapper.findAll();
    for(BookShelf item:current){
      if(item.getBid()==book.getId())
      {
        return ;
      }
    }
    BookShelf bookShelf=new BookShelf();
    bookShelf.setBid(book.getId());
    bookShelf.setUid(user.getId());
    bookShelf.setId(0);
    bookShelfMapper.save(bookShelf);
  }

  public void removeBookFromShelf(Book book, String username) {
    User user=userService.findByUsername(username);
    List<BookShelf> bookShelfList = bookShelfMapper.findAllByUid(user.getId());
    for(BookShelf item:bookShelfList){
      if(item.getBid()==book.getId()){
        bookShelfMapper.deleteById(item.getId());
      }
    }
  }
}
