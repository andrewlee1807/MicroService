package com.banvien.fc.payment.service;

import com.banvien.fc.payment.dto.grabpay.OrderInformationMobileDTO;
import com.banvien.fc.payment.dto.grabpay.ShoppingCartRequestBodyDTO;
import com.banvien.fc.payment.dto.grabpay.SubmitOrderResponseDTO;
import javassist.tools.rmi.ObjectNotFoundException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(name = "order-service")
//@RequestMapping("/order/easy")
public interface OrderServiceProxy {
//    @PostMapping("/mobile/shoppingCart/")
//    public String submitOrder();

    @PostMapping("/order/easy/shoppingcart/updates")
    public ResponseEntity<Map<String, Object>> updateShoppingCart(@RequestBody ShoppingCartRequestBodyDTO dto);

    @PostMapping("/order/easy/service/shoppingCart/submit")
    public ResponseEntity<SubmitOrderResponseDTO> submitOrder4Mobile(@RequestBody OrderInformationMobileDTO dto, @RequestParam(value = "userId") Long userId);

    @GetMapping("/order/easy/get")
    public ResponseEntity<Map<String,String>> getOrderByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping("/order/easy/update/grabtransaction")
    public ResponseEntity<Integer> updateGrabTransaction(@RequestParam("grabTxId") String grabTxId, @RequestParam("token") String token, @RequestParam("orderOutletId") Long orderOutletId);

    @PostMapping("/order/mobile/shoppingCart/grab/checkout")
    public Map<String, Object> checkoutShoppingCart(@RequestBody OrderInformationMobileDTO dto) throws JSONException, ObjectNotFoundException;

    @PostMapping("/order/mobile/shoppingCart/grab/submit")
    public Object submitOrderFromGrabPay(@RequestParam String state,
                                         @RequestParam String txId,
                                         @RequestParam String token);

    @RequestMapping("/order/temporary")
    public Map<String, Object> getAmountOrderTemporary(@RequestParam("orderTemporaryId") Long orderTemporaryId);

    @RequestMapping("/order/easy/update/paymentstatus")
    public ResponseEntity<Integer> updateGrabPaymentStatus(@RequestParam("paymentStatus") String paymentStatus, @RequestParam("orderOutletCode") String orderOutletCode);

    @PostMapping("/order/easy/grabPay/paid")
    public ResponseEntity<Map<String, Object>> saveOrderAfterPaid(@RequestBody String body);

    @RequestMapping("/order/get")
    public Map<String, Object> getOrderOutletInfo(@RequestParam("code") String code);
}
