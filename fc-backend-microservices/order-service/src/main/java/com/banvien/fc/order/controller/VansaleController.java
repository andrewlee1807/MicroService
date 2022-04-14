package com.banvien.fc.order.controller;

import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.order.dto.EasyOrderSubmitDTO;
import com.banvien.fc.order.dto.SubmitOrderResponseDTO;
import com.banvien.fc.order.dto.vansale.ShoppingCartVansaleDTO;
import com.banvien.fc.order.service.VansaleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class VansaleController {

    @Autowired
    private VansaleService vansaleService;

    @PostMapping("/vansale/submit")
    public ResponseEntity submitOrder4Vansale(@RequestBody String body, HttpServletRequest request) {
        Long vansaleUserId = RestUtil.getUserIdFromToken(request);
        Gson gson = new Gson();

        //get list shopping cart of quicksell
        List<ShoppingCartVansaleDTO> shoppingCartVansaleDTOS = gson.fromJson(body, new TypeToken<ArrayList<ShoppingCartVansaleDTO>>() {
        }.getType());
        return ResponseEntity.ok(vansaleService.submitOrders4Quicksell(shoppingCartVansaleDTOS,vansaleUserId));
    }

    @PostMapping("/vansale/checkout")
    public ResponseEntity checkoutShoppingcartVansale(@RequestBody EasyOrderSubmitDTO easyOrderSubmitDTO) {
        return ResponseEntity.ok(vansaleService.checkoutShoppingcartVansale(easyOrderSubmitDTO));
    }
}
