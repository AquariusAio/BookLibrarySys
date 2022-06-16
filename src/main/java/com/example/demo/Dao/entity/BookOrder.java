package com.example.demo.Dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther:Helen
 * @date 2022/6/5&19:19
 */
@TableName("sys_bookorder")
@Data
public class BookOrder implements Serializable {
    @TableId
    private String ordernumber;

    @TableField
    private String customername;
    private int customerid;
    private String bookname;
    private String bookid;
    private float singleprice;
    private Date ordertime;
    private int buynum;
    private float totalprice;
    private String phonenumber;
    private String address;
    private String tip;
    private float discount;
    private String category;
    private String label;

    public float getDiscount() {
        return discount;
    }

    public String getOrdernumber()
    {
        return ordernumber;
    }

    public String getAddress() {
        return address;
    }
    public String getBookid()
    {
        return bookid;
    }

    public float getTotalprice() {
        return totalprice;
    }
    public int getCustomerid()
    {
        return customerid;
    }

    public int getBuynum() {
        return buynum;
    }
    public String getBookname() {
        return bookname;
    }
    public String getTip() {
        return tip;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public float getSingleprice() {
        return singleprice;
    }
    public Date getOrdertime() {
        return ordertime;
    }

    public String getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }
    public void setOrdernumber(String ordernumber)
    {
        this.ordernumber = ordernumber;
    }

    public String getCustomername() {
        return customername;
    }
    public void setCustomerid(int customerid)
    {
        this.customerid = customerid;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }
    public void setDiscount(float discount) {
        this.discount = discount;
    }
    public void setSingleprice(float singleprice) {
        this.singleprice = singleprice;
    }
    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
}
