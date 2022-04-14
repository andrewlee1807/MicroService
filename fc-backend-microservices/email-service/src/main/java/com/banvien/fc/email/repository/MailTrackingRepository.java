package com.banvien.fc.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailTrackingRepository  {
//    @Query("FROM MailTracking m WHERE m.status IN(:pendingStatus)")
//    List<MailTracking> findAllByStatusContains(@Param("pendingStatus") List<Integer> pendingStatus);
//    @Modifying
//    @Query("UPDATE MailTracking m SET m.status = :status, m.note = :note where m.mailTrackingId = :mailTrackingId")
//    void changeStatusWithNote(@Param("mailTrackingId") Long mailTrackingId, @Param("status") Integer status, @Param("note") String note);


}
