package com.banvien.fc.email.service;

import com.banvien.fc.email.dto.AttachmentFileDTO;
import com.banvien.fc.email.dto.MailTemplateDTO;
import com.banvien.fc.email.dto.MailTrackingDTO;
import com.banvien.fc.email.dto.SendMailDTO;
import com.banvien.fc.email.entity.MailTemplateEntity;
import com.banvien.fc.email.entity.NotificationEntity;
import com.banvien.fc.email.entity.NotifyTemplateEntity;
import com.banvien.fc.email.entity.OutletPromotionEntity;
import com.banvien.fc.email.repository.*;
import com.banvien.fc.email.utils.CoreConstants;
import com.banvien.fc.email.utils.MessageBeanUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private OutletRepository outletRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotifyTemplateRepository notifyTemplateRepository;
    @Autowired
    private OutletPromotionRepository outletPromotionRepository;

    @Autowired
    private MailTemplateRepository mailTemplateRepository;
    private Integer MAIL_SENT_SUCCESS = 1;
    private Integer MAIL_SENT_FAILURE = -1;
    private Integer MAIL_WAITING_SEND = 0;
    private Integer MAIL_STOP_SEND = 2;

    public void sendMessageByTemplate(SendMailDTO dto) throws MessagingException {
        try {
            if (dto.getSendNow() != null && dto.getSendNow()) {
                sendMailImmediately(dto);
            } else {
               // saveAndSendLate(dto);
            }
        } catch (NoSuchElementException ex) {
            throw new MessagingException(ex.getMessage());
        } catch (Exception ex) {
            throw new MessagingException(ex.getMessage());
        }
    }

    public void alertProtionEnd(Long outletPromotionId) {
        Optional<OutletPromotionEntity> promotion = outletPromotionRepository.findById(outletPromotionId);
        OutletPromotionEntity promotionEntity;
        if (promotion.isPresent()) {
            promotionEntity = promotion.get();
            if (promotionEntity.getStatus()) {
                promotionEntity.setStatus(false);
                outletPromotionRepository.save(promotionEntity);
                List<Long> userIds = outletRepository.findByOutletId(promotionEntity.getOutletId());
                userIds.addAll(getCustomerUserIds(promotionEntity));
                sendNotificationToOutletAndCustomer(promotionEntity, userIds);
            }
        }
    }

    private void sendMailImmediately(SendMailDTO dto) throws MessagingException {
        try {
            Context context = new Context();
            Map<String, Object> sender = dto.getSender();
            if (sender != null && sender.size() > 0) {
                Set<String> keySet = sender.keySet();
                for (String key : keySet) {
                    context.setVariable(key, sender.get(key));
                }
            }
//            MailTemplateEntity template = mailTemplateRepository.findByTemplateName(dto.getMailTemplate());

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String subject = templateEngine.process("ORDER INFORMATION", context);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(dto.getRecipients());
            mimeMessageHelper.setFrom("SmartShopper");

            mimeMessageHelper.setTo(dto.getRecipients());


            String htmlContent = templateEngine.process("submit_order.html", context);

            mimeMessageHelper.setText(htmlContent, true);

            // Send email
            emailSender.send(mimeMessage);

            //Store history email
            //saveMailTracking(dto, htmlContent, template.getSubject(), this.MAIL_SENT_SUCCESS);
        } catch (NoSuchElementException ex) {
            throw new MessagingException(ex.getMessage());
        } catch (Exception ex) {
            throw new MessagingException(ex.getMessage());
        }
    }

    private void saveMailTracking(SendMailDTO dto, String htmlContent, String subject, Integer status) throws Exception {
        MailTrackingDTO mailTrackingDTO = new MailTrackingDTO();
        mailTrackingDTO.setRecipient(Arrays.toString(dto.getRecipients()).replace("[", "").replace("]", ""));
        if (dto.getCc() != null) {
            mailTrackingDTO.setCc(Arrays.toString(dto.getCc()));
        }
        if (dto.getBcc() != null) {
            mailTrackingDTO.setBcc(Arrays.toString(dto.getBcc()));
        }
        mailTrackingDTO.setSubject(subject);
        mailTrackingDTO.setBody(htmlContent);
        mailTrackingDTO.setCreatedDate(Instant.now());
        mailTrackingDTO.setStatus(status);
       // mailTrackingService.save(mailTrackingDTO);
    }

    private MimeMessageHelper getMimeMessageHelperFromMailTracking(MailTrackingDTO dto, MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper mimeMessageHelper = null;
        if (StringUtils.isNotEmpty(dto.getFileAttachments())) {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            TypeReference<List<AttachmentFileDTO>> mapType = new TypeReference<List<AttachmentFileDTO>>() {
            };
            List<AttachmentFileDTO> attachFiles = objectMapper.readValue(dto.getFileAttachments(), mapType);

            for (AttachmentFileDTO attachDTO : attachFiles) {
                File file = new File(attachDTO.getFilePath());
                mimeMessageHelper.addAttachment(attachDTO.getFileName(), file);
            }
        } else {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        }
        return mimeMessageHelper;
    }

    private void sendNotificationToOutletAndCustomer(OutletPromotionEntity outletPromotion, List<Long> userIds) {
        NotifyTemplateEntity notifyTemplateEntity =
                notifyTemplateRepository.findByCode(CoreConstants.NOTIFY_TEMPLATE.PROMOTION_INVALID_BY_GIFT.getName());
        Map<String, Object> properties = new HashMap<>();
        properties.put("outletPromotionName", outletPromotion.getTitle());
        for (Long userId : userIds) {
            List<String> listLang = userRepository.findDistinctByUserId(userId);
            for (String lang : listLang) {
                NotificationEntity notificationEntity = new NotificationEntity();
                String messageSent = MessageBeanUtil.getContentFromVelocityTemplate(MessageBeanUtil.getNotificationWithLanguage(notifyTemplateEntity, lang), properties);
                notificationEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                notificationEntity.setNotifyTemplateId(null);
                notificationEntity.setMessage(messageSent);
                notificationEntity.setStatus(CoreConstants.NOTIFY_STATUS.WAITING.getStatus());
                notificationEntity.setType(CoreConstants.NOTIFICATION_TYPE);
                notificationEntity.setScheduledDate(notificationEntity.getCreatedDate());
                notificationEntity.setSentDate(notificationEntity.getCreatedDate());
                notificationEntity.setSendByManual(Boolean.FALSE);
                notificationEntity.setTitle(notifyTemplateEntity.getTitle());
                notificationEntity.setUserId(userId);
                notificationEntity.setNotifyTemplateId(notifyTemplateEntity.getNotifyTemplateId());
                notificationEntity.setOutletPromotionId(outletPromotion.getOutletPromotionId());
                notificationEntity.setTargetType(CoreConstants.PROMOTION_TYPE_NOTIFICATION);
                notificationEntity.setTargetId(outletPromotion.getOutletPromotionId());
                notificationEntity.setLang(lang);
                notificationRepository.save(notificationEntity);
            }
        }
    }

    private List<Long> getCustomerUserIds(OutletPromotionEntity outletPromotion){
        List<Long> userIds = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(outletPromotion.getNewPromotionJson());
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
                    userIds = userRepository.findUserIdsInOutlet(outletPromotion.getOutletId());
                } else {
                    List<String> groups = Arrays.asList((String[]) json.get("customerGroupIds"));
                    userIds = userRepository.findUserIdsByGroupAndOutlet(groups, outletPromotion.getOutletId());
                }
            }
        }catch (Exception e){
        }
        return userIds;
    }
}
