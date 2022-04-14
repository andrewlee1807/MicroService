package com.banvien.fc.sms.vivas.client;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Properties;

public class VivasSmsConfig extends Properties {
    /**
     *
     */
    private static final long serialVersionUID = 15644675332534645L;

    private Log log = LogFactory.getLog(getClass());

    private static final String CONFIG_FILE = "vivas.properties";

    private static VivasSmsConfig instance;


    private VivasSmsConfig() {
        try {
            put("vivas.url.login", "http://mkt.vivas.vn:9080/SMSBNAPI/login");
            put("vivas.url.send.sms", "http://mkt.vivas.vn:9080/SMSBNAPI/send_sms");
            put("vivas.url.send.ext", "");
            put("vivas.url.verify", "");
            put("vivas.url.verify.ext", "");
            put("vivas.brandname", "CoGaiHaLan");
            put("vivas.sharekey", "123456");
            put("vivas.username", "fcv2018");
            put("vivas.password", "B2AKxSOGxDapMgSDQzVtAccJPB4=");
        } catch (Exception e) {
            log.error("Could not load settting vivas: " + e.getMessage(), e);
        }
    }

    public static VivasSmsConfig getInstance() {
        if (instance == null) {
            instance = new VivasSmsConfig();
        }
        return instance;
    }

    public void setProperties(Map<String, String> properties) {
        clear();
        putAll(properties);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String s = getProperty(key);
        if (s == null) {
            s = defaultValue;
        }
        return s;
    }
}
