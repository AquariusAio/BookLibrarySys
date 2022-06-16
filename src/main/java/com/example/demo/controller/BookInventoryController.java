package com.example.demo.controller;

/**
 * @auther:Helen
 * @date 2022/6/2&11:35
 */

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.Dao.entity.BookBuyOrderNumber;
import com.example.demo.Dao.entity.BookBuyRecord;
import com.example.demo.Dao.entity.BookInventory;
import com.example.demo.Dao.entity.BookSelling;
import com.example.demo.conf.Result;
import com.example.demo.service.impl.BookInventoryServiceImpl;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.jsf.el.WebApplicationContextFacesELResolver;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
@RestController
@RequestMapping("/book")
public class BookInventoryController {

    //@Value("#{config['server.port']}")
    private static final String port = "9090";

    private static final String ip = "http://localhost";


    @Autowired
    BookInventoryServiceImpl bookinventoryservice;

    @PostMapping("/images/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        /**
         * 上传接口
         */
       String oringinalFilename = file.getOriginalFilename();
       //       定义文件的唯一标识
       String flag = IdUtil.fastSimpleUUID();
       String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/images/" +flag +"_"+ oringinalFilename;

       FileUtil.writeBytes(file.getBytes(),rootFilePath);

       return Result.success(ip + ":" + port + "/book/load/" + flag);//返回url
    }

    @GetMapping("/load/{flag}")
    public void getFiles(HttpServletResponse response,@PathVariable String flag){
        /**
         * 下载接口
         */
        OutputStream os;
        String baseFilePath = System.getProperty("user.dir") + "/src/main/resources/images/";//下载文件的根路径
        List<String> fileNames = FileUtil.listFileNames(baseFilePath);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(fileName)){

                response.addHeader("Content-Disposition","attachment;filename =" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");

                byte[] bytes = FileUtil.readBytes(baseFilePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }
        catch(Exception e){
            System.out.println("图片下载失败");
        }

    }

    @PutMapping
    public Result updateBookRecord(@RequestBody BookInventory book)
    {
        System.out.println(book.getSrc());
        return bookinventoryservice.updateBookRecord(book);
    }


    @GetMapping("/page")
    public IPage<BookInventory> findPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam(defaultValue = "") String bookname,
                                         @RequestParam(defaultValue = "") String status,
                                         @RequestParam(defaultValue = "") String category,
                                         @RequestParam(defaultValue = "") String booklabel,
                                         @RequestParam(defaultValue = "") String id) {

        IPage<BookInventory> page = new Page<>(pageNum,pageSize);

        QueryWrapper<BookInventory> queryWrapper = new QueryWrapper<>();
        System.out.println("page....");
        if(!"".equals(bookname)){
            queryWrapper.like("bookname",bookname);
        }
        if(!"".equals(status)){
            queryWrapper.like("status",status);
        }
        if(!"".equals(category)){
            queryWrapper.like("category",category);
        }
        if(!"".equals(booklabel)){
            queryWrapper.like("booklabel",booklabel);
        }
        if(!"".equals(id)){
            queryWrapper.like("id",id);
        }
        System.out.println(bookinventoryservice.page(page,queryWrapper).getRecords());
        return bookinventoryservice.page(page,queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {

        List<BookInventory> list = bookinventoryservice.list();
        //通过工具类writer写出到磁盘路径
        //ExcelWriter writer = ExcelUtil.getWriter(fileUploadPath + "/新进图书,xlsx");
        //在内存操作，写出到浏览器

        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "图书编号");
        writer.addHeaderAlias("booklabel", "图书标签");
        writer.addHeaderAlias("bookname", "书名");
        writer.addHeaderAlias("author", "作者");
        writer.addHeaderAlias("category", "图书类别");
        writer.addHeaderAlias("src", "图书封面");
        writer.addHeaderAlias("bookintroduction", "图书简介");
        writer.addHeaderAlias("status", "状态");
        writer.addHeaderAlias("bookpurchaseprice", "进货单价");
        writer.addHeaderAlias("salenum", "销量");
        writer.addHeaderAlias("bookinventory", "库存量");
        writer.addHeaderAlias("recentbuytime", "进货时间");
        writer.addHeaderAlias("supplier", "供货商");
        //一次性写出list
        writer.write(list,true);


        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename = URLEncoder.encode("库存信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename ="+filename+".xlsx");
        ServletOutputStream out  = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    @ResponseBody
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<BookInventory> books = reader.readAll(BookInventory.class);

        // 方式2：忽略表头的中文，直接读取表的内容 明确每一列的内容是什么
        List<List<Object>> list = reader.read(1);
        List<BookInventory> books = CollUtil.newArrayList();
        List<BookBuyRecord> bookbuyrecords = CollUtil.newArrayList();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        BookBuyOrderNumber record = new BookBuyOrderNumber();
        int i = 0;

        for (List<Object> row : list) {
            BookInventory book = new BookInventory();
            BookBuyRecord buyrecord = new BookBuyRecord();
            book.setId(row.get(0).toString());
            buyrecord.setId(row.get(0).toString());
            book.setBooklabel(row.get(1).toString());
            buyrecord.setBooklabel(row.get(1).toString());
            book.setBookname(row.get(2).toString());
            buyrecord.setBookname(row.get(2).toString());
            book.setAuthor(row.get(3).toString());
            buyrecord.setAuthor(row.get(3).toString());
            book.setCategory(row.get(4).toString());
            buyrecord.setCategory(row.get(4).toString());
            book.setSrc(row.get(5).toString());
            book.setBookintroduction(row.get(6).toString());
            book.setBookpurchaseprice(Float.valueOf(row.get(7).toString()));
            buyrecord.setBookpurchaseprice(Float.valueOf(row.get(7).toString()));
            book.setSalenum(Integer.valueOf(row.get(8).toString()));
            book.setBookinventory(Integer.valueOf(row.get(9).toString()));
            buyrecord.setBooknum(Integer.valueOf(row.get(9).toString()));
            LocalDateTime localDateTime = LocalDateTime.parse(row.get(10).toString(),df);
            book.setRecentbuytime(localDateTime);
            buyrecord.setBuytime(localDateTime);
            book.setStatus("未上架");
            book.setSupplier(row.get(11).toString());
            buyrecord.setSupplier(row.get(11).toString());
            buyrecord.setOrdernumber(row.get(12).toString());
            books.add(book);
            bookbuyrecords.add(buyrecord);
            if(i==0){
                record.setBuyrecordordernumber(row.get(12).toString());
                record.setRecentbuytime(localDateTime);
                record.setSupplier(row.get(11).toString());
                i = 1;
            }
        }
        if(bookinventoryservice.saveBatch(books,bookbuyrecords,record)){
            i = 0;
            return true;
        }
        return false;
    }


    //删除库存
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        return bookinventoryservice.delById(id);
    }
    //恢复到待上架
    @PutMapping("/{id}")
    public Result returnInventory(@PathVariable String id) {
        return bookinventoryservice.returnById(id);
    }

    @GetMapping("/buyrecord")
    public Result findBuyRecord(@RequestParam(defaultValue = "") String time1,
                                @RequestParam(defaultValue = "") String time2,
                                @RequestParam(defaultValue = "") String supplier,
                                @RequestParam(defaultValue = "") String ordernumber) {
        return bookinventoryservice.findBuyRecord(time1,time2,supplier,ordernumber);
    }
    //批量删除库存
    @PostMapping("remove/batch")
    public Result deleteBatch(@RequestBody List<String> bookids){
        return bookinventoryservice.removeBatchByIds(bookids);
    }
}