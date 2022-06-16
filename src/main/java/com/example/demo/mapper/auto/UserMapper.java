package com.example.demo.mapper.auto;

import com.example.demo.Dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component()
public interface UserMapper extends JpaRepository<User,Integer> {
  User findByCustomername(String username);

  User getByCustomernameAndPassword(String username,String password);
}

