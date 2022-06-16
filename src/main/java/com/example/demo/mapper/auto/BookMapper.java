package com.example.demo.mapper.auto;

import com.example.demo.Dao.entity.Book;
import com.example.demo.Dao.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component()
public interface BookMapper extends JpaRepository<Book,Integer> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
    List<Book> findAllById(int id);
}
