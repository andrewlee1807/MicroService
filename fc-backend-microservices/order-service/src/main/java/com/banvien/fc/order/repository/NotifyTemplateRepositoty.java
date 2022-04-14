package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.NotifyTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyTemplateRepositoty extends JpaRepository<NotifyTemplateEntity,Long> {
    NotifyTemplateEntity findByCode(String code);

}
