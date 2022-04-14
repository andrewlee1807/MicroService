package com.banvien.fc.promotion;


import com.banvien.fc.promotion.dto.rules.DiscountDTO;
import com.banvien.fc.promotion.dto.rules.GroupTypeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Product {
    public Long id;
    public Double price;
    public Integer quantity;
    public Long catId;
    public Long brandId;
}

class Order {
    public ArrayList<Product> products;
    public Double totalPrice;
    public Integer totalQuantity;
}

//@RunWith(SpringRunner.class)
public class FormatJS2Engine {
    @Test
    public void test() throws Exception {
        Double totalPrice = 0.0;
        Integer totalQuantity = 0;

        Product product = new Product();
        product.brandId = 1L;
        product.catId = 1L;
        product.id = 2L;
        product.quantity = 12;
        product.price = 12.0;

        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);
        products.add(product);

        // Create a OrderObj Object
        Order orderObj = new Order();
        orderObj.products = products;
        orderObj.totalPrice = totalPrice;
        orderObj.totalQuantity = totalQuantity;


        ObjectMapper oMapper = new ObjectMapper();
        String tmp = oMapper.writeValueAsString(orderObj); // obj 2 json

        Gson gsonBuilder = new GsonBuilder().create();
        String productsJson = gsonBuilder.toJson(products);

//        JsonObject order = new JsonObject();
//        order.addProperty("totalPrice", totalPrice);
//        order.addProperty("totalQuantity", totalQuantity);
//        order.addProperty("products", tmp);


        System.out.println("\n\n\n\n");
//        System.out.println(order);
        System.out.println(tmp);
        Order dto = oMapper.readValue(tmp, Order.class); // json 2 obj
        System.out.println(dto.toString());


    }

    public static final String OTP_DIGIT_CHARSET = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Test
    public void test1(){
        StringBuffer sb = new StringBuffer();
        int length = 8;
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(OTP_DIGIT_CHARSET.charAt(rand.nextInt(OTP_DIGIT_CHARSET.length())));
        }
        System.out.println(sb);
    }

    @Test
    public void test2(){
        List<GroupTypeDTO> categoryGr = new ArrayList<>();
        categoryGr.add(new GroupTypeDTO(1L, 11, 11.D));
        categoryGr.add(new GroupTypeDTO(2L, 11, 11.D));
        categoryGr.add(new GroupTypeDTO(3L, 11, 11.D));
        categoryGr.add(new GroupTypeDTO(4L, 11, 11.D));
        categoryGr.add(new GroupTypeDTO(5L, 11, 11.D));

        for (GroupTypeDTO categoryType : categoryGr){
                if (5L == categoryType.getId()){
                    categoryType.setAmount(categoryType.getAmount() + 10.0);
                    break;
                }
        }

        for (GroupTypeDTO categoryType : categoryGr){
            System.out.println(categoryType.toString());
        }

    }
}
