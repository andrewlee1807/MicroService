package com.banvien.fc.email.repository;

import com.banvien.fc.email.entity.GiftEntity;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface GiftRepository  extends JpaRepository<GiftEntity, Long> {
   List<GiftEntity> findByExpiredDateBetween(Timestamp startDate, Timestamp endDate);
}
