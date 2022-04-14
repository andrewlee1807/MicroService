package com.banvien.fc.order.utils;

import com.banvien.fc.order.dto.*;
import com.banvien.fc.order.entity.LoyaltyMemberEntity;
import com.banvien.fc.order.entity.ShoppingCartEntity;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShoppingCartBeanUtils {
    public static SubmitOrderResponseDTO messageWarningApproveCredit(SubmitOrderResponseDTO responseDTO, LoyaltyMemberEntity loyaltyMemberEntity, Double newOrderValue) {
        if (loyaltyMemberEntity != null && loyaltyMemberEntity.getTerms() != null && loyaltyMemberEntity.getTerms()) {
            Double creditLimit = loyaltyMemberEntity.getCreditLimit();
            Double totalDebt = loyaltyMemberEntity.getTotalDebt() != null ? loyaltyMemberEntity.getTotalDebt() : 0;
            Double creditWarningSetting = 100 - loyaltyMemberEntity.getCreditWarning() ;
            Timestamp paymentDueDate = loyaltyMemberEntity.getPaymentDueDate();
            Double creditBalance = creditLimit - totalDebt;

            Timestamp now = move2BeginTimeOfDay(new Timestamp(System.currentTimeMillis()));
            Double creditWarning = (creditLimit * creditWarningSetting) / 100;
            Double newTotalDebt = totalDebt + newOrderValue;

            if (newOrderValue <= creditBalance) {
                // due date valid
                if (paymentDueDate == null || paymentDueDate.after(now)) {
                    // hit warning
                    if (newTotalDebt > creditWarning) {
                        responseDTO.setSuccess(false);
                        responseDTO.setWarningType("ORANGE");
                        responseDTO.setResponseMessageKey("submit_order_hit_warning");
                        responseDTO.setDescription("New credit over threshold");
                    }
                } else {
                    // due date invalid
                    // hit warning
                    if (newTotalDebt > creditWarning) {
                        responseDTO.setSuccess(false);
                        responseDTO.setWarningType("RED");
                        responseDTO.setResponseMessageKey("submit_order_duedate_invalid_hit_warning");
                        responseDTO.setDescription("New credit over threshold and payment due date is invalid");
                    } else {
                        // not hit warning
                        responseDTO.setSuccess(false);
                        responseDTO.setWarningType("RED");
                        responseDTO.setResponseMessageKey("submit_order_duedate_invalid");
                        responseDTO.setDescription("Payment due date is invalid");
                    }
                }
            } else {
                // over balance
                // due date valid
                if (paymentDueDate == null || paymentDueDate.after(now)) {
                    responseDTO.setSuccess(false);
                    responseDTO.setWarningType("RED");
                    responseDTO.setResponseMessageKey("submit_order_over_balance");
                    responseDTO.setDescription("New debt over credit balance");
                } else {
                    // due date invalid
                    responseDTO.setSuccess(false);
                    responseDTO.setWarningType("RED");
                    responseDTO.setResponseMessageKey("submit_order_duedate_invalid_over_balance");
                    responseDTO.setDescription("New debt over credit balance and payment due date invalid");
                }
            }
        } else {
            responseDTO.setSuccess(false);
            responseDTO.setResponseMessageKey("submit_order_customer_not_active_credit_yet");
            responseDTO.setDescription("This customer select paid by credit, but unfortunately this customer was not active credit yet");
        }

        return responseDTO;
    }

    public static Timestamp move2BeginTimeOfDay(Timestamp input) {
        Timestamp res = null;
        if (input != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(input.getTime());
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
            res = new Timestamp(cal.getTimeInMillis());
        }

        return res;
    }

    public static ShoppingCartDTO entity2Dto(ShoppingCartEntity shoppingCartEntity) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setQuantity(shoppingCartEntity.getQuantity());
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(shoppingCartEntity.getCustomer().getCustomerId());
        shoppingCartDTO.setCustomer(customerDTO);
        shoppingCartDTO.setCreatedDate(shoppingCartEntity.getCreatedDate());
        ProductOutletSkuDTO productOutletSkuDTO = new ProductOutletSkuDTO();
        ProductOutletDTO productOutletDTO = new ProductOutletDTO();
        productOutletDTO.setProductOutletId(shoppingCartEntity.getProductOutletSku().getProductOutlet().getProductOutletId());
        productOutletDTO.setName(shoppingCartEntity.getProductOutletSku().getProductOutlet().getName());
        productOutletDTO.setDescription(shoppingCartEntity.getProductOutletSku().getProductOutlet().getDescription());
        productOutletSkuDTO.setProductOutlet(productOutletDTO);
        productOutletSkuDTO.setProductOutletSkuId(shoppingCartEntity.getProductOutletSku().getProductOutletSkuId());
        productOutletSkuDTO.setWeight(shoppingCartEntity.getProductOutletSku().getWeight());
        productOutletSkuDTO.setSalePrice(shoppingCartEntity.getProductOutletSku().getSalePrice());
        shoppingCartDTO.setProductOutletSku(productOutletSkuDTO);
        shoppingCartDTO.setDevice(shoppingCartEntity.getDevice());
        shoppingCartDTO.setTemporaryOrderCode(shoppingCartEntity.getTemporaryOrderCode());
        return shoppingCartDTO;
    }

    public static List<ShoppingCartDTO> entities2Dtos(List<ShoppingCartEntity> shoppingCartEntities) {
        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();
        for (ShoppingCartEntity temp : shoppingCartEntities) {
            shoppingCartDTOS.add(entity2Dto(temp));
        }
        return shoppingCartDTOS;
    }
}
