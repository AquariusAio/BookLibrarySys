package com.example.demo.Dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * @Author Aquarius
 * @Date $ $
 **/
@Data
@Entity
@Table(name = "sys_bookshelf")
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})

public class BookShelf {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int uid;

    private int bid;

  public int getuid() {
    return uid;
  }

    public int getBid() {
      return this.bid;
    }
}
