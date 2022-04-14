package com.banvien.fc.catalog.scheduler;

import com.banvien.fc.catalog.service.SalesFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {
    @Scheduled(cron = "${cron.updateStoreAndCustomer}")
    public void updateStoreAndCustomer() throws Exception {
        salesFlowService.updateDistributorMasterData();
        salesFlowService.updateStoreMasterData();
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void updateCatalog() throws Exception {
        // sleep for gateway start
        TimeUnit.MINUTES.sleep(5);
        salesFlowService.updateProductMasterData();
    }

    @Autowired
    private SalesFlowService salesFlowService;
}
