package com.example.demo.controller;

/**
 * @Author Aquarius
 * @Date $ $
 **/
import com.example.demo.Dao.entity.Book;
import com.example.demo.conf.Result;
import com.example.demo.result.Result1;
import com.example.demo.service.BookService;
import com.example.demo.service.BookShelfService;
import com.example.demo.utils.StringUtils;
import com.example.demo.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
public class LibraryController {

  @Autowired
  BookService bookService;

  @Autowired
  BookShelfService bookShelfService;
  @GetMapping("/books")
  public Result listBooks() {
    return bookService.list();
  }


  @PostMapping("/admin/content/books")
  public Result addOrUpdateBooks(@RequestBody Book book) {
    System.out.println(book);
    System.out.println("12sad3");
    bookService.addOrUpdate(book);
    return Result.success();
  }

  @PostMapping("/admin/content/books/delete")
  public Result deleteBook(@RequestBody Book book) {
    System.out.println(book);
    bookService.deleteById(book.getId());
    return Result.success();
  }
  @PostMapping("/admin/content/books/deletemulti")
  public Result deleteMultiBook(@RequestBody List<Integer> books) {
    System.out.println("54578941");
    bookService.deleteMulti(books);
    return Result.success();
  }

  @GetMapping("/search")
  public Result searchResult(@RequestParam("keywords") String keywords) {

    if ("".equals(keywords)) {

      return bookService.list();
    } else {
      return bookService.Search(keywords);
    }
  }

  @GetMapping("/categories/{cid}/books")
  public Result listByCategory(@PathVariable("cid") int cid) {
    System.out.println(cid);
    if (0 != cid) {
      return bookService.listByCategory(cid);
    } else {
      return bookService.list();
    }
  }

  @PostMapping("/admin/content/books/covers")
  public String coversUpload(MultipartFile file) {
    String folder = "D:/workspace/img";
    File imageFolder = new File(folder);
    File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
        .substring(file.getOriginalFilename().length() - 4));
    if (!f.getParentFile().exists())
      f.getParentFile().mkdirs();
    try {
      file.transferTo(f);
      String imgURL = "http://localhost:9090/file/" + f.getName();
      return imgURL;
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }
  @GetMapping("/bookshelf")
  public Result listBookShelf(@RequestParam("username") String username) {
    System.out.println("456123");
    return bookShelfService.listBookShelfByUser(username);
  }

}

