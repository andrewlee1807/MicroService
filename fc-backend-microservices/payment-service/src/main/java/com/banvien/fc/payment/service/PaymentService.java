package com.banvien.fc.payment.service;

import com.banvien.fc.common.enums.ErrorCodeMap;
import com.banvien.fc.common.rest.errors.BadRequestException;
import com.banvien.fc.common.util.RestUtil;
import com.banvien.fc.payment.dto.grabpay.*;
import com.banvien.fc.payment.entity.CountryEntity;
import com.banvien.fc.payment.entity.OutletEntity;
import com.banvien.fc.payment.entity.PaymentMethodEntity;
import com.banvien.fc.payment.repository.CountryRepository;
import com.banvien.fc.payment.repository.OutletRepository;
import com.banvien.fc.payment.repository.PaymentMethodRepository;
import com.banvien.fc.payment.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.internal.cglib.proxy.$UndeclaredThrowableException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.banvien.fc.payment.constant.PaymentConstant.*;

@Service
public class PaymentService {

    public static final String HMAC_SHA_256 = "HmacSHA256";
    public static final String TIMESTAMP_TEMPLATE = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String ALPHA_NUMERIC_STRING = "ABCDEF0123456789abcdef";

    @Autowired
    private static HttpUtils httpUtils;

    @Autowired
    private OrderServiceProxy proxy;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OutletRepository outletRepository;

    @Value("${grab.url}")
    private String grabUrl;

    @Value("${grab.relative.url}")
    private String grabRelativeUrl;

//    @Value("${grab.init.url}")
//    private String grabInitUrl;

    @Value("${grab.redirect.url}")
    private String grabRedirectUrl;

    @Value("${grab.token.url}")
    private String grabUrlToken;

    @Value("${grab.redirect.status.url}")
    private String grabRidirectStatus;

    @Value("${grab.complete.url}")
    private String grabUrlComplete;

    @Value("${grab.refund.url}")
    private String grabRefundUrl;

    public String getGrabUrl() {
        return grabUrl;
    }

    public String getGrabRelativeUrl() {
        return grabRelativeUrl;
    }

    public String getGrabInitUrl() {
        return grabUrl + grabRelativeUrl;
    }

    public String getGrabRedirectUrl() {
        return grabRedirectUrl;
    }

    public String getGrabUrlToken() {
        return grabUrlToken;
    }

    public String getGrabRidirectStatus() {
        return grabRidirectStatus;
    }

    public String getGrabUrlComplete() {
        return grabUrlComplete;
    }

    public String getGrabRefundUrl() {
        return grabRefundUrl;
    }

    /**
     * @param content
     * @return
     */
    public String hashSHA256(String content) {
        return DigestUtils.sha256Hex(content);
    }

    /**
     * @param content
     * @param secret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws DecoderException
     */
    public String getHMAC_SHA256(String content, String secret) throws NoSuchAlgorithmException, InvalidKeyException, DecoderException {
        Mac sha256_HMAC = Mac.getInstance(HMAC_SHA_256);
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), HMAC_SHA_256);
        sha256_HMAC.init(secret_key);
        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(content.getBytes()));
        return hash;
    }

    /**
     * @param hashSHA256
     * @return
     * @throws DecoderException
     */
    public String encodeBase64WithHEX(String hashSHA256) throws DecoderException {
        byte[] decodedHex = Hex.decodeHex(hashSHA256);
        return new String(Base64.encodeBase64(decodedHex));
    }

    /**
     * @param content
     * @return
     * @throws DecoderException
     */
    public String encodeBase64AndHashSHA256(String content) throws DecoderException {
        return encodeBase64WithHEX(hashSHA256(content));
    }

    /**
     * @return
     */
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_TEMPLATE);
        Date date = new Date();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    /**
     * @param sDate
     * @return
     * @throws ParseException
     */
    private Date parseStringToDate(String sDate) throws ParseException {
        return new SimpleDateFormat(TIMESTAMP_TEMPLATE).parse(sDate);
    }

    /**
     * @param partnerID
     * @param partnerSecret
     * @param httpMethod
     * @param requestpath
     * @param contentType
     * @param requestBody
     * @param timeStamp
     * @return
     * @throws DecoderException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public String generateHMACSignature(String partnerID, String partnerSecret, String httpMethod, String requestpath, String contentType, String requestBody, String timeStamp) throws DecoderException, InvalidKeyException, NoSuchAlgorithmException {
        if (httpMethod.equalsIgnoreCase("GET") || httpMethod.isEmpty()) {
            requestBody = "";
        }
        String hashPayload = encodeBase64AndHashSHA256(requestBody);
        String requestdata = String.join("", String.join("\n", httpMethod, contentType, timeStamp, requestpath, hashPayload), "\n");
        String hmacDigest = getHMAC_SHA256(requestdata, partnerSecret);
        String authHeader = partnerID + ':' + hmacDigest;
        return authHeader;
    }

    /**
     * @param value
     * @return
     * @throws DecoderException
     */
    public String generateBase64URLEncode(String value) throws DecoderException {
        return value.replace("=", "").replace("+", "-").replace("/", "_");
    }

    /**
     * @param value
     * @return
     * @throws DecoderException
     */
    public String generateCodeChallenge(String value) throws DecoderException {
        return generateBase64URLEncode(encodeBase64AndHashSHA256(value));
    }

    /**
     * @param value
     * @return
     * @throws DecoderException
     */
    public String generateCodeVerifier(String value) throws DecoderException {
        return generateBase64URLEncode(encodeBase64WithHEX(value));
    }

    /**
     * Generate a Hex random String with length
     *
     * @param lenght
     * @return
     */
    public String generateRandomString(int lenght) {
        // chose a Character random from this String
        String AlphaNumericString = ALPHA_NUMERIC_STRING;
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(lenght);
        for (int i = 0; i < lenght; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    /**
     * @param clientSecret
     * @param accessToken
     * @param timestamp
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws DecoderException
     * @throws ParseException
     */
    public String generatePOPSignature(String clientSecret, String accessToken, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException, DecoderException, ParseException {
        Date date = parseStringToDate(timestamp);
        int timestampUnix = Math.round(date.getTime() / 1000);
        String message = timestampUnix + accessToken;
        String signature = getHMAC_SHA256(message, clientSecret);
        String payloadBytes = "{\"time_since_epoch\":" + timestampUnix + ",\"sig\":\"" + generateBase64URLEncode(signature) + "\"}";
        return generateBase64URLEncode(new String(java.util.Base64.getEncoder().encode(payloadBytes.getBytes())));
    }

    /**
     * @param params
     * @return
     * @throws StringIndexOutOfBoundsException
     */
    public String generateRequestInitBody(String... params) throws StringIndexOutOfBoundsException {
        if (params.length == 6) {
            return requestInitBody()
                    .replace("{{partnerGroupTxID}}", params[0])
                    .replace("{{partner_tx_id}}", params[1])
                    .replace("{{currency}}", params[2])
                    .replace("{{amount}}", params[3])
                    .replace("{{description}}", params[4])
                    .replace("{{merchantID}}", params[5]);
        }
        throw new StringIndexOutOfBoundsException("Request Init body is missing arguments: " + Arrays.toString(params));
    }

    /**
     * @param params
     * @return
     * @throws StringIndexOutOfBoundsException
     */
    public String generateAuthenURL(String... params) throws StringIndexOutOfBoundsException {
        if (params.length == 7) {
            return authenURL()
                    .replace("{{currency}}", params[0])
                    .replace("{{clientID}}", params[1])
                    .replace("{{codeChallenge}}", params[2])
                    .replace("{{nonce}}", params[3])
                    .replace("{{redirectUrl}}", params[4])
                    .replace("{{request}}", params[5])
                    .replace("{{state}}", params[6]);
        }
        throw new StringIndexOutOfBoundsException("Auth URL is missing arguments: " + Arrays.toString(params));
    }

    /**
     * @param params
     * @return
     * @throws StringIndexOutOfBoundsException
     */
    public String generateRequestTokenBody(String... params) throws StringIndexOutOfBoundsException {
        if (params.length == 5) {
            return bodyToken()
                    .replace("{{client_id}}", params[0])
                    .replace("{{client_secret}}", params[1])
                    .replace("{{code_verifier}}", params[2])
                    .replace("{{redirect_uri}}", params[3])
                    .replace("{{code}}", params[4]);
        }
        throw new StringIndexOutOfBoundsException("Request toke body is missing arguments: " + Arrays.toString(params));
    }

    private String requestInitBody() {
        return "{\n" +
                "  \"partnerGroupTxID\":\"{{partnerGroupTxID}}\",\n" +
                "  \"partnerTxID\":\"{{partner_tx_id}}\",\n" +
                "  \"currency\":\"{{currency}}\",\n" +
                "  \"amount\":{{amount}},\n" +
                "  \"description\":\"{{description}}\",\n" +
                "  \"merchantID\":\"{{merchantID}}\"\n" +
                "}";
    }

    private String authenURL() {
        return "https://api.stg-myteksi.com/grabid/v1/oauth2/authorize?" +
                "acr_values=consent_ctx%3AcountryCode%3DMY,currency%3D{{currency}}&client_id={{clientID}}" +
                "&code_challenge={{codeChallenge}}&code_challenge_method=S256&nonce={{nonce}}" +
                "&redirect_uri={{redirectUrl}}&request={{request}}&response_type=code" +
                "&scope=payment.one_time_charge&state={{state}}";
    }

    private String bodyToken() {
        return "{\n" +
                "  \"grant_type\":\"authorization_code\",\n" +
                "  \"client_id\":\"{{client_id}}\",\n" +
                "  \"client_secret\":\"{{client_secret}}\",\n" +
                "  \"code_verifier\":\"{{code_verifier}}\",\n" +
                "  \"redirect_uri\":\"{{redirect_uri}}\",\n" +
                "  \"code\":\"{{code}}\"\n" +
                "}";
    }

    private String formatString(String content, String... params) {
        return String.format(content, params);
    }

    /**
     * @param requestBodyDTO
     * @param servletRequest
     * @param mapStateAndCodeVerifier
     * @return
     * @throws Exception
     */
    public String generateAuthURL(RequestBodyDTO requestBodyDTO, HttpServletRequest servletRequest, Map<String,
            OrderInformationMobileDTO> mapStateAndCodeVerifier) throws Exception {
        PaymentMethodEntity rs = paymentMethodRepository.findPaymentMethodByCode("GRABPAY", requestBodyDTO.getSubmitParams().getOutletId());

        if (rs == null || StringUtils.isBlank(rs.getMerchantId()) || StringUtils.isBlank(rs.getPartnerId()) ||
                StringUtils.isBlank(rs.getPartnerSecret()) || StringUtils.isBlank(rs.getClientId()) || StringUtils.isBlank(rs.getClientSecret())) {
            System.out.println("Internal Error: Init missing Certificates");
            throw new BadRequestException(ErrorCodeMap.FAILURE_MISSING_GRAB_CERTIFICATES.name());
        }

        if (StringUtils.isBlank(requestBodyDTO.getPartnerGroupTxID()) || StringUtils.isBlank(requestBodyDTO.getCurrency())
                || StringUtils.isBlank(requestBodyDTO.getAmount())) {
            System.out.println("Internal Error: Init missing request arguments");
            throw new BadRequestException(ErrorCodeMap.FAILURE_MISSING_GRAB_REQUEST_BODY.name());
        }

        if (StringUtils.isBlank(requestBodyDTO.getDescription())) {
            requestBodyDTO.setDescription("");
        }
        Long userId = RestUtil.getUserIdFromToken(servletRequest);
        String codeVerifier = generateCodeVerifier(generateRandomString(64));
        System.out.println("codeVerifier:---" + codeVerifier);
        String state = Base64.encodeBase64String(UUID.randomUUID().toString().getBytes());
        System.out.println("state:---" + state);
        // prepare submit order
        OrderInformationMobileDTO orderInformationMobileDTO = new OrderInformationMobileDTO();
        orderInformationMobileDTO.setReceiverName(requestBodyDTO.getSubmitParams().getReceiverName());
        orderInformationMobileDTO.setOutletId(requestBodyDTO.getSubmitParams().getOutletId());
        orderInformationMobileDTO.setNote(requestBodyDTO.getSubmitParams().getNote());
        orderInformationMobileDTO.setReceiverPhone(requestBodyDTO.getSubmitParams().getReceiverPhone());
        orderInformationMobileDTO.setDeliveryMethod(requestBodyDTO.getSubmitParams().getDeliveryMethod());
        orderInformationMobileDTO.setPayment(requestBodyDTO.getSubmitParams().getPayment());
        orderInformationMobileDTO.setUsedPoint(requestBodyDTO.getSubmitParams().getUsedPoint());
        orderInformationMobileDTO.setAmountAfterUsePoint(requestBodyDTO.getSubmitParams().getAmountAfterUsePoint());
        orderInformationMobileDTO.setListProductAndQuantity(null);
//        orderInformationMobileDTO.setTransactionCode(partner);
        orderInformationMobileDTO.setUserId(userId);
        orderInformationMobileDTO.setCodeVerifier(codeVerifier);
        orderInformationMobileDTO.setGrabGroupTxId(requestBodyDTO.getPartnerGroupTxID());
        orderInformationMobileDTO.setPartnerId(rs.getPartnerId());
        orderInformationMobileDTO.setPartnerSecret(rs.getPartnerSecret());
        orderInformationMobileDTO.setClientId(rs.getClientId());
        orderInformationMobileDTO.setClientSecret(rs.getClientSecret());
        orderInformationMobileDTO.setState(state);
        orderInformationMobileDTO.setCustomerId(requestBodyDTO.getCustomerId());
        orderInformationMobileDTO.setSource(requestBodyDTO.getSubmitParams().getSource());
        orderInformationMobileDTO.setReceiverAddress(requestBodyDTO.getSubmitParams().getReceiverAddress());
        orderInformationMobileDTO.setReceiverLat(requestBodyDTO.getSubmitParams().getReceiverLat());
        orderInformationMobileDTO.setReceiverLng(requestBodyDTO.getSubmitParams().getReceiverLng());

        Map<String, Object> map = proxy.checkoutShoppingCart(orderInformationMobileDTO);
        if(StringUtils.isBlank(String.valueOf(map.get("OrderOutletCode")))) {
            throw new Exception("GrabPay's error: Cannot Create an OrderTemporary");
        }

        orderInformationMobileDTO.setTransactionCode(String.valueOf(map.get("OrderOutletCode")));
        mapStateAndCodeVerifier.put(state, orderInformationMobileDTO);

        Double pureTotalPrice = Double.parseDouble(map.get("Amount").toString()) * 100;
        String totalPrice = String.format("%.0f", pureTotalPrice);

        // add transaction id to shopping cart
        ShoppingCartRequestBodyDTO dto = new ShoppingCartRequestBodyDTO();
        //dto.setTransactionID(partner);
        dto.setItems(requestBodyDTO.getSubmitParams().getItems());
        proxy.updateShoppingCart(dto);

        String timestamp = getCurrentTimeStamp();

        String initReqBody = generateRequestInitBody(requestBodyDTO.getPartnerGroupTxID(), String.valueOf(map.get("OrderOutletCode")),
                requestBodyDTO.getCurrency(), totalPrice, requestBodyDTO.getDescription(), rs.getMerchantId());
        String nonce = Base64.encodeBase64String(UUID.randomUUID().toString().getBytes());


        String codeChallenge = generateCodeChallenge(codeVerifier);
        System.out.println("codeChallenge:---" + codeChallenge);
        // generate HMAC
        String hmacSignature = generateHMACSignature(rs.getPartnerId(), rs.getPartnerSecret(), GRAB_HTTP_POST_METHOD, getGrabRelativeUrl(), GRAB_CONTENT_TYPE,
                initReqBody, timestamp);

        // create a request
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Authorization", hmacSignature);
        mapHeader.put("Date", timestamp);
        mapHeader.put("Content-Type", GRAB_CONTENT_TYPE);

        System.out.println("requestBody of Init:--" + initReqBody);
        String response = HttpUtils.callPostRequest(getGrabInitUrl(), mapHeader, initReqBody);
        ObjectMapper oMapper = new ObjectMapper();
        GrabResponseDTO grabResponseDTO = oMapper.readValue(response, GrabResponseDTO.class);

        return generateAuthenURL(requestBodyDTO.getCurrency(), rs.getClientId(), codeChallenge,
                nonce, getGrabRedirectUrl(), grabResponseDTO.getRequest(), state);
    }

    public String getToken(String code, String state, String codeVerifier, OrderInformationMobileDTO dto) {
        System.out.println("state:---" + state);
        System.out.println("codeVerifier:---" + codeVerifier);

        // create a request header
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", GRAB_CONTENT_TYPE);
        // create a request body
        String requestBody = generateRequestTokenBody(dto.getClientId(), dto.getClientSecret(), codeVerifier,
                getGrabRedirectUrl(), code);

        System.out.println("requestBody of get token:--" + requestBody);
        return HttpUtils.callPostRequest(getGrabUrlToken(), mapHeader, requestBody);
    }

    private String bodyComplete() {
        return "{\n" +
                "  \"partnerTxID\":\"{{partner_tx_id}}\"\n" +
                "}";
    }

    /**
     * @param token
     * @param dto
     * @return
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws DecoderException
     */
    public String chargeComplete(String token, OrderInformationMobileDTO dto) throws ParseException, NoSuchAlgorithmException, InvalidKeyException, DecoderException {
        System.out.println("++ Start chargeComplete service");
        String timestamp = getCurrentTimeStamp();
        System.out.println("timestamp:" + timestamp);
        String popSignature = generatePOPSignature(dto.getClientSecret(), token, timestamp);
        System.out.println("popSignature:" + popSignature);
        // create a request header
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Authorization", "Bearer " + token);
        mapHeader.put("X-GID-AUX-POP", popSignature);
        mapHeader.put("Content-Type", GRAB_CONTENT_TYPE);
        mapHeader.put("Date", timestamp);
        // create a request body
        System.out.println("partner:---" + dto.getTransactionCode());
        String requestBody = bodyComplete().replace("{{partner_tx_id}}", dto.getTransactionCode());
        System.out.println("requestBody of charge complete:--" + requestBody);
        return HttpUtils.callPostRequest(getGrabUrlComplete(), mapHeader, requestBody);
    }

    /**
     *
     * @param requestBodyDTO
     * @return
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws DecoderException
     */
    public String refund(RequestBodyDTO requestBodyDTO, HttpServletRequest servletRequest) throws ParseException, NoSuchAlgorithmException, InvalidKeyException, DecoderException {

        //Map<String, Object> stringObjectMap = proxy.getOrderOutletInfo(requestBodyDTO.getPartnerGroupTxID());
        OutletEntity outletEntity = outletRepository.findById(requestBodyDTO.getOutletId()).get();
        if(outletEntity == null) {
            throw new BadRequestException("GrabPay's Error: Cannot get Currency");
        }
        CountryEntity country = countryRepository.findById(outletEntity.getCountryId()).get();

        String Url = getGrabRefundUrl();
        PaymentMethodEntity paymentMethod = paymentMethodRepository.findPaymentMethodByCode("GRABPAY", requestBodyDTO.getOutletId());
        String timestamp = getCurrentTimeStamp();
        System.out.println("timestamp:" + timestamp);
        String popSignature = generatePOPSignature(paymentMethod.getClientSecret(), requestBodyDTO.getToken(), timestamp);
        System.out.println("popSignature:" + popSignature);
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Origin Amount: " + requestBodyDTO.getAmount());
        Double total = Double.parseDouble(df.format(Double.parseDouble(requestBodyDTO.getAmount())));

        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Authorization", "Bearer " + requestBodyDTO.getToken());
        mapHeader.put("X-GID-AUX-POP", popSignature);
        mapHeader.put("Content-Type", GRAB_CONTENT_TYPE);
        mapHeader.put("Date", timestamp);

        String body = "{\n" +
                "  \"partnerGroupTxID\":\"{{partnerGroupTxID}}\",\n" +
                "  \"partnerTxID\":\"{{partnerTxID}}\",\n" +
                "  \"currency\":\"{{currency}}\",\n" +
                "  \"amount\":{{amount}},\n" +
                "  \"description\":\"test refund\",\n" +
                "  \"originTxID\":\"{{originTxID}}\",\n" +
                "  \"merchantID\":\"{{merchantID}}\"\n" +
                "}";
        String requestBody = body
                .replace("{{partnerTxID}}", requestBodyDTO.getPartnerTxID())
                .replace("{{partnerGroupTxID}}", requestBodyDTO.getPartnerGroupTxID())
                .replace("{{amount}}", String.valueOf(total.longValue()))
                .replace("{{originTxID}}", requestBodyDTO.getOriginTxID())
                .replace("{{currency}}", country.getCurrency())
                .replace("{{merchantID}}", paymentMethod.getMerchantId());
        System.out.println("requestBody of refund:--" + requestBody);
        String rs = HttpUtils.callPostRequest(Url,mapHeader, requestBody);
        System.out.println("Refund:--" + rs);
        return rs;
    }

    /**
     *
     * @param outletId
     * @return
     * @throws Exception
     */
    public CertificatesDTO getCertificates(Long outletId) throws Exception{
        PaymentMethodEntity rs = paymentMethodRepository.findPaymentMethodByCode("GRABPAY", outletId);
        if (rs == null || StringUtils.isBlank(rs.getMerchantId()) || StringUtils.isBlank(rs.getPartnerId()) ||
                StringUtils.isBlank(rs.getPartnerSecret()) || StringUtils.isBlank(rs.getClientId()) || StringUtils.isBlank(rs.getClientSecret())) {
            throw new Exception("Error: Init missing Certificates");
        }
        String state = Base64.encodeBase64String(UUID.randomUUID().toString().getBytes());
        CertificatesDTO dto = new CertificatesDTO();
        //dto.setGrabgrouptxid(rs.get);
        dto.setState(state);
        return dto;
    }

    /**
     * @param orderTemporaryId
     * @return
     */
    public AuthenDTO generateAuthen(Long orderTemporaryId, Map<String, OrderInformationMobileDTO> mapStateAndCodeVerifier, String currency) throws Exception {
        try {
            Map<String, Object> rs = proxy.getAmountOrderTemporary(orderTemporaryId);
            if(rs == null) {
                throw new Exception(String.format("Internal Error: Cannot get OrderTemporary record by ID {0}", orderTemporaryId));
            }
            PaymentMethodEntity rsPayment = paymentMethodRepository.findPaymentMethodByCode("GRABPAY", Long.parseLong(String.valueOf(rs.get("Outlet"))));

            if (rsPayment == null || StringUtils.isBlank(rsPayment.getMerchantId()) || StringUtils.isBlank(rsPayment.getPartnerId()) ||
                    StringUtils.isBlank(rsPayment.getPartnerSecret()) || StringUtils.isBlank(rsPayment.getClientId()) || StringUtils.isBlank(rsPayment.getClientSecret())) {
                System.out.println("Internal Error: Init missing Certificates");
                throw new BadRequestException(ErrorCodeMap.FAILURE_MISSING_GRAB_CERTIFICATES.name());
            }

            String codeVerifier = generateCodeVerifier(generateRandomString(64));
            System.out.println("codeVerifier:---" + codeVerifier);
            String state = Base64.encodeBase64String(UUID.randomUUID().toString().getBytes());
            System.out.println("state:---" + state);
// prepare submit order
            OrderInformationMobileDTO orderInformationMobileDTO = new OrderInformationMobileDTO();
            orderInformationMobileDTO.setListProductAndQuantity(null);
//        orderInformationMobileDTO.setTransactionCode(partner);
            orderInformationMobileDTO.setCodeVerifier(codeVerifier);
            orderInformationMobileDTO.setState(state);
            orderInformationMobileDTO.setPartnerId(rsPayment.getPartnerId());
            orderInformationMobileDTO.setPartnerSecret(rsPayment.getPartnerSecret());
            orderInformationMobileDTO.setClientId(rsPayment.getClientId());
            orderInformationMobileDTO.setClientSecret(rsPayment.getClientSecret());
            orderInformationMobileDTO.setSource(String.valueOf(rs.get("Source")));
            orderInformationMobileDTO.setTransactionCode(String.valueOf(rs.get("OrderOutletCode")));
            mapStateAndCodeVerifier.put(state, orderInformationMobileDTO);

            Double pureTotalPrice = Double.parseDouble(rs.get("Amount").toString()) * 100;
            String totalPrice = String.format("%.0f", pureTotalPrice);

            // add transaction id to shopping cart
            ShoppingCartRequestBodyDTO dto = new ShoppingCartRequestBodyDTO();
            //dto.setTransactionID(partner);
//            dto.setItems(requestBodyDTO.getSubmitParams().getItems());
//            proxy.updateShoppingCart(dto);

            String timestamp = getCurrentTimeStamp();

            String initReqBody = generateRequestInitBody(String.valueOf(rs.get("OrderOutletCode")), String.valueOf(rs.get("OrderOutletCode")),
                    currency, totalPrice, "EASY_ORDER", rsPayment.getMerchantId());
            String nonce = Base64.encodeBase64String(UUID.randomUUID().toString().getBytes());


            String codeChallenge = generateCodeChallenge(codeVerifier);
            System.out.println("codeChallenge:---" + codeChallenge);
            // generate HMAC
            String hmacSignature = generateHMACSignature(rsPayment.getPartnerId(), rsPayment.getPartnerSecret(), GRAB_HTTP_POST_METHOD, getGrabRelativeUrl(), GRAB_CONTENT_TYPE,
                    initReqBody, timestamp);

            // create a request
            Map<String, String> mapHeader = new HashMap<>();
            mapHeader.put("Authorization", hmacSignature);
            mapHeader.put("Date", timestamp);
            mapHeader.put("Content-Type", GRAB_CONTENT_TYPE);

            System.out.println("requestBody of Init:--" + initReqBody);
            String response = HttpUtils.callPostRequest(getGrabInitUrl(), mapHeader, initReqBody);
            ObjectMapper oMapper = new ObjectMapper();
            GrabResponseDTO grabResponseDTO = oMapper.readValue(response, GrabResponseDTO.class);

            AuthenDTO authenDTO = new AuthenDTO();
            authenDTO.setAuthenUrl(generateAuthenURL(currency, rsPayment.getClientId(), codeChallenge,
                    nonce, getGrabRedirectUrl(), grabResponseDTO.getRequest(), state));
            return authenDTO;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String completedPage(String status) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Sma@rt Retail</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@10\"></script>\n" +
                "    <script>\n" +
                "      Swal.fire({\n" +
                "        title: \"Payment "+ ((("success").equalsIgnoreCase(status)) ? "success" : "failed") +"!\",\n" +
                "        text: \"Please close this tab to continue this order's process!\",\n" +
                "        icon: \"" +((("success").equalsIgnoreCase(status)) ? "success" : "error")+ "\",\n" +
                "        showConfirmButton: false,\n" +
                "      });\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";
    }
}
