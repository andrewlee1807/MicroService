package com.banvien.fc.email.controller;

import com.banvien.fc.email.dto.SendMailDTO;
import com.banvien.fc.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String send(@RequestBody SendMailDTO dto) throws Exception{
        try {
            emailService.sendMessageByTemplate(dto);
            return "{status: true}";
        } catch (MessagingException ex) {
            return "{status: false}";
        }
    }
    @PostMapping("/promotion-end-notify")
    public String send(@RequestBody Long outletPromotionId) throws Exception{
        try {
            emailService.alertProtionEnd(outletPromotionId);
            return "{status: true}";
        } catch (Exception ex) {
            return "{status: false}";
        }
    }
}

