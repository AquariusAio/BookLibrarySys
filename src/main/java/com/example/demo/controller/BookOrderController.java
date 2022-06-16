package com.example.demo.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Dao.entity.BookInventory;
import com.example.demo.Dao.entity.BookOrder;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.BookOrderMapper;
import com.example.demo.service.BookOrderService;
import com.example.demo.service.impl.BookOrderServiceImpl;
import com.example.demo.service.impl.LoginServiceImpl;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @auther:Helen
 * @date 2022/6/5&19:20
 */

@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/order")
public class BookOrderController {

    @Resource
    BookOrderServiceImpl bookorderservice;


    @GetMapping("/number")
    public Result getOrderNumber()
    {
        return Result.success(bookorderservice.getOrderNumber());
    }


    @PostMapping("/payorder")
    public Result saveOrder(@RequestBody BookOrder bookorder) throws AuthenticationException
    {
        if(bookorder.getOrdernumber()==null)
        {
            bookorder.setOrdernumber(bookorderservice.getOrderNumber());
        }
        return bookorderservice.saveOrder(bookorder);
    }

    @GetMapping("/export1")
    public void export(HttpServletResponse response) throws Exception {

        List<BookOrder> list = bookorderservice.list();
        //通过工具类writer写出到磁盘路径
        //ExcelWriter writer = ExcelUtil.getWriter(fileUploadPath + "/新进图书,xlsx");
        //在内存操作，写出到浏览器

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("ordernumber", "订单号");
        writer.addHeaderAlias("customername", "用户名");
        writer.addHeaderAlias("customerid", "用户ID");
        writer.addHeaderAlias("bookid", "图书ID");
        writer.addHeaderAlias("bookname", "书名");
        writer.addHeaderAlias("singleprice", "单价");
        writer.addHeaderAlias("discount", "折扣");
        writer.addHeaderAlias("buynum", "购买数量");
        writer.addHeaderAlias("totalprice", "总价");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("phonenumber", "电话");
        writer.addHeaderAlias("tip", "备注");
        writer.addHeaderAlias("ordertime", "下单时间");
        //一次性写出list
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename = URLEncoder.encode("订单信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename ="+filename+".xlsx");
        ServletOutputStream out  = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    //管理员的订单
    @GetMapping("/page")
    public IPage<BookOrder> findPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String customername,
                                     @RequestParam(defaultValue = "") String ordernumber,
                                     @RequestParam(defaultValue = "") String bookname,
                                     @RequestParam(defaultValue = "") String label,
                                     @RequestParam(defaultValue = "") String category,
                                     @RequestParam(defaultValue = "") String time1,
                                     @RequestParam(defaultValue = "") String time2){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        IPage<BookOrder> page = new Page<>(pageNum,pageSize);
        QueryWrapper<BookOrder> queryWrapper = new QueryWrapper<>();
        System.out.println("page....");
        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(ordernumber)){
            queryWrapper.like("ordernumber",ordernumber);
        }
        if(!"".equals(customername)){
            queryWrapper.like("customername",customername);
        }
        if(!"".equals(label)){
            queryWrapper.like("label",label);
        }
        if(!"".equals(category)){
            queryWrapper.like("category",category);
        }
        if(!"".equals(time1)){
            LocalDateTime Time1 = LocalDateTime.parse(time1,df);
            queryWrapper.ge("ordertime",Time1);
        }
        if(!"".equals(time2)){
            LocalDateTime Time2 = LocalDateTime.parse(time2,df);
            queryWrapper.le("ordertime",Time2);
        }
        return bookorderservice.page(page,queryWrapper);
    }


    //批量用户删除订单
    @PostMapping("remove/batch")
    public Result deleteBatch(@RequestBody List<String> ordernumbers){
        return bookorderservice.removeBatchByIds(ordernumbers);
    }

}
