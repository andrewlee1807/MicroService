package com.banvien.fc.email.scheduler;

import com.banvien.fc.common.util.CommonUtils;
import com.banvien.fc.email.entity.GiftEntity;
import com.banvien.fc.email.entity.NotificationEntity;
import com.banvien.fc.email.entity.NotifyTemplateEntity;
import com.banvien.fc.email.entity.OutletPromotionEntity;
import com.banvien.fc.email.repository.*;
import com.banvien.fc.email.service.EmailService;
import com.banvien.fc.email.utils.CoreConstants;
import com.banvien.fc.email.utils.MessageBeanUtil;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ScheduledTasks {

    @Autowired
    private OutletPromotionRepository outletPromotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "${notify.send.cronjob}")
    public void sendPendingMail() {
        List<OutletPromotionEntity> outletPromotionEntities = outletPromotionRepository.findByNotifySendDateBeforeAndNotifySentDateIsNull(new Timestamp(System.currentTimeMillis()));
        for (OutletPromotionEntity outletPromotionEntity : outletPromotionEntities) {
            try {
                if (outletPromotionEntity.getNotifySendDate() == null) continue;
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

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void checkExpire() {
        Timestamp startOfDate = CommonUtils.move2BeginTimeOfDay(new Timestamp(System.currentTimeMillis()));
        Timestamp endOfDate = CommonUtils.move2EndTimeOfDay(new Timestamp(System.currentTimeMillis()));
        try {
            List<GiftEntity> gifts = giftRepository.findByExpiredDateBetween(startOfDate, endOfDate);
            for (GiftEntity gift : gifts) {
                String pattern = "%\"targetIds\":[" + gift.getGiftId() + "]%";
                List<Long> outletPromotionIds = outletPromotionRepository.findByNewPromotionJson(pattern);
                for (Long outletPromotionId : outletPromotionIds) {
                     emailService.alertProtionEnd(outletPromotionId);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
