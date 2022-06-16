package com.example.demo;

import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DemoApplicationTests {

//    @Autowired
//    JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
//        Long aL = jdbcTemplate.queryForObject("select count(*) from sys_employee",Long.class);
//        System.out.println("test OracleConnect :"+aL+"条");
    }

}
