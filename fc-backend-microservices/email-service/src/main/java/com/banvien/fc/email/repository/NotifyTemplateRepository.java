package com.banvien.fc.email.repository;

import com.banvien.fc.email.entity.GiftEntity;
import com.banvien.fc.email.entity.NotifyTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyTemplateRepository extends JpaRepository<NotifyTemplateEntity, Long> {
    NotifyTemplateEntity findByCode(String Code);

}
