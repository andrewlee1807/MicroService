package com.banvien.fc.order.utils;

import com.banvien.fc.order.entity.*;

import java.sql.Timestamp;
import java.util.*;

public class LoyaltyOutletEventBeanUtils {

    public static LoyaltyEventOrderAmountEntity selectOneEventOrderAmount(List<LoyaltyEventOrderAmountEntity> orderAmountEvents) {
        if (orderAmountEvents != null && orderAmountEvents.size() > 0) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            Map<Long, LoyaltyEventOrderAmountEntity> mapEvent4AllCustomer = new HashMap<>();
            Map<Long, LoyaltyEventOrderAmountEntity> mapEvent4CustomerGroup = new HashMap<>();
            Map<Long, LoyaltyEventOrderAmountEntity> mapEvent = new HashMap<>();

            for (LoyaltyEventOrderAmountEntity entity : orderAmountEvents) {
                LoyaltyOutletEventEntity loyaltyOutletEventEntity = entity.getLoyaltyOutletEvent();
                Timestamp startDate = loyaltyOutletEventEntity.getStartDate();
                Timestamp endDate = loyaltyOutletEventEntity.getEndDate();
                Timestamp endDateWithMaxTime = (endDate != null) ? getEndDateWithMaxTime(endDate) : new Timestamp(System.currentTimeMillis());

                if (loyaltyOutletEventEntity.getStatus() != null && CoreConstants.ACTIVE.equals(loyaltyOutletEventEntity.getStatus()) &&
                        ((startDate == null && endDate == null) || (currentTime.after(startDate) && currentTime.before(endDateWithMaxTime)))) {

                    if (loyaltyOutletEventEntity.getCustomersTarget() != null && loyaltyOutletEventEntity.getCustomersTarget().size() > 0) { // approve for customer group
                        mapEvent4CustomerGroup.put(entity.getLoyaltyEventOrderAmountId(), entity);
                    } else { // approve for all customer
                        mapEvent4AllCustomer.put(entity.getLoyaltyEventOrderAmountId(), entity);
                    }
                }
            }

            // 1. priority applies for event have specify customer group
            if (!mapEvent4CustomerGroup.isEmpty()) {
                mapEvent.putAll(mapEvent4CustomerGroup);
            } else { // less priority
                mapEvent.putAll(mapEvent4AllCustomer);
            }

            // 2. latest event
            LoyaltyEventOrderAmountEntity latestEvent = new LoyaltyEventOrderAmountEntity();
            Set<Long> keys = mapEvent.keySet();
            for (Long eventId : keys) {
                LoyaltyEventOrderAmountEntity eventEntity = mapEvent.get(eventId);
                if (latestEvent.getLoyaltyOutletEvent() == null || (latestEvent.getLoyaltyOutletEvent().getCreatedDate().before(eventEntity.getLoyaltyOutletEvent().getCreatedDate()))) {
                    latestEvent = eventEntity;
                }
            }

            return latestEvent;
        }

        return null;
    }

    public static LoyaltyOutletEventEntity selectOneEventFirstOrder(List<LoyaltyEventFirstOrderEntity> eventsFirstOrder) {
        if (eventsFirstOrder != null && eventsFirstOrder.size() > 0) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            Map<Long, LoyaltyOutletEventEntity> mapEvent4AllCustomer = new HashMap<>();

            for (LoyaltyEventFirstOrderEntity entity : eventsFirstOrder) {

                LoyaltyOutletEventEntity outletEventEntity = entity.getLoyaltyOutletEvent();

                Timestamp startDate = outletEventEntity.getStartDate();
                Timestamp endDate = outletEventEntity.getEndDate();
                Timestamp endDateWithMaxTime = (endDate != null) ? getEndDateWithMaxTime(endDate) : new Timestamp(System.currentTimeMillis());

                if (outletEventEntity.getStatus() != null && CoreConstants.ACTIVE.equals(outletEventEntity.getStatus()) &&
                        ((startDate == null && endDate == null)
                                || (currentTime.after(startDate)
                                && currentTime.before(endDateWithMaxTime)))) {

                    mapEvent4AllCustomer.put(outletEventEntity.getLoyaltyOutletEventId(), outletEventEntity);

                }
            }

            LoyaltyOutletEventEntity latestEvent = new LoyaltyOutletEventEntity();
            Set<Long> keys = mapEvent4AllCustomer.keySet();
            for (Long eventId : keys) {
                LoyaltyOutletEventEntity eventEntity = mapEvent4AllCustomer.get(eventId);
                if (latestEvent.getCreatedDate() == null || (latestEvent.getStartDate().before(eventEntity.getStartDate()))) {
                    latestEvent = eventEntity;
                }
            }

            return latestEvent;

        }
        return null;
    }

    public static LoyaltyOutletEventEntity selectOneEventOrderProduct(List<LoyaltyOutletEventEntity> eventOrderProducts) {
        if (eventOrderProducts != null && eventOrderProducts.size() > 0) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            Map<Long, LoyaltyOutletEventEntity> mapEvent4AllCustomer = new HashMap<>();
            Map<Long, LoyaltyOutletEventEntity> mapEvent4CustomerGroup = new HashMap<>();
            Map<Long, LoyaltyOutletEventEntity> mapEvent = new HashMap<>();

            for (LoyaltyOutletEventEntity loyaltyOutletEventEntity : eventOrderProducts) {

                Timestamp startDate = loyaltyOutletEventEntity.getStartDate();
                Timestamp endDate = loyaltyOutletEventEntity.getEndDate();
                Timestamp endDateWithMaxTime = (endDate != null) ? getEndDateWithMaxTime(endDate) : new Timestamp(System.currentTimeMillis());

                if (CoreConstants.ACTIVE.equals(loyaltyOutletEventEntity.getStatus()) &&
                        ((startDate == null && endDate == null) ||
                                (currentTime.after(startDate) && currentTime.before(endDateWithMaxTime)))) {

                    if (loyaltyOutletEventEntity.getCustomersTarget() != null && loyaltyOutletEventEntity.getCustomersTarget().size() > 0) { // approve for customer group
                        mapEvent4CustomerGroup.put(loyaltyOutletEventEntity.getLoyaltyOutletEventId(), loyaltyOutletEventEntity);
                    } else { // approve for all customer
                        mapEvent4AllCustomer.put(loyaltyOutletEventEntity.getLoyaltyOutletEventId(), loyaltyOutletEventEntity);
                    }
                }
            }

            // 1. priority applies for event have specify customer group
            if (!mapEvent4CustomerGroup.isEmpty()) {
                mapEvent.putAll(mapEvent4CustomerGroup);
            } else { // less priority
                mapEvent.putAll(mapEvent4AllCustomer);
            }

            // 2. latest event
            LoyaltyOutletEventEntity latestEvent = new LoyaltyOutletEventEntity();
            Set<Long> keys = mapEvent.keySet();
            for (Long eventId : keys) {
                LoyaltyOutletEventEntity eventEntity = mapEvent.get(eventId);
                if (latestEvent.getCreatedDate() == null || (latestEvent.getCreatedDate().before(eventEntity.getCreatedDate()))) {
                    latestEvent = eventEntity;
                }
            }

            return latestEvent;
        }
        return null;
    }

    public static Boolean checkCustomerGroupApproveLoyalty(LoyaltyOutletEventEntity loyaltyOutletEvent, LoyaltyMemberEntity loyaltyMemberEntity) {

        if (loyaltyOutletEvent.getCustomersTarget() == null || loyaltyOutletEvent.getCustomersTarget().size() <= 0 || loyaltyMemberEntity == null) { // apply for all customer and case not login
            return true;
        }

        CustomerGroupEntity customerGroupEntity = loyaltyMemberEntity.getCustomerGroup();

        if (customerGroupEntity == null) {
            return false;
        }

        for (CustomerGroupEntity customerGroupInProgram : loyaltyOutletEvent.getCustomersTarget()) {
            if (customerGroupInProgram.getCustomerGroupId().equals(customerGroupEntity.getCustomerGroupId())) {
                return true;
            }
        }

        return false;
    }

    //get date with time is 23:59:59.0
    public static Timestamp getEndDateWithMaxTime (Timestamp endDate) {
        // 86400000 milliseconds in a day
        long oneDay = 1 * 24 * 60 * 60 * 1000 - 1000;
        // to add to the timestamp
        return new Timestamp(endDate.getTime() + oneDay);
    }

}
