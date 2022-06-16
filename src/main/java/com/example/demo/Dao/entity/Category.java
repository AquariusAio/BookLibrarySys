package com.example.demo.Dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Category entity.
 *
 * @author Evan
 * @date 2019/4
 */
@Data
@Entity
@Table(name = "sys_eleccategory")
@ToString
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private Integer id;

    /**
     * Category name in Chinese.
     */
    private String name;
}
