package com.example.demo.Dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @auther:Helen
 * @date 2022/6/13&12:29
 */

@TableName("sys_buyrecord")
@Data
@ToString
public class BookBuyRecord {

    @TableId
    private String id;
    private String booklabel;

    @TableField(exist = true)
    private String bookname;
    private int booknum;
    private String category;
    private String supplier;
    private float bookpurchaseprice;
    private LocalDateTime buytime;
    private String author;
    private String ordernumber;

    public LocalDateTime getBuytime() {
        return buytime;
    }

    public String getId() {
        return id;
    }

    public String getSupplier() {
        return supplier;
    }

    public float getBookpurchaseprice() {
        return bookpurchaseprice;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setBookpurchaseprice(float bookpurchaseprice) {
        this.bookpurchaseprice = bookpurchaseprice;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setBooklabel(String booklabel) {
        this.booklabel = booklabel;
    }

    public void setBooknum(int booknum) {
        this.booknum = booknum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBuytime(LocalDateTime buytime) {
        this.buytime = buytime;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }
}
