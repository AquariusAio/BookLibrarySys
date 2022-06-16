package com.example.demo.mapper.auto;

import com.example.demo.Dao.entity.BookShelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookShelfMapper extends JpaRepository<BookShelf, Integer> {
  List<BookShelf> findAllByUid(int uid);
}
