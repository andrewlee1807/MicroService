package com.banvien.fc.email.utils;

import com.banvien.fc.email.entity.NotifyTemplateEntity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

public class MessageBeanUtil {

    private static final String TAG_VELOCITY = "VELOCITY";

    public static String getContentFromVelocityTemplate(String templateContent, Map<String, Object> model) {
        VelocityContext context = new VelocityContext();
        StringWriter writer = new StringWriter();
        try {
            if (model != null) {
                Set<String> keys = model.keySet();
                for (String key : keys) {
                    context.put(key, model.get(key));
                }
            }

            StringReader reader = new StringReader(templateContent);
            Velocity.evaluate(context, writer, TAG_VELOCITY, reader);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return writer.toString();
    }

    public static String getNotificationWithLanguage (NotifyTemplateEntity notifyTemplateEntity, String language) {
        language = language != null ? language : "en";

        switch (language) {
            case CoreConstants.VIETNAMESE:
                return notifyTemplateEntity.getContentVi();
            case CoreConstants.INDONESIA:
            case CoreConstants.INDONESIA_IN:
                return notifyTemplateEntity.getContentId();
            case CoreConstants.MALAYSIA:
            case CoreConstants.MALAYSIA_MY:
                return notifyTemplateEntity.getContentMa();
            case CoreConstants.CHINA:
            case CoreConstants.CHINA_ZH:
                return notifyTemplateEntity.getContentCn();
            default:
                return notifyTemplateEntity.getContentEn();
        }
    }
}