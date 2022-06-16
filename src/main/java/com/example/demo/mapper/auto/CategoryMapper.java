package com.example.demo.mapper.auto;

import com.example.demo.Dao.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component()
public interface CategoryMapper extends JpaRepository<Category, Integer> {

}
