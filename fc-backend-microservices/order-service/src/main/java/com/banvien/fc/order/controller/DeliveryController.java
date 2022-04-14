package com.banvien.fc.order.controller;

import com.banvien.fc.common.util.GoogleLocationUtil;
import com.banvien.fc.order.dto.delivery.*;
import com.banvien.fc.order.dto.rules.Enum.ApiStatusCode;
import com.banvien.fc.order.repository.AppSettingRepository;
import com.banvien.fc.order.repository.OutletRepository;
import com.banvien.fc.order.repository.PaymentMethodRepository;
import com.banvien.fc.order.service.DeliveryService;
import com.banvien.fc.order.utils.CoreConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order/delivery")
public class DeliveryController extends ApplicationObjectSupport {

    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping("/getDeliveryAndPayment")
    public ResponseEntity getDeliveryAndPayment(@RequestBody EasyOrderRequestDTO easyOrderRequestDTO,
                                                HttpServletRequest request) {
        return ResponseEntity.ok(deliveryService.getDeliveryAndPayment(easyOrderRequestDTO, request.getLocale()));
    }
}
