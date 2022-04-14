package com.banvien.fc.email.repository;


import com.banvien.fc.email.entity.MailTemplateEntity;
import com.banvien.fc.email.entity.OutletPromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface OutletPromotionRepository extends JpaRepository<OutletPromotionEntity, Long>{
    List<OutletPromotionEntity> findByNotifySendDateBeforeAndNotifySentDateIsNull(Timestamp now);

    @Query(value = "select o.outletPromotionId FROM OutletPromotionEntity o WHERE o.newPromotionJson like :pattern and o.status = true")
    List<Long> findByNewPromotionJson(@Param("pattern") String pattern);
}
