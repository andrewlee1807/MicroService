package com.banvien.fc.payment.constant;

public final class PaymentConstant {
    private PaymentConstant() {
        // Dont create an instance.
    }

    public static final String GRAB_URL = "https://partner-api.stg-myteksi.com";
    public static final String GRAB_RELATIVE_URL = "/grabpay/partner/v2/charge/init";
    public static final String GRAB_INIT_URL = GRAB_URL + GRAB_RELATIVE_URL;
//    public static final String GRAB_PARTNER_ID = "8d46abcb-e293-47d2-90fa-b7070f0b1a22";
//    public static final String GRAB_PARTNER_SECRET = "KVzhZwi3zylQWVov";
    public static final String GRAB_HTTP_POST_METHOD = "POST";
    public static final String GRAB_CONTENT_TYPE = "application/json";
//    public static final String GRAB_CLIENT_ID = "a6f9c23927444dcfba0c93f2aa0be48c";
//    public static final String GRAB_CLIENT_SECRET = "OBLzgTznQ_rxcxfG";
    public static final String GRAB_REDIRECT_URL = "https://customer-api-smartapp.banvien.com.vn/partner/grabpay/callback?";
    public static final String GRAB_URL_TOKEN = "https://api.stg-myteksi.com/grabid/v1/oauth2/token";
    public static final String GRAB_RIDIRECT_STATUS = "https://customer-api-smartapp.banvien.com.vn/partner/grabpay/callback?status=";
    public static final String GRAB_URL_COMPLETE = "https://partner-api.stg-myteksi.com/grabpay/partner/v2/charge/complete";
    public static final String GRAB_SUCCESS_STATUS = "success";
    public static final String GRAB_FAILED_STATUS = "failed";
    public static final String GRAB_UNKNOWN_STATUS = "unknown";
    public static final String GRAB_ERROR = "consent_required";
}
