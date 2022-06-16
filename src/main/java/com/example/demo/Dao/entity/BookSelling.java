package com.example.demo.Dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther:Helen
 * @date 2022/6/11&17:15
 */
@TableName("sys_selling")
@Data
public class BookSelling implements Serializable {
    @TableId
    private String id;
    private String booklabel;

    @TableField(exist = true)
    private String bookname;
    private int bookinventory;
    private String bookintroduction;
    private String category;
    private String src;
    private float booksaleprice;
    private float bookpurchaseprice;
    private Date onsaletime;
    private int salenum;
    private float discount;
    private String author;

    public int getBookinventory() {
        return bookinventory;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setBookinventory(int bookinventory) {
        this.bookinventory = bookinventory;
    }

    public void setSalenum(int salenum) {
        this.salenum = salenum;
    }

    public int getSalenum() {
        return salenum;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public float getBooksaleprice() {
        return booksaleprice;
    }

    public String getBookintroduction() {
        return bookintroduction;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getBooklabel() {
        return booklabel;
    }

    public void setBooklabel(String booklabel) {
        this.booklabel = booklabel;
    }

    public String getBookname() {
        return bookname;
    }

    public String getSrc() {
        return src;
    }

    public float getBookpurchaseprice() {
        return bookpurchaseprice;
    }
}
