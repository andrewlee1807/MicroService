package com.banvien.fc.email.repository;


import com.banvien.fc.email.entity.MailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplateEntity, Long>, JpaSpecificationExecutor<MailTemplateEntity> {
    MailTemplateEntity findByTemplateName(String param);
}
