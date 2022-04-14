package com.banvien.fc.email;

import com.banvien.fc.email.entity.NotificationEntity;
import com.banvien.fc.email.entity.OutletPromotionEntity;
import com.banvien.fc.email.repository.NotificationRepository;
import com.banvien.fc.email.repository.OutletPromotionRepository;
import com.banvien.fc.email.repository.UserRepository;
import com.banvien.fc.email.utils.CoreConstants;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class EmailServiceApplicationTests {

    @Test
    void contextLoads() {
        sendPendingMail();
    }

    @Autowired
    private OutletPromotionRepository outletPromotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public void sendPendingMail() {
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.findByNotifySendDateBeforeAndNotifySentDateIsNull(new Timestamp(System.currentTimeMillis()));
        for (OutletPromotionEntity outletPromotionEntity : outletPromotionEntities) {
            try {
                if (Math.abs(outletPromotionEntity.getNotifySendDate().getTime() - System.currentTimeMillis()) < 1000 * 60 * 15) {
                    continue;
                }
                List<Long> userIds;
                JSONObject json = new JSONObject(outletPromotionEntity.getNewPromotionJson());
                List<Long> customerTargetIds = new ArrayList<>();
                try {
                    JSONArray arr = json.getJSONArray("customerTargetIds");
                    for (int i = 0; i < arr.length(); i++) {
                        customerTargetIds.add(arr.getLong(i));
                    }
                } catch (Exception ex) {
                }
                if (customerTargetIds.size() > 0) {
                    userIds = customerTargetIds;
                } else {
                    if (json.getJSONObject("promotionRule").getBoolean("isAll")) {
                        userIds = userRepository.findUserIdsInOutlet(outletPromotionEntity.getOutletId());
                    } else {
                        List<String> groups = Arrays.asList((String[]) json.get("customerGroupIds"));
                        userIds = userRepository.findUserIdsByGroupAndOutlet(groups, outletPromotionEntity.getOutletId());
                    }
                }
                for (Long userId : userIds) {
                    List<String> langs = userRepository.findDistinctByUserId(userId);
                    for (String lang : langs) {
                        NotificationEntity notificationEntity = new NotificationEntity();
                        notificationEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        notificationEntity.setNotifyTemplateId(null);
                        notificationEntity.setMessage(outletPromotionEntity.getNotifyContent());
                        notificationEntity.setStatus(CoreConstants.NOTIFY_STATUS.WAITING.getStatus());
                        notificationEntity.setType(CoreConstants.NOTIFICATION_TYPE);
                        notificationEntity.setScheduledDate(outletPromotionEntity.getNotifySendDate());
                        notificationEntity.setUserId(userId);
                        notificationEntity.setSentDate(notificationEntity.getCreatedDate());
                        notificationEntity.setOutlet(outletPromotionEntity.getOutletId());
                        notificationEntity.setTargetId(outletPromotionEntity.getOutletPromotionId());
                        notificationEntity.setTargetType(CoreConstants.PROMOTION_TYPE_NOTIFICATION);
                        notificationEntity.setTitle(outletPromotionEntity.getTitle());
                        notificationEntity.setSendByManual(Boolean.FALSE);
                        notificationEntity.setLang(lang);
                        notificationRepository.save(notificationEntity);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            outletPromotionEntity.setNotifySentDate(new Timestamp(System.currentTimeMillis()));
            outletPromotionRepository.save(outletPromotionEntity);
        }
        notificationRepository.flush();
    }
}
