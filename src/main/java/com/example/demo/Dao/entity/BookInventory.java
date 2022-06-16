package com.example.demo.Dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import freemarker.ext.beans.*;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @auther:Helen
 * @date 2022/6/2&11:43
 */

@TableName("sys_inventory")
@Data
@ToString
public class BookInventory  implements Serializable {

    @TableId
    private String id;
    private String booklabel;

    @TableField(exist = true)
    private String bookname;
    private int bookinventory;
    private String bookintroduction;
    private String category;
    private String supplier;
    private String src;
    private float bookpurchaseprice;
    private LocalDateTime recentbuytime;
    private int salenum;
    private String author;
    private String status;

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

    public String getSrc() {
        return src;
    }

    public String getCategory() {
        return category;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setBookpurchaseprice(float bookpurchaseprice) {
        this.bookpurchaseprice = bookpurchaseprice;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRecentbuytime(LocalDateTime recentbuytime) {
        this.recentbuytime = recentbuytime;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public float getBookpurchaseprice() {
        return bookpurchaseprice;
    }

    public String getBookintroduction() {
        return bookintroduction;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setBookintroduction(String bookintroduction) {
        this.bookintroduction = bookintroduction;
    }
}
