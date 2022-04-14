package com.banvien.fc.order.scheduler;

import com.banvien.fc.order.entity.OrderOutletEntity;
import com.banvien.fc.order.entity.RawOrderStatusEntity;
import com.banvien.fc.order.repository.OrderOutletRepository;
import com.banvien.fc.order.repository.RawOrderStatusRepository;
import com.banvien.fc.order.service.SalesFlowService;
import com.banvien.fc.order.utils.CoreConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {

    @Autowired
    private SalesFlowService salesFlowService;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void bulkOrderStatus() throws JsonProcessingException {
        salesFlowService.bulkOrderStatus();
    }
}
