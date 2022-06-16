package com.example.demo.Dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther:Helen
 * @date 2022/5/30&23:53
 */
@TableName("sys_customer")
public class Customer implements Serializable {

    @TableId
    private  int id;

    @TableField
    private String customername;
    private String password;
    private String address;
    private String telephone;
    private String gender;
    private Date regdate;
    private String membership;
    private String email;
    private float account;
    //private String token;

    public String getCustomername() {
        return customername;
    }
    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getAccount() {
        return account;
    }

    public void setAccount(float account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }
    public Date getRegdate()
    {
        return regdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMembership() {
        return membership;
    }

    public String getGender() {
        return gender;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

//    public void setToken(String token) {
//        this.token = token;
//    }
}
