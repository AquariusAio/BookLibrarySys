package com.example.demo.service;

/**
 * @Author Aquarius
 * @Date $ $
 **/
import com.example.demo.Dao.entity.User;
import com.example.demo.mapper.auto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserMapper userDAO;

  public boolean isExist(String username) {
    User user = userDAO.findByCustomername(username);
    return null != user;
  }

  public User findByUsername(String username) {
    //登录判断
    return userDAO.findByCustomername(username);
  }

  public User get(String username, String password) {
    return userDAO.getByCustomernameAndPassword(username, password);
  }


  public void deleteById(int id) {
    userDAO.deleteById(id);
  }
}
