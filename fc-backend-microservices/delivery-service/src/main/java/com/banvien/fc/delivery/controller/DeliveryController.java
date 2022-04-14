package com.banvien.fc.delivery.controller;

import com.banvien.fc.delivery.dto.grabexpress.*;
import com.banvien.fc.delivery.dto.grabexpress.platformdto.QuotesPlatformDTO;
import com.banvien.fc.delivery.dto.grabexpress.requestdto.DeliveryRequestDTO;
import com.banvien.fc.delivery.dto.grabexpress.requestdto.QuotesRequestDTO;
import com.banvien.fc.delivery.dto.grabexpress.requestdto.WebhookRequestDTO;
import com.banvien.fc.delivery.dto.grabexpress.responsedto.DeliveryResponseDTO;
import com.banvien.fc.delivery.dto.grabexpress.responsedto.ErrorResponseDTO;
import com.banvien.fc.delivery.dto.grabexpress.responsedto.QuotesResponseDTO;
import com.banvien.fc.delivery.service.DeliveryService;
import com.banvien.fc.delivery.service.OrderServiceProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.ribbon.proxy.annotation.Http;
import org.apiguardian.api.API;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/delivery/grabexpress")
public class DeliveryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private OrderServiceProxy orderServiceProxy;

    @RequestMapping("/test")
    public String test() {
        return "Successful";
        //return deliveryService.getProperties();
    }

    @RequestMapping("/quotes")
    public ResponseDTO<QuotesResponseDTO> getQuotes(@RequestBody QuotesPlatformDTO quotesPlatformDTO) {
        try {
            if (quotesPlatformDTO.getLatitude() == null || quotesPlatformDTO.getLongitude() == null) {
                LOGGER.error("Buyer's Coordinates is null");
                ResponseDTO<QuotesResponseDTO> response = new ResponseDTO<>();
                response.setMessage("Buyer's Coordinates is null");
                response.setStatus(400);
                return response;
            }
            String token = deliveryService.getTokenFromGrabExpress(quotesPlatformDTO.getOutletId());
            QuotesRequestDTO quotesRequest = new QuotesRequestDTO();
            quotesRequest.setServiceType("INSTANT");
            if(quotesPlatformDTO.getCustomerId() != null) {
                ResponseEntity<ResponseDTO<GEQuotesDTO>> geQuotesDTO =
                        orderServiceProxy.getShoppingCartItems(quotesPlatformDTO.getCustomerId(), quotesPlatformDTO.getDevice(), quotesPlatformDTO.getOutletId());
                // Address's seller
                OriginDTO originDTO = new OriginDTO();
                originDTO.setAddress(geQuotesDTO.getBody().getBody().getOrigin().getAddress());
                originDTO.setKeywords("");
                CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
                coordinatesDTO.setLatitude(geQuotesDTO.getBody().getBody().getOrigin().getLatitude().doubleValue());
                coordinatesDTO.setLongitude(geQuotesDTO.getBody().getBody().getOrigin().getLongitude().doubleValue());
                originDTO.setCoordinates(coordinatesDTO);
                quotesRequest.setOrigin(originDTO);
            } else {
                // Address's seller
                OriginDTO originDTO = new OriginDTO();
                originDTO.setAddress(quotesPlatformDTO.getAddress());
                originDTO.setKeywords("");
                CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
                coordinatesDTO.setLatitude(quotesPlatformDTO.getSrcLatitude());
                coordinatesDTO.setLongitude(quotesPlatformDTO.getSrcLongitude());
                originDTO.setCoordinates(coordinatesDTO);
                quotesRequest.setOrigin(originDTO);
            }
            // set packages
            quotesRequest.setPackages(quotesPlatformDTO.getPackages());
            // Address's buyer
            DestinationDTO destinationDTO = new DestinationDTO();
            destinationDTO.setAddress(quotesPlatformDTO.getAddress());
            CoordinatesDTO desCoor = new CoordinatesDTO();
            desCoor.setLongitude(quotesPlatformDTO.getLongitude());
            desCoor.setLatitude(quotesPlatformDTO.getLatitude());
            destinationDTO.setCoordinates(desCoor);
            quotesRequest.setDestination(destinationDTO);
            //
            //TODO: MerchantOrderId didnt define in API docs, and at this moment we cannot create MerchantOrderId
            quotesRequest.setMerchantOrderID("get_quotes");
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(quotesRequest);
            ResponseDTO<QuotesResponseDTO> rs = deliveryService.getQuotes(token, requestBody);
            if (rs.getStatus() == 200)
                return rs;
            JSONObject obj = new JSONObject(rs.getMessage());
            String stt = obj.getString("arg");
            throw new Exception(stt);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            ResponseDTO<QuotesResponseDTO> response = new ResponseDTO<>();
            response.setMessage(e.getMessage());
            response.setStatus(400);
            return response;
        }
    }

    @RequestMapping("/noti")
    public void webhook(@RequestBody WebhookRequestDTO webhookRequestDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(webhookRequestDTO);
        System.out.println(requestBody);
    }

    @RequestMapping("/create")
    public ResponseEntity<ResponseDTO<DeliveryResponseDTO>> createDelivery(@RequestBody DeliveryRequestDTO deliveryRequestDTO) {
        try {
            String token = deliveryService.getTokenFromGrabExpress(deliveryRequestDTO.getOutletId());
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(deliveryRequestDTO);
            ResponseDTO<DeliveryResponseDTO> rp = deliveryService.createDelivery(token, requestBody);
            if (rp.getStatus() == 200)
                return new ResponseEntity<ResponseDTO<DeliveryResponseDTO>>(rp, null, HttpStatus.OK);
            throw new Exception(rp.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            ResponseDTO<DeliveryResponseDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus(400);
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<ResponseDTO<DeliveryResponseDTO>>(responseDTO, null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/save")
    public ResponseEntity<ResponseDTO<GrabExpressInfoDTO>> save(@RequestBody GrabExpressInfoDTO grabExpressInfoDTO) {
        deliveryService.save(grabExpressInfoDTO);
        return new ResponseEntity<>(null, null, HttpStatus.OK);
    }

    /**
     *
     * @param deliveryId
     * @param outletId
     * @return
     */
    @RequestMapping("/cancel")
    public ResponseEntity<ResponseDTO<String>> cancelDelivery(@RequestParam(value = "deliveryId", required = false) String deliveryId,
                                         @RequestParam(value = "outletId", required = false) Long outletId) {
        try {
            String token = deliveryService.getTokenFromGrabExpress(outletId);
            ResponseDTO<String> rp = deliveryService.cancel(deliveryId, token);
            if (rp.getStatus() == 200)
                return new ResponseEntity<ResponseDTO<String>>(rp, null, HttpStatus.OK);
            throw new Exception(rp.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus(400);
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<ResponseDTO<String>>(responseDTO, null, HttpStatus.BAD_REQUEST);
        }
    }
}
