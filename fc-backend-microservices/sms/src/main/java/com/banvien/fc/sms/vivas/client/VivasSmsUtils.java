package com.banvien.fc.sms.vivas.client;

import com.banvien.fc.sms.main.SmsUtils;
import com.banvien.fc.sms.utils.DateUtil;
import com.banvien.fc.sms.utils.MD5Utils;
import com.banvien.fc.sms.vivas.client.model.MsgInfo;
import com.banvien.fc.sms.vivas.client.model.SmsReq;
import com.banvien.fc.sms.vivas.client.model.SmsRes;
import com.banvien.fc.sms.vivas.client.model.UserReq;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MediaType;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class VivasSmsUtils extends SmsUtils {
    private static String COOKIE = null;
    private static final String NO_COOKIE = "20";
    private static final String TYPE = "1";
    private static final String ISUNICODE = "0";
    private static String CHECK_SUM_FORMAT = "username={0}&password={1}&brandname={2}&sendtime={3}&msgid={4}&msg={5}&msisdn={6}&sharekey={7}";

    private String brandname;
    private String urlLogin;
    private String urlSendSms;
    private String username;
    private String password;
    private String sharekey;

    public VivasSmsUtils() {
        urlLogin = VivasSmsConfig.getInstance().getProperty("vivas.url.login");
        urlSendSms = VivasSmsConfig.getInstance().getProperty("vivas.url.send.sms");
        brandname = VivasSmsConfig.getInstance().getProperty("vivas.brandname");
        username = VivasSmsConfig.getInstance().getProperty("vivas.username");
        password = VivasSmsConfig.getInstance().getProperty("vivas.password");
        sharekey = VivasSmsConfig.getInstance().getProperty("vivas.sharekey");
    }

    @Override
    public Object[] sendSms(String phoneNumber, String message) {
        boolean success = false;
        int statusCode = 0;
        String errorMessage = "";
        String remoteRequestId = UUID.randomUUID().toString();
        if (StringUtils.isNotBlank(phoneNumber) && StringUtils.isNotBlank(message) && phoneNumber.length() > 8) {
            phoneNumber = phoneNumber.trim();
            message = message.trim();
            int len = phoneNumber.length();
            phoneNumber = phoneNumber.substring(len - 9, len);
            /*if (phoneNumber.startsWith("0")) {
                phoneNumber = phoneNumber.substring(1);
            }
            if (!phoneNumber.startsWith("84")) {
                phoneNumber = "84" + phoneNumber;
            }*/
            phoneNumber = "84" + phoneNumber;
            System.out.println(">>>>---S--e--n--d--sms--to: " + phoneNumber);
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)
                    && StringUtils.isNotBlank(brandname) && StringUtils.isNotBlank(sharekey)) {
                System.out.println(">>>>---step 1 ");
                System.out.println(">> COOKIE " +  COOKIE);
                if (COOKIE == null) {
                    System.out.println(">>>>---step 2 ");
                    ClientResponse resLogin = RestClientUtils.instance(urlLogin, loginInfo(), COOKIE, MediaType.APPLICATION_XML_TYPE, null);
                    COOKIE = RestClientUtils.cookie(resLogin);
                }
                System.out.println(">>>>---step 3 ");
                System.out.println(">> COOKIE " +  COOKIE);
                ClientResponse response = RestClientUtils.instance(urlSendSms,
                        sendSmsInfo(phoneNumber, message, remoteRequestId), COOKIE, MediaType.APPLICATION_XML_TYPE, null);
                SmsRes smsRes = response.getEntity(SmsRes.class);
                System.out.println(smsRes.getReqId());
                System.out.println(smsRes.getStatus());
                if (NO_COOKIE.equals(smsRes.getStatus())) {
                    COOKIE = null;
                    return sendSms(phoneNumber, message);
                }
                statusCode = Integer.valueOf(smsRes.getStatus());
                success = statusCode == 0;
                errorMessage = getErrorMessage(statusCode);

                System.out.println(">>>>---result: " + errorMessage);
            }
        }
        return new Object[]{success, statusCode, errorMessage, remoteRequestId};
    }

    private UserReq loginInfo() {
        UserReq userReq = new UserReq(username, password);
        return userReq;
    }

    private SmsReq sendSmsInfo(String phoneNumber, String message, String remoteRequestId) {
        message = utf8toAscii(message);
        String sendDate = DateUtil.date2String(new Date(), "yyyyMMddHHmmss");
        SmsReq smsReq = new SmsReq(remoteRequestId, brandname, message, sendDate, TYPE, ISUNICODE);
        MsgInfo msgInfo = new MsgInfo(remoteRequestId, phoneNumber,
                checksum(phoneNumber, message, remoteRequestId, sendDate));
        smsReq.setMsgInfo(msgInfo);
        return smsReq;
    }

    public static String utf8toAscii(String message) {
        String nfdNormalizedString = Normalizer.normalize(message, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("")
                .replace("đ", "d").replace("Đ", "D");
    }

    private String checksum(String phoneNumber, String message, String remoteRequestId, String sendDate) {
        String checksum = new MessageFormat(CHECK_SUM_FORMAT).format(new Object[]{username, password, brandname,
                sendDate, remoteRequestId, message, phoneNumber, sharekey});
        return MD5Utils.md5(checksum);
    }

    private static String getErrorMessage(int statusCode) {
        switch (statusCode) {
            case 0:
                return "Request được tiếp nhận thành công";
            case 3:
                return "Request bị từ chối vì Brandname không tồn tại hoặc không thuộc sở hữu";
            case 4:
                return "Request bị từ chối vì không tìm thấy template hoặc không đúng template";
            case 5:
                return "Request bị từ chối vì chứa một checksum sai";
            case 6:
                return "Request bị từ chối vì trùng ID";
            case 8:
                return "Request bị từ chối vì vượt hạn mức gửi tin";
            case 9:
                return "Request bị từ thối vì thiếu loại SMS";
            case 10:
                return "Request bị từ chối vì thiếu thời gian gửi";
            case 12:
                return "Request bị từ chối vì trùng msgid";
            case 13:
                return "Request bị từ chối vì vượt quá số lượng số điện thoại trong request";
            case 14:
                return "Request bị từ chối vì chứa số điện thoại sai";
            case 20:
                return "Request bị từ chối vì chưa đăng nhập hoặc mất session";
            case 21:
                return "Request bị từ chối vì quá số lượng request đồng thời đến hệ thống";
            case 98:
                return "Lỗi sai protocol gọi request";
            case 99:
                return "Lỗi thiếu tham số gọi request ";
            default:
                return "Lỗi xử lý";
        }
    }
}
