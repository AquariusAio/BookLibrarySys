package com.example.demo.Dao.entity;

/**
 * @Author Aquarius
 * @Date $ $
 **/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_customer")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //@Column(name = "id")
  Integer id;

  private String customername;

  private String password;

  private String address;

  private String telephone;

  private String gender;

  private Date regdate;

  private String membership;

  private float account;

  private String email;
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return customername;
  }

  public void setUsername(String username) {
    this.customername = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

