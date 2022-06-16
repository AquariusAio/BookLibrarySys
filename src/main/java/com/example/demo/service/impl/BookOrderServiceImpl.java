package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Dao.entity.*;
import com.example.demo.conf.Result;
import com.example.demo.mapper.auto.*;
import com.example.demo.service.BookOrderService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @auther:Helen
 * @date 2022/6/5&20:53
 */
@Service
public class BookOrderServiceImpl extends ServiceImpl<BookOrderMapper, BookOrder> implements BookOrderService {


    @Resource
    BookOrderMapper bookordermapper;

    @Resource
    CustomerMapper customermapper;

    @Resource
    BookInventoryMapper  bookinventorymapper;

    @Resource
    BookSellingMapper booksellingmapper;
    @Resource
    CustomerBookOrderMapper customerbookordermapper;


    private static int sequence = 0;
    private static int length = 6;

    public static String addLeftZero(String s, int length) {
        // StringBuilder sb=new StringBuilder();
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                        "Numeric value is larger than intended length: " + s
                                + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);
    }
    @Override
    public synchronized String getOrderNumber() {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String s = Integer.toString(sequence);
        return datetime +addLeftZero(s, length);
    }

    @Override
    public Result getAllOrder() {
        return null;
    }

    @Override
    public Result saveOrder(BookOrder bookorder) {
        BookOrder res = bookordermapper.selectOne(Wrappers.<BookOrder>lambdaQuery().eq(BookOrder::getOrdernumber,bookorder.getOrdernumber()));
        if(res != null)
        {
            return Result.error("-1","请重新操作");
        }
        BookInventory bookInventory = bookinventorymapper.selectById(bookorder.getBookid());
        BookSelling bookSelling = booksellingmapper.selectById(bookorder.getBookid());
        if(bookSelling.getBookinventory() <bookorder.getBuynum())
        {
            return Result.error("-1","库存不足");
        }

        if(bookorder.getCustomerid()==-1&&bookorder.getCustomername().equals("非余额支付"))
        {
            System.out.println(bookorder.getCustomerid());
            bookInventory.setBookinventory(bookInventory.getBookinventory() - bookorder.getBuynum());
            bookInventory.setSalenum(bookInventory.getSalenum()+bookorder.getBuynum());
            bookinventorymapper.updateById(bookInventory);
            bookSelling.setBookinventory(bookSelling.getBookinventory() - bookorder.getBuynum());
            bookSelling.setSalenum(bookSelling.getSalenum()+bookorder.getBuynum());
            booksellingmapper.updateById(bookSelling);
            bookordermapper.insert(bookorder);
            return Result.success();
        }

        Customer customer = customermapper.selectById(bookorder.getCustomerid());
        if(customer.getAccount()<bookorder.getTotalprice())
        {
            return Result.error("-1","余额不足，请充值后重新购买");
        }
        customer.setAccount(customer.getAccount()-bookorder.getTotalprice());
        customermapper.updateById(customer);
        bookInventory.setBookinventory(bookInventory.getBookinventory()-bookorder.getBuynum());
        bookInventory.setSalenum(bookInventory.getSalenum()+bookorder.getBuynum());
        bookinventorymapper.updateById(bookInventory);
        bookSelling.setBookinventory(bookSelling.getBookinventory() - bookorder.getBuynum());
        bookSelling.setSalenum(bookSelling.getSalenum()+bookorder.getBuynum());
        booksellingmapper.updateById(bookSelling);
        bookordermapper.insert(bookorder);

        if(bookorder.getCustomerid()!=-1){
            CustomerBookOrder bookorder1 = new CustomerBookOrder();
            bookorder1.setCustomername(bookorder.getCustomername());
            bookorder1.setCustomerid(bookorder.getCustomerid());
            bookorder1.setOrdernumber(bookorder.getOrdernumber());
            bookorder1.setAddress(bookorder.getAddress());
            bookorder1.setBookid(bookorder.getBookid());
            bookorder1.setBookname(bookorder.getBookname());
            bookorder1.setBuynum(bookorder.getBuynum());
            bookorder1.setPhonenumber(bookorder.getPhonenumber());
            bookorder1.setTotalprice(bookorder.getTotalprice());
            bookorder1.setTip(bookorder.getTip());
            bookorder1.setSingleprice(bookorder.getSingleprice());
            bookorder1.setOrdertime(bookorder.getOrdertime());
            bookorder1.setDiscount(bookorder.getDiscount());
            bookorder1.setCategory(bookorder.getCategory());
            bookorder1.setLabel(bookorder.getLabel());
            customerbookordermapper.insert(bookorder1);
        }
        return Result.success(customer);
    }


    @Override
    public Result removeBatchByIds(List<String> ordernumbers) {

        int num  = bookordermapper.deleteBatchIds(ordernumbers);
        System.out.println(ordernumbers);
        return Result.success(num);
    }
}