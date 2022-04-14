package com.banvien.fc.payment.controller;

import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.payment.dto.grabpay.*;
import com.banvien.fc.payment.entity.PaymentMethodEntity;
import com.banvien.fc.payment.repository.PaymentMethodRepository;
import com.banvien.fc.payment.service.OrderServiceProxy;
import com.banvien.fc.payment.service.PaymentService;

import static com.banvien.fc.payment.constant.PaymentConstant.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.DecoderException;
import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/partner/grabpay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderServiceProxy proxy;

    private Map<String, OrderInformationMobileDTO> mapStateAndCodeVerifier =
            new HashMap<String, OrderInformationMobileDTO>();

    @RequestMapping("/test")
    public String test() {
        return "successful";
    }

    @PostMapping(value = "/init")
    ResponseEntity<ResponseDTO<AuthenDTO>> chargeInit(@RequestBody RequestBodyDTO requestBodyDTO, HttpServletRequest servletRequest) {
        String chargeInitURL = "";
        try {
            chargeInitURL = paymentService.generateAuthURL(requestBodyDTO, servletRequest, mapStateAndCodeVerifier);
        } catch (NoSuchAlgorithmException | InvalidKeyException | DecoderException | IOException ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<AuthenDTO>(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex.getMessage(), new AuthenDTO()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<AuthenDTO>(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), new AuthenDTO()));
        }
        return ResponseEntity.ok(new ResponseDTO<AuthenDTO>(HttpStatus.SC_OK, "", new AuthenDTO(chargeInitURL)));
    }

    /**
     * @param code
     * @param state
     * @param status
     * @return
     */
    @RequestMapping("/callback")
    public ResponseEntity<String> getToken(@RequestParam(value = "code", required = false) String code,
                                                @RequestParam(value = "state", required = false) String state,
                                                @RequestParam(value = "status", required = false) String status,
                                                @RequestParam(value = "error", required = false) String error) {
        if (status != null && GRAB_SUCCESS_STATUS.equalsIgnoreCase(status)) {
            return ResponseEntity.ok(paymentService.completedPage(GRAB_SUCCESS_STATUS));
        }
        if ((error != null && GRAB_ERROR.equalsIgnoreCase(error)) || status != null && GRAB_FAILED_STATUS.equalsIgnoreCase(status)) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body(paymentService.completedPage(GRAB_FAILED_STATUS));
        }

        OrderInformationMobileDTO orderInformationMobileDTO = mapStateAndCodeVerifier.get(state);
        String codeVerifier = orderInformationMobileDTO.getCodeVerifier();

        String response = paymentService.getToken(code, state, codeVerifier, orderInformationMobileDTO);

        ObjectMapper oMapper = new ObjectMapper();
        TokenDTO tokenDTO = null;
        try {
            tokenDTO = oMapper.readValue(response, TokenDTO.class);
            // code demo
            String rp = paymentService.chargeComplete(tokenDTO.getAccess_token(), orderInformationMobileDTO);

            JSONObject obj = new JSONObject(rp);
            String stt = obj.getString("status");
            String txId = obj.getString("txID");
            // create CompletedBodyEasyOrderDTO
            CompletedBodyEasyOrderDTO dto = new CompletedBodyEasyOrderDTO();
            dto.setOrderCode(orderInformationMobileDTO.getTransactionCode());
            dto.setTxId(txId);
            dto.setToken(tokenDTO.getAccess_token());
            dto.setPaymentStatus(stt.toUpperCase());
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(dto);
            switch (stt) {
                case GRAB_SUCCESS_STATUS:
                    if ("SOURCE_EASY_ORDER".equalsIgnoreCase(orderInformationMobileDTO.getSource())) {
                        System.out.println("SOURCE_EASY_ORDER");
                        // Thanh cong thi create easyorder
                        proxy.saveOrderAfterPaid(body);
                    } else {
                        System.out.println("Another Source");
                        // Thanh cong thi create order
                        proxy.submitOrderFromGrabPay(state, txId, tokenDTO.getAccess_token());
                    }
                    mapStateAndCodeVerifier.remove(state);
                    return ResponseEntity.status(302)
                            .header("Location", paymentService.getGrabRidirectStatus() + stt).build();
                case GRAB_FAILED_STATUS:
                    // set status
                    if ("SOURCE_EASY_ORDER".equalsIgnoreCase(orderInformationMobileDTO.getSource())) {
                        System.out.println("SOURCE_EASY_ORDER");
                        // Thanh cong thi create easyorder
                        proxy.saveOrderAfterPaid(body);
                    }
                    mapStateAndCodeVerifier.remove(state);
                    return ResponseEntity.status(302)
                            .header("Location", paymentService.getGrabRidirectStatus() + stt).build();
                case GRAB_UNKNOWN_STATUS:
                default:
                    return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                            .body( "Default Status");
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | ParseException | DecoderException | JSONException | JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), null));
            throw new ResponseStatusException(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "Failed", e);
        }
    }

    @RequestMapping("/refund")
    public ResponseEntity<ResponseDTO> refund(@RequestBody RequestBodyDTO requestBodyDTO, HttpServletRequest servletRequest) {
        String rp = "";
        try {
            rp = paymentService.refund(requestBodyDTO, servletRequest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.SC_OK, rp, null));
    }

    @RequestMapping("/notification")
    public ResponseEntity<ResponseDTO> noti(@RequestBody WebHookDTO webHookDTO, HttpServletRequest request) {
        try {
            System.out.println("Webhook: ");
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(webHookDTO);
            System.out.println(body);
            System.out.println(request.getHeader("Authorization"));
            if ("Capture".equalsIgnoreCase(webHookDTO.getTxType())) {
                switch (webHookDTO.getStatus()) {
                    case "success":
                        break;
                    case "failed":
                        break;
                    default:
                        System.out.println("Notification: Unknown status");
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("Noti fail");
        }
        return null;
    }


    @RequestMapping(value = "/certificates")
    public ResponseEntity getCertificates(@RequestParam(value = "outletId") Long outletId) {
        CertificatesDTO dto = new CertificatesDTO();
        try {
            dto = paymentService.getCertificates(outletId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/easy/init")
    public AuthenDTO generateAuthen(@RequestParam("orderTemporaryId") Long orderTemporaryId, @RequestParam("currency") String currency) {
        try {
            AuthenDTO authenDTO = paymentService.generateAuthen(orderTemporaryId, mapStateAndCodeVerifier, currency);
            if (authenDTO == null) {
                //throw new BadRequestException(ErrorCodeMap.)
            }
            authenDTO.setAuthenUrl(authenDTO.getAuthenUrl());
            return authenDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Internal Error: Somethings went Wrong when generating the Grab Authen");
        }
    }
}