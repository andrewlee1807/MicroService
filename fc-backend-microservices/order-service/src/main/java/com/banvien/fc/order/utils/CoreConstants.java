package com.banvien.fc.order.utils;

/**
 * Created by Henry Le on 3/28/2017.
 */
public class CoreConstants {


    public static String dotCharacter = String.valueOf('.');
    public static String commaCharacter = String.valueOf(',');
    public static String doubleZero = String.valueOf("00");
    public static String minusOperator = String.valueOf("-");

    //General constant
    public static final String STATUS = "status";
    public static final String FAIL_REASON = "failReason";
    public static final String FAIL_ELEMENT = "failElement";
    public static final String FAIL_ERRORS = "failErrors";
    public static final String VALUE_TRUE = "true";

    //DB Constant
    public static final String UNION = "\nUNION\n";
    public static final String UNION_ALL = "UNION ALL";

    //employee role
    public static final String ROLE_SALESMAN = "SALESMAN";

    public static final String TIMESTAMP_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String CUSTOMER_GENERAL = "CUS";
    public static final String EMPLOYEE_PREFIX_CODE = "EMP";
    public static final String PRODUCT_GENERAL_CODE = "PID";
    public static final String PRODUCT_SKU_GENERAL = "SKU";
    public static final String ORDER_TYPE_NOTIFICATION = "ORDER";
    public static final String NOTIFICATION_TYPE_REWARD = "REWARD";
    public static final String NEWS_TYPE_NOTIFICATION = "NEWS";
    public static final String PROMOTION_TYPE_NOTIFICATION = "PROMOTION";
    public static final String GIFT_TYPE_NOTIFICATION = "GIFT";
    public static final String LOYALTY_REQUEST_TYPE_NOTIFICATION = "LOYALTY_REQUEST";
    public static final String CAMPAIGN_START_TYPE_NOTIFICATION = "CAMPAIGN_START";
    public static final String LOYALTY_HISTORY_TYPE_NOTIFICATION = "LOYALTY_HISTORY";
    public static final String FCV_PRODUCTSKU = "FCV_PRODUCTSKU";
    public static final String FCV_BRAND = "FCV_BRAND";
    public static final String DELIVERY_ASSIGN = "DELIVERY_ASSIGN";

    public static final String ORDER_DELIVERED = "DELIVERED";
    public static final String ORDER_CANCEL = "CANCEL";
    public static final String WAITING_FOR_CONFIRMATION = "WAITING_FOR_CONFIRMATION";
    public static final String WAITING_FOR_PICKING = "WAITING_FOR_PICKING";
    public static final String SAVE_AS_DRAFT = "SAVE_AS_DRAFT";
    public static final String WAITING_FOR_CONFIRMATION_LABEL = "order.status.waiting.confirm";
    public static final String PREPARING_LABEL = "order.status.preparing";
    public static final String WAITING_FOR_PICKING_LABEL = "order.status.waiting.pick";
    public static final String DELIVER_SUCCESS_LABEL = "order.status.delivery.success";
    public static final String SUCCESS_LABEL = "order.status.success";
    public static final String CANCELED_LABEL = "order.status.cancel";
    public static final String STATUS_SUCCESS = "SUCCESS";

    public static final String SAVE_AS_DRAFT_LABEL = "order.status.save.as.draft";
    public static final String SALE_CHANEL_DIRECT = "SALE_CHANEL_DIRECT";
    public static final String PAYMENT_METHOD_COD = "COD";
    public static final String PAYMENT_METHOD_COD_TITLE = "payment.cod.title";
    public static final String PAYMENT_METHOD_POINT = "POINT";
    public static final String PAYMENT_METHOD_POINT_TITLE = "payment.point.title";
    public static final String PAYMENT_METHOD_CREDIT = "CREDIT";
    public static final String PAYMENT_METHOD_CREDIT_TITLE = "payment.credit.title";
    public static final String PAYMENT_METHOD_CHEQUE = "CHEQUE";
    public static final String PAYMENT_METHOD_CHEQUE_TITLE = "payment.cheque.title";
    public static final String RESET_CREDIT = "RESET_CREDIT";
    public static final String PAYMENT_METHOD_GRABPAY = "GRABPAY";
    public static final String PAYMENT_METHOD_GRABPAY_TITLE = "payment.grabpay.title";
    public static final String PAYMENT_METHOD_GATEWAY = "GATEWAY";
    public static final String PAYMENT_METHOD_GATEWAY_TITLE = "payment.gateway.title";


    public static final int ACTIVE_STATUS = 1;
    public static final int INACTIVE_STATUS = 0;
    public static final int DELETED_STATUS = -1;
    public static final boolean ACTIVE_STATUS_B = true;
    public static final int NOLIMIT_BUYER_PROMOTION = -1;
    public static final String DISCOUNT_ORDER_PROMOTION_CASH = "CASH";
    public static final String DISCOUNT_PRODUCT_PROMOTION_CASH = "CASH";
    public static final String PRODUCT_PROMOTION = "BUY_ANY_X";
    public static final String PRODUCT_PROMOTION_GET_GIFT = "GIFT";
    public static final String PRODUCT_PROMOTION_GET_DISCOUNT = "DISCOUNT";
    public static final String PRODUCT_PROMOTION_GET_PRODUCT = "PRODUCT";
    public static final int DEFAULT_PRODUCT_PROMOTION_QUANTIY = 1;
    public static final int NUMBER_PRODUCT_PROMOTION_SEARCH = 5;
    public static final String PRODUCT_PROMOTION_FOR_VIEW = "PRODUCT";
    public static final String PRODUCT_PROMOTION_FOR_CART = "CART";
    public static final String PRODUCT_PROMOTION_FOR_ORDER = "ORDER";

    public static final String SORT_ASC = "2";
    public static final String SORT_DESC = "1";

    public static final String USER_GROUP_SHOPPER = "SHOPPER";
    public static final String USER_GROUP_OUTLET = "OUTLET";
    public static final String USER_GROUP_ADMIN = "ADMIN";
    public static final String USER_GROUP_EMPLOYEE = "EMPLOYEE";
    public static final String ROLE_USER_RETAILER = "USER_RETAILER";
    public static final String SET_PASSWORD_DEFAULT = "123456";

    // master event
    public static final String MASTER_EVENT_TITLE_PAID_BY_POINT = "Paid by point";
    public static final String MASTER_EVENT_TYPE_PAID_BY_POINT = "PAID_BY_POINT";
    public static final String OUTLET_EVENT_PAID_BY_POINT_NAME = "Paid by point";
    public static final String MASTER_EVENT_TYPE_EXPIRED_POINT = "EXPIRED_POINT";
    public static final String MASTER_EVENT_TITLE_EXPIRED_POINT = "Expired point";

    //Type Search
    public static final String OVERALL = "OVERALL";
    public static final String CURRENT_WEEK = "CURRENT_WEEK";
    public static final String LAST_WEEK = "LAST_WEEK";
    public static final String LAST_7_WEEK = "LAST_7_WEEK";
    public static final String CURRENT_MONTH = "CURRENT_MONTH";
    public static final String LAST_MONTH = "LAST_MONTH";
    public static final String SEARCH_DAY = "SEARCH_DAY";

    //View
    public static final String VIEW_COUNT = "VIEW_COUNT";
    public static final String VIEW_SALES = "VIEW_SALES";

    // outlet event
    public static final String OUTLET_EVENT_EXPIRED_POINT_NAME = "Expired point";
    public static final String OUTLET_EVENT_STATUS = "ACTIVE";

    public static final String DEFAULT_IMAGE = "/";

    public static final int MOBILE_RANDOM_PWD_LEN = 4;

    public static final int MIN_PHONE_NUMBER_LEN = 9;

    public static final String NOTICE_TEMPLATE_SMS_ACTIVATED_SHOPPER_SUCCESS = "SMS_ActivatedShopperAccountSuccessfully";

    public static final int CUMULATIVE_TRANSACTION_TYPE_POINT = 1;

    public static final int CUMULATIVE_POINT_AMOUNT_SIZE = 10000;

    public static final int DATA_CHANGE_HISTORY_TYPE_STATUS = 10000;
    public static final String DATE_FORMAT_JAVA = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DB = "DD/MM/YYYY";

    public static final int DATA_CHANGE_PROCESS_STATUS_PENDING = 0;
    public static final int DATA_CHANGE_PROCESS_STATUS_DONE = 1;

    public static final int SELLING_STATUS = 1;
    public static final int STOP_SELLING_STATUS = 0;
    public static final int ALL_STATUS = 3;

    public static final String NOTIFICATION_TYPE = "NOTIFY";
    public static final String NOTIFICATION_PARAM_1 = "param_1";
    public static final String NOTIFICATION_PARAM_2 = "param_2";
    public static final String NOTIFICATION_PARAM_3 = "param_3";

    public static final String UNIT_M = "m";
    public static final String UNIT_KM = "km";

    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String DELIVERY_PICK_AND_GO = "PICK_AND_GO";
    public static final String DELIVERY_PICK_N_GO = "PICK_N_GO";
    public static final String DELIVERY_PICK_N_GO_TITLE = "delivery.method.pick-and-go";
    public static final String DELIVERY_PAY_N_GET = "PAY_N_GET";
    public static final String DELIVERY_GROUP_CODE_PAY_N_GET_ = "PAY_N_GET";
    public static final String DELIVERY_PAY_N_GET_CODE = "SHIP_TO_HOME";
    public static final String DELIVERY_PAY_N_GET_TITLE = "delivery.method.pay-and-get";
    public static final String DELIVERY_PAY_N_GET_EXPRESS_CODE = "SHIP_TO_HOME_EXPRESS";
    public static final String DELIVERY_PAY_N_GET_EXPRESS_TITLE = "delivery.method.pay-and-get.express";

    public static final String THIRD_PARTY_GRAB_CODE = "THIRD_PARTY_GRAB";
    public static final String THIRD_PARTY_GRAB_TITLE = "delivery.method.grab.title";
    public static final String THIRD_PARTY_AHAMOVE_CODE = "THIRD_PARTY_AHAMOVE";
    public static final String THIRD_PARTY_AHAMOVE_TITLE = "delivery.method.ahamove.title";

    public static final String DELIVERY_COMPLETED = "COMPLETED";
    public static final String DELIVERY_CANCELLED = "CANCELLED";
    public static final String DELIVERY_PARTIAL_COMPLETED = "PARTIAL_COMPLETED";

    public static final Integer MAX_OUTLET_HOT_DEALS = 10;
    public static final Integer MAX_OUTLET_BEST_SELLING = 10;
    public static final String DEFAULT_PASSWORD = "123456";
    public static final Double PROMOTION_BUY_1K_PRICE = 1000d;
    public static final Integer MAX_RESULT = 10;
    public static final Integer MAX_PLAY = 3;
    public static final Integer DEFAULT_TOP_BRAND = 5;

    public static final String FRAMEBLUE = "frameBlue";
    public static final String FRAMEGREEN = "frameGreen";
    public static final String FRAMEYELLOW = "frameYellow";

    public static final String SYNC_PRODUCT_OUTLET = "SYNC_PRODUCT_OUTLET";
    public static final String SYNC_PRODUCT_OUTLET_SKU = "SYNC_PRODUCT_OUTLET_SKU";
    public static final String SYNC_BRAND = "SYNC_BRAND";
    public static final String SYNC_CAT_GROUP = "SYNC_CAT_GROUP";
    public static final String SYNC_LOYALTY_MEMBER = "SYNC_LOYALTY_MEMBER";
    public static final String SYNC_ORDER = "SYNC_ORDER";
    public static final String SYNC_COLLECTION = "SYNC_COLLECTION";
    public static final String SYNC_COLLECTION_DETAIL = "SYNC_COLLECTION_DETAIL";

    // Inventory
    public static final String PREFIX_STOCK_IN = "SI";
    public static final String PREFIX_STOCK_OUT = "SO";
    public static final String PREFIX_AUDIT = "AU";
    public static final String PREFIX_BACK_ITEM = "BI";
    public static final String RETURN_FROM_ORDER = "RETURN_FROM_ORDER ID : ";
    public static final String BACK_FROM_ORDER = "BACK_FROM_ORDER ID : ";
    public static final String CANCEL_FROM_ORDER = "CANCEL_FROM_ORDER ID : ";


    public static final String INVENTORY_NO_SP_STOCKED_IN_WAREHOUSE_ERROR_LABEL = "warehouse.no.order.product.stocked";
    public static final String INVENTORY_SP_NOT_IN_WAREHOUSE_ERROR_LABEL = "warehouse.exist.product.not.stocked";
    public static final String INVENTORY_SP_QUANTITY_LESS_THAN_IN_ORDER_ERROR_LABEL = "warehouse.product.quantity.less.than.in.order";
    public static final String INVENTORY_WAREHOUSE_NOT_ESTABLISHED_OR_DISABLED_ERROR_LABLE = "warehouse.not.established.or.disabled";
    public static final String ORDER_SAVE_LOYALTY_ERROR_LABEL = "order.save.loyalty.error";

    public static final String DIRECT_SUCCESS_SOURCE = "direct-success";
    public static final String NORMAL_FLOW_SOURCE = "normal-flow";

    // Current customer
    public static final String DEFAULT_CUSTOMER_NAME = "CURRENT_CUSTOMER";
    public static final String DEFAULT_CUSTOMER_ADDRESS = "DEFAULT_ADDRESS";
    public static final String DEFAULT_USER_PHONE = "0000000000";
    public static final String DEFAULT_USER_NAME = "CURRENT_CUSTOMER_USER_NAME";
    public static final String DEFAULT_USER_PASSWORD = "12345678";
    public static final String DEFAULT_USER_CODE = "00000000000000";
    public static final Integer DEFAULT_USER_STATUS = 1;

    public static final String PREFIX_PACKAGE_NO = "PK_";

    //Notification expire date point
    public static final String LOYALTY_EXPIRY_NOTIFICATION_DAYS = "LOYALTY_EXPIRY_NOTIFICATION_DAYS";
    public static final String LOYALTY_EXPIRY_NOTIFICATION_DAYS_TITLE = "Expiry Date Point";


    public static final Integer PAYMENT_TYPE_PAYMENT = 1;
    public static final Integer PAYMENT_TYPE_RETURN = 2;
    public static final Integer PAYMENT_TYPE_ALL = 3;

    public static final String OFFLINE_MODE = "OFFLINE_MODE";

    public static final String LOYALTY_STATUS_ACTIVE = "ACTIVE";
    public static final String LOYALTY_NAME_CANCEL_PAID_BY_POINT = "Cancel paid by point";
    public static final String MASTER_EVENT_TYPE_CANCEL_PAID_BY_POINT = "CANCEL_PAID_BY_POINT";
    public static final String MASTER_EVENT_TITLE_CANCEL_PAID_BY_POINT = "Cancel paid by point";
    public static final String MASTER_EVENT_TITLE_ORDER_PRODUCT = "ORDER_PRODUCT";

    public static final String SOURCE_SMART_RETAIL = "SOURCE_SMART_RETAIL";
    public static final String SOURCE_SMART_SELL = "SOURCE_SMART_SELL";
    public static final String SOURCE_SMART_REP = "SOURCE_SMART_REP";
    public static final String SOURCE_POS = "SOURCE_POS";
    public static final String SOURCE_EASY_ORDER = "SOURCE_EASY_ORDER";

    public static final String GEOGRAPHY = "GEOGRAPHY";
    public static final String OUTLET_TYPE = "OUTLET_TYPE";
    public static final String CUSTOMER_GROUP = "CUSTOMER_GROUP";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String PERFORMANCE = "PERFORMANCE";

    //language
    public static final String VIETNAMESE = "vi";
    public static final String ENGLISH = "en";
    public static final String MALAYSIA = "ma";
    public static final String MALAYSIA_MY = "my";
    public static final String INDONESIA = "id";
    public static final String INDONESIA_IN = "in";
    public static final String CHINA = "cn";
    public static final String CHINA_ZH = "zh";

    public static final Integer STATUS_NOT_VISITED = -1;
    public static final Integer STATUS_VISITTING = 0;
    public static final Integer STATUS_VISITED = 1;

    //error
    public static final String ALREADY_EXISTS = "ALREADY_EXISTS";
    public static final String NOT_EXISTS = "NOT_EXISTS";
    public static final String VERIFIED = "VERIFIED";
    public static final String SORT_BY_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String SORT_BY_DISTANCE = "DISTANCE";
    public static final String SORT_BY_DATE = "DATE";

    public static final String CAT_MILK = "1003";
    public static final String CAT_NUTRI = "1045";

    public static final String STATUS_CANCELED = "CANCELED";
    public static final String STATUS_PREPARING = "PREPARING";
    public static final String REQUEST = "REQUEST";
    public static final String REQUEST_WAITING_CONFIRM = "REQUEST_WAITING_CONFIRM";

    public static final String INVALID_PRODUCT = "INVALID_PRODUCT";
    public static final String OUT_OF_STOCK = "OUT_OF_STOCK";

    //employee role
    public static final String REWARD_TYPE_PROMOTION = "promotion";
    public static final String REWARD_TYPE_GIFT = "gift";


// Product

    public enum PRODUCT_TYPE {
        HOT_DEALS("hot-deal"),
        PROMOTION("promotion"),
        BEST_SELLER("best-seller"),
        RECENTLY_BOUGHT("recently-bought"),
        NEW_PRODUCT("new-product"),
        RELATED_PRODUCT("related-product"),
        VIEWED_PRODUCT("viewed-product");

        private String value;

        PRODUCT_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum TOP_DISPLAY_PRIORITY {
        TOP_PRIORITY(1), DEFAULT_PRIORITY(99999), OTHER_PRIORITY(3);
        private Integer index;

        TOP_DISPLAY_PRIORITY(Integer index) {
            this.index = index;
        }

        public Integer getIndex() {
            return index;
        }
    }

    //Status Notification
    public enum NOTIFY_STATUS {
        DISABLE(-1),
        DELETED(0),
        WAITING(1),
        SENT(2),
        ERROR(3),
        SEEN(4),
        LOYALTY_APPROVE(5),
        LOYALTY_DECLINE(6);

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        private int status;

        NOTIFY_STATUS(int status) {
            this.status = status;
        }
    }

    public enum DATA_CHANGE_TYPE {

        CREATE_PROMOTION(1),
        UPDATE_PROMOTION(2),
        DELETE_PROMOTION(3),
        UPDATE_OUTLET(4),
        ADD_OUTLET(5),
        CREATE_SAMPLING(6),
        UPDATE_SAMPLING(7),
        DELETE_SAMPLING(8);

        private int type;

        DATA_CHANGE_TYPE(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public enum OrderStatusCode {
        ALL,
        WAITING_FOR_CONFIRMATION,
        PREPARING,
        WAITING_FOR_PICKING,
        SUCCESS,
        CANCELED,
        SAVE_AS_DRAFT;
    }

    public enum STATIC_PAGE {
        WORKING("WORKING"),
        HIDNG("HIDING");
        private String status;

        STATIC_PAGE(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

    }

    public enum LOYALTY_MEMBERSHIP {
        NOT_MEMBER("NOT_MEMBER"),
        DECLINE("DECLINE"),
        WAITING_CUSTOMER_APPROVAL("WAITING_CUSTOMER_APPROVAL"),
        MEMBER("MEMBER"),
        NORMAL("NORMAL");

        private String status;

        LOYALTY_MEMBERSHIP(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum NOTIFY_TEMPLATE {
        FORGOT_PASSWORD("FORGOT_PASSWORD"),
        TEMPLATE_ORDER_STATUS("ORDER_CREATED_SUCCESS"),
        TEMPLATE_OUTLET_NEW_ORDER("OUTLET_NEW_ORDER"),
        TEMPLATE_CONFIRMATION_CODE("CONFIRMATION_CODE"),
        TEMPLATE_REDEEM_CODE("REDEEM_GIFT_CODE"),
        TEMPLATE_ORDER_STATUS_CHANGE("TEMPLATE_ORDER_STATUS_CHANGE"),
        SHOPPER_REQUEST_LOYALTY_MEMBER("SHOPPER_REQUEST_LOYALTY_MEMBER"),
        RETAILER_APPROVE_LOYALTY_MEMBER("RETAILER_APPROVE_LOYALTY_MEMBER"),
        OUTLET_INVITE_LOYALTY_MEMBER("OUTLET_INVITE_LOYALTY_MEMBER"),
        OUTLET_INVITE_MEMBER("OUTLET_INVITE_MEMBER"),
        OUTLET_CREATE_NEW_CUSTOMER("OUTLET_CREATE_NEW_CUSTOMER"),
        OUTLET_NEW_ORDER("OUTLET_NEW_ORDER"),
        ORDER_CREATED_SUCCESS("ORDER_CREATED_SUCCESS"),
        NEW_CAMPAIGN_AVAILABLE("NEW_CAMPAIGN_AVAILABLE"),
        OUTLET_CAMPAIGN_ABOUT_TO_START("OUTLET_CAMPAIGN_ABOUT_TO_START"),
        NEW_CUSTOMER_IMPORT("NEW_CUSTOMER_IMPORT"),
        WELCOME_FIRST_LOGIN("WELCOME_FIRST_LOGIN"),
        CUSTOMER_RATING("CUSTOMER_RATING"),
        OUTLET_REPLY_RATING_TYPE_NOTIFICATION("OUTLET_REPLY_RATING"),
        STATUS_PROMOTION_ACTIVE("STATUS_PROMOTION_ACTIVE"),
        STATUS_PROMOTION_INACTIVE("STATUS_PROMOTION_INACTIVE"),
        STATUS_LOYALTY_ACTIVE("STATUS_LOYALTY_ACTIVE"),
        STATUS_LOYALTY_INACTIVE("STATUS_LOYALTY_INACTIVE"),
        HAPPY_BIRTHDAY("HAPPY_BIRTHDAY"),

        DELIVERY_SHIPPER_REGISTER_SUCCESS("DELIVERY_SHIPPER_REGISTER_SUCCESS"),
        DELIVERY_RETAILER_NEW_SHIPPER_INFORM("DELIVERY_RETAILER_NEW_SHIPPER_INFORM"),
        DELIVERY_ASSIGN("DELIVERY_ASSIGN"),
        DELIVERY_SHIPPER_COMPLETE("DELIVERY_SHIPPER_COMPLETE"),
        DELIVERY_SHIPPER_CANCEL("DELIVERY_SHIPPER_CANCEL"),
        DELIVERY_SHIPPER_START("DELIVERY_SHIPPER_START"),

        OUTLET_HELP_ORDER("OUTLET_HELP_ORDER"),
        REDEEM_GIFT_SUCCESS("REDEEM_GIFT_SUCCESS"),
        OUTLET_SALESMAN_HELP_ORDER("OUTLET_SALESMAN_HELP_ORDER"),
        OUTLET_SALESMAN_NEW_ORDER("OUTLET_SALESMAN_NEW_ORDER"),
        EXPIRED_POINT("EXPIRED_POINT"),
        EXPIRED_DATE("EXPIRED_DATE"),
        QUIT_STORE_CONFIRMATION("QUIT_STORE_CONFIRMATION"),
        FORCED_LOGOUT("FORCED_LOGOUT"),
        //changed
        STORE_MEMBER_INVITE_CONFIRM("STORE_MEMBER_INVITE_CONFIRM"),
        STORE_MEMBER_INVITE_CONFIRM_MESSAGE("STORE_MEMBER_INVITE_CONFIRM_MESSAGE"),
        QUIT_STORE_REJECTED("QUIT_STORE_REJECTED"),
        QUIT_STORE_REJECTED_MESSAGE("QUIT_STORE_REJECTED_MESSAGE"),
        KICK_STORE_CONFIRMATION("KICK_STORE_CONFIRMATION"),
        KICK_STORE_CONFIRMATION_MESSAGE("KICK_STORE_CONFIRMATION_MESSAGE"),
        NEW_MEMBER_REJECTED("NEW_MEMBER_REJECTED"),
        NEW_MEMBER_APPROVED("NEW_MEMBER_APPROVED"),
        KICK_STORE_APPROVED("KICK_STORE_APPROVED"),
        KICK_STORE_REJECTED("KICK_STORE_REJECTED"),
        CUSTOMER_ACCEPTED_OTHERS("CUSTOMER_ACCEPTED_OTHERS");

        private String name;

        NOTIFY_TEMPLATE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum SALE_CHANEL {
        ONLINE("ONLINE"),
        OFFLINE("OFFLINE");

        private String name;

        SALE_CHANEL(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum CAMPAIGN_MISSION_STATUS {
        INACTIVE("INACTIVE"),
        DONE("DONE"),
        PENDING("PENDING"),
        EXPIRED("EXPIRED");

        private String value;

        CAMPAIGN_MISSION_STATUS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CAMPAIGN_REQUEST_STATUS {
        NOT_REQUEST("not_request"),
        APPROVED("approved"),
        RENEW_REQUEST("renew_requested"),
        REQUESTED("requested"),
        REJECTED("rejected"),
        LEAVE("leave");

        private String status;

        CAMPAIGN_REQUEST_STATUS(String value) {
            this.status = value;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum CAMPAIGN_FILTER_STATUS {
        NEWEST("NEWEST"),
        MOST_LIKED("MOST_LIKED"),
        NEARLY_EXPIRED("NEARLY_EXPIRED"),
        JOINING("JOINING");

        private String status;

        CAMPAIGN_FILTER_STATUS(String value) {
            this.status = value;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum OUTLET_CAMPAIGN_STATUS {
        RUNNING("RUNNING"),
        STOP("STOP"),
        REJECTED("REJECTED"),
        RENEW("RENEW"),
        LEAVE("LEAVE");

        private String status;

        OUTLET_CAMPAIGN_STATUS(String value) {
            this.status = value;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum CUSTOMER_CAMPAIGN_STATUS {
        PENDING("PENDING"),     // Customer are playing on this game
        INACTIVE("INACTIVE"),   // Customer still not reach this level
        DONE("DONE"),           // Customer finished this game
        SKIP("SKIP"),           // Customer restarted game and skip this game
        EXPIRED("EXPIRED");     // This campaign/mission/action was expired.

        private String status;

        CUSTOMER_CAMPAIGN_STATUS(String value) {
            this.status = value;
        }

        public String getStatus() {
            return status;
        }
    }

    // Outlet product status
    public enum OUTLET_PRODUCT_STATUS {
        STOP(-1),
        INACTIVE(0),
        ACTIVE(1);

        private int value;

        OUTLET_PRODUCT_STATUS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    // Promotion Types
    public enum PROMOTION_TYPE {
        BUY_ANY_X_GET_Y("BUY_ANY_X_GET_Y"),
        BUY_ANY_X_GET_GIFT("BUY_ANY_X_GET_GIFT"),
        OFFER_IN_ORDER("OFFER_IN_ORDER"),
        BUY_ANY_X_GET_DISCOUNT("BUY_ANY_X_GET_DISCOUNT"),
        BUY_PRODUCT_IN_BRAND_GET_DISCOUNT("BUY_PRODUCT_IN_BRAND_GET_DISCOUNT"),
        BIG_ORDER_BUY_X_GET_Y("BIG_ORDER_BUY_X_GET_Y"),
        ON_BOARDING_PROMOTION("ON_BOARDING_PROMOTION"),
        BUY_X_WITH_QUANTITY_N_GET_SOMETHING("BUY_X_WITH_QUANTITY_N_GET_SOMETHING"),
        BIG_ORDER_FREE_DELIVERY("BIG_ORDER_FREE_DELIVERY");

        public String getType() {
            return type;
        }

        private String type;

        PROMOTION_TYPE(String type) {
            this.type = type;
        }
    }

    // Frame
    public enum FRAME_URL {
        FRAMEBLUE("/documnet/store-level/frameBlue.png"),
        FRAMEGREEN("/documnet/store-level/frameGreen.png"),
        FRAMEYELLOW("/documnet/store-level/frameYellow.png");

        public String getUrl() {
            return url;
        }

        private String url;

        FRAME_URL(String url) {
            this.url = url;
        }
    }

    // E-Spot Target
    public enum ESPOT_TARGET_TYPE {
        TYPE_CAMPAIGN("CAMPAIGN"),
        TYPE_PROMOTION("PROMOTION"),
        TYPE_LOYALTY("LOYALTY"),
        TYPE_COLLECTION("COLLECTION");
        private String type;

        ESPOT_TARGET_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum ESPOT_CODE_TYPE {
        HOME_PAGE("HOME_PAGE"),
        HOME_COLLECTION("HOME_COLLECTION"),
        HOME_COLLECTION_FLAT("HOME_COLLECTION_FLAT");
        private String code;

        ESPOT_CODE_TYPE(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    // Promotion Types
    public enum ON_BOARDING_ADD_TERM_CART {
        SUCCESS("label.success.status"),
        NOT_FOUND_OUTLET("label.outlet.not.found.status"),
        EXISTED("label.device.joined.status"),
        TIME_EXPIRED("label.promotion.time.expired.status"),
        FAIL("label.fail.status");

        public String getType() {
            return type;
        }

        private String type;

        ON_BOARDING_ADD_TERM_CART(String type) {
            this.type = type;
        }
    }

    public enum LOYALTY_CONDITION_TYPE {
        PRODUCT("product"),
        CATEGORY("category"),
        VALUE_ORDER("valueOrder"),
        BRAND("brand");

        private String type;

        LOYALTY_CONDITION_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum SOCIAL_CHAT_TYPE {

        FACEBOOK("FACEBOOK"),
        ZALO("ZALO"),
        WHATSAPP("WHATSAPP"),
        VIBER("VIBER"),
        LINE("LINE"),
        WECHAT("WECHAT");

        private String value;

        SOCIAL_CHAT_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Payment Method Types
    public enum PAYMENT_METHOD_TYPE {
        PAYMENT("PAYMENT", 1),
        REFUND("REFUND", 2),
        ALL("ALL", 3);

        private String type;
        private Integer value;

        public String getType() {
            return type;
        }

        public Integer getValue() {
            return value;
        }

        PAYMENT_METHOD_TYPE(String type, Integer value) {
            this.type = type;
            this.value = value;
        }
    }

    // app setting
    public enum APP_SETTING {
        CONSTRAINT_DISTANCE("CONSTRAINT_DISTANCE"),
        LINK_WITH_INVENTORY("LINK_WITH_INVENTORY"),
        SHOW_IMAGE_WHEN_SEARCH("SHOW_IMAGE_WHEN_SEARCH"),
        ALWAY_ONBOARDING("ALWAY_ONBOARDING");

        private String value;

        APP_SETTING(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // delivery app reject reasons
    public enum DELIVERY_ORDER_STATUS {
        DELIVERY_ORDER_STATUS_COMPLETED("delivery.status.complete"),
        DELIVERY_ORDER_STATUS_CANNOT_CONTACT_WITH_CUSTOMER("delivery.status.cannot.contact.with.customer"),
        DELIVERY_ORDER_STATUS_CUSTOMER_REFUSE_ORDER("delivery.status.customer.refuse.order"),
        DELIVERY_ORDER_STATUS_CANNOT_FIND_CUSTOMER_ADDRESS("delivery.status.cannot.find.customer.address"),
        DELIVERY_ORDER_STATUS_PARTIAL_DELIVERY("delivery.status.partial.delivery");
        private String value;

        DELIVERY_ORDER_STATUS(String value) {
            this.value = value;
        }
    }

    public enum OUTLET_TYPE_SORT {
        MOST_CAMPAIGN("mostCampaign"),
        NEAREST("nearest");

        private String value;

        OUTLET_TYPE_SORT(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum DELIVERY_SHIPPER_COST {
        DROPPOINT_FEE("DROPPOINT_FEE"),
        DELIVER_FEE("DELIVER_FEE"),
        RETURN_FEE("RETURN_FEE");

        private String value;

        DELIVERY_SHIPPER_COST(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum GIFT_TYPE {
        PROMOTION("PROMOTION"),
        LOYALTY("LOYALTY"),
        CAMPAIGN("CAMPAIGN");

        private String value;

        GIFT_TYPE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ACTION {
        FILL_IN_THE_BLANK("FILL_IN_THE_BLANK"),
        CAPTURE_AND_SHARE("CAPTURE_AND_SHARE"),
        SHARE("SHARE");

        private String value;

        ACTION(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum DEBT_TYPE {
        PAY_BACK("PAY_BACK"),
        DEBT_CREDIT("DEBT_CREDIT"),
        RE_PAY_BACK("RE_PAY_BACK");

        private String type;

        DEBT_TYPE(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum QUIT_STORE_STATUS {
        NOT_QUIT("NOT_QUIT"),
        QUITTED("QUITTED"),
        SENT_QUIT_REQUEST("SENT_QUIT_REQUEST"),
        SENT_KICK_REQUEST("SENT_KICK_REQUEST");

        private String value;

        QUIT_STORE_STATUS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public enum LOYALTY_MEMBER_STATUS {
        INACTIVE(0),
        ACTIVE(1),
        PRIVATE(2),
        REMOVED(-1);
        private Integer type;

        LOYALTY_MEMBER_STATUS(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    public enum MEMBER_REQUEST_HISTORY_ACTION {
        MEMBER_QUIT_REQUEST_STORE,
        STORE_REQUEST_MEMBER,
        STORE_KICK_REQUEST_MEMBER,
        STORE_INVITE_CUSTOMER;
    }

    public enum MEMBER_REQUEST_HISTORY_STATUS {
        WAITING_FOR_CONFIRMATION,
        APPROVED,
        REJECTED,
        INVALID
    }

    public enum TERM_TYPES {
        PERIOD("PERIOD"), INVOICE("INVOICE");

        private String termType;

        TERM_TYPES(String termType) {
            this.termType = termType;
        }

        public String getTermType() {
            return termType;
        }
    }

    public enum STORE_PRIVACY {
        PRIVATE(0),
        PUBLIC(1);

        private Integer value;

        STORE_PRIVACY(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    public enum PROCESS_STATUS {
        WAITTING_FOR_UPDATE(0),
        UPDATED(1),
        FAIL(-1);

        PROCESS_STATUS(int value) {
            this.value = value;
        }

        private int value;

        public Integer getValue() {
            return value;
        }
    }
}
