package com.banvien.fc.delivery.service;

import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.delivery.dto.grabexpress.GrabExpressInfoDTO;
import com.banvien.fc.delivery.dto.grabexpress.ResponseDTO;
import com.banvien.fc.delivery.dto.grabexpress.responsedto.DeliveryResponseDTO;
import com.banvien.fc.delivery.dto.grabexpress.responsedto.QuotesResponseDTO;
import com.banvien.fc.delivery.entity.DeliveryServiceEntity;
import com.banvien.fc.delivery.entity.GrabExpressInfoEntity;
import com.banvien.fc.delivery.repository.DeliveryMethodRepository;
import com.banvien.fc.delivery.repository.GrabExpressInfoRepository;
import com.banvien.fc.delivery.utils.GrabExpressInfoBeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class DeliveryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryService.class);
    @Autowired
    GrabExpressInfoRepository grabExpressInfoRepository;
    @Autowired
    DeliveryMethodRepository deliveryMethodRepository;

    @Value("${grab.token.url}")
    private String grabTokenUrl;

    @Value("${grab.quotes.url}")
    private String grabQuotesUrl;

    @Value("${grab.delivery.url}")
    private String grabDeliveryUrl;

    @Value("${grab.cancel.url}")
    private String grabCancelUrl;

    public String getGrabTokenUrl() {
        return grabTokenUrl;
    }

    public String getGrabQuotesUrl() {
        return grabQuotesUrl;
    }

    public String getGrabDeliveryUrl() {
        return grabDeliveryUrl;
    }

    public String getGrabCancelUrl() {
        return grabCancelUrl;
    }

    /**
     * "client_id", "846397343feb40a0835f230d8e681eaa"
     * "client_secret", "GXrtL43zu9G-CqGR"
     * @param outletId
     * @return
     * @throws UnirestException
     * @throws JSONException
     */
    public String getTokenFromGrabExpress(Long outletId) throws Exception {
        LOGGER.info("Start get token service");
        DeliveryServiceEntity deliveryServiceEntity = deliveryMethodRepository.findDeliveryMethodByCode("SHIP_TO_HOME_EXPRESS",outletId);
        if(deliveryServiceEntity == null || StringUtils.isBlank(deliveryServiceEntity.getClientId()) || StringUtils.isBlank(deliveryServiceEntity.getClientSecret())) {
            throw new BadRequestException("GrabExpress's Error: Missing Certificates");
        }
        HttpResponse<String> response = Unirest.post(getGrabTokenUrl())
                .header("Cache-Control", "no-cache")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", deliveryServiceEntity.getClientId())
                .field("client_secret", deliveryServiceEntity.getClientSecret())
                .field("grant_type", "client_credentials")
                .field("scope", "grab_express.partner_deliveries")
                .asString();

        JSONObject obj = new JSONObject(response.getBody());
        String token = obj.getString("access_token");
        LOGGER.debug("Token: " + token);
        LOGGER.info("End get token service");
        return token;
    }

    public ResponseDTO<QuotesResponseDTO> getQuotes(String token, String body) throws UnirestException, JsonProcessingException {
        LOGGER.info("Start getQuotes service");
        QuotesResponseDTO quotesResponseDTO = null;
        ResponseDTO<QuotesResponseDTO> rp = new ResponseDTO<>();
        HttpResponse<String> response = Unirest.post(getGrabQuotesUrl())
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .asString();
        LOGGER.debug("Body: " + response.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        rp.setStatus(response.getStatus());
        if (response.getStatus() == 200) {
            quotesResponseDTO = objectMapper.readValue(response.getBody(), QuotesResponseDTO.class);
            rp.setMessage("");
            rp.setBody(quotesResponseDTO);
        } else {
            rp.setMessage(response.getBody());
        }
        LOGGER.info("End getQuotes service");
        return rp;
    }

    /**
     * @param token
     * @param body
     * @return
     * @throws UnirestException
     * @throws JsonProcessingException
     */
    public ResponseDTO<DeliveryResponseDTO> createDelivery(String token, String body) throws UnirestException, JsonProcessingException {
        LOGGER.info("Start createDelivery service");
        System.out.println("Request: " + body);
        DeliveryResponseDTO deliveryResponseDTO = null;
        ResponseDTO<DeliveryResponseDTO> rp = new ResponseDTO<>();
        HttpResponse<String> response = Unirest.post(getGrabDeliveryUrl())
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(body)
                .asString();
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.debug("Body: " + response.getBody());
        rp.setStatus(response.getStatus());
        if (response.getStatus() == 200) {
            deliveryResponseDTO = objectMapper.readValue(response.getBody(), DeliveryResponseDTO.class);
            rp.setMessage("");
            rp.setBody(deliveryResponseDTO);
        } else {
            rp.setMessage(response.getBody());
        }
        System.out.println("Response: " + response.getStatusText());
        LOGGER.info("End createDelivery service");
        return rp;
    }

    /**
     *
     * @param grabExpressInfoDTO
     * @return
     */
    public GrabExpressInfoDTO save(GrabExpressInfoDTO grabExpressInfoDTO) {
        GrabExpressInfoEntity grabExpressInfoEntity = GrabExpressInfoBeanUtil.dto2Entity(grabExpressInfoDTO);
        return GrabExpressInfoBeanUtil.entity2Dto(grabExpressInfoRepository.save(grabExpressInfoEntity));
    }

    /**
     *
     * @param deliveryId
     * @return
     * @throws UnirestException
     */
    public ResponseDTO<String> cancel(String deliveryId, String token) throws UnirestException {
        HttpResponse<String> response = Unirest.delete(getGrabCancelUrl() + deliveryId)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .asString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("X-Grabkit-Grab-Requestid", response.getHeaders().get("X-Grabkit-Grab-Requestid"));
        ResponseDTO<String> rp = new ResponseDTO<>();
        rp.setStatus(response.getStatus());
        if (response.getStatus() == 200) {
            rp.setMessage("");
            rp.setBody(response.getBody());
        } else {
            rp.setMessage(response.getBody());
        }
        return rp;
    }
}
