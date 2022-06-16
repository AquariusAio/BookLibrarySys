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

@TableName("sys_buyordernumber")
@Data
@ToString
public class BookBuyOrderNumber {
    @TableId
    private String buyrecordordernumber;

    @TableField(exist = true)
    private LocalDateTime buytime;
    private String supplier;

    public void setRecentbuytime(LocalDateTime recentbuytime) {
        this.buytime = recentbuytime;
    }

    public LocalDateTime getBuytime() {
        return buytime;
    }

    public void setBuyrecordordernumber(String buyrecordordernumber) {
        this.buyrecordordernumber = buyrecordordernumber;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBuyrecordordernumber() {
        return buyrecordordernumber;
    }
}
