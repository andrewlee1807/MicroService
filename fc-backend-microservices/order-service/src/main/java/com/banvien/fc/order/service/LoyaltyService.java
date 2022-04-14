package com.banvien.fc.order.service;

import com.banvien.fc.order.entity.*;
import com.banvien.fc.order.repository.*;
import com.banvien.fc.order.utils.CommonBeanUtils;
import com.banvien.fc.order.utils.CoreConstants;
import com.banvien.fc.order.utils.LoyaltyOutletEventBeanUtils;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
public class LoyaltyService {

    @Autowired
    private LoyaltyMemberRepository loyaltyMemberRepository;
    @Autowired
    private LoyaltyOrderHistoryRepository loyaltyOrderHistoryRepository;
    @Autowired
    private LoyaltyOutletEventRepository loyaltyOutletEventRepository;
    @Autowired
    private LoyaltyMasterEventRepository loyaltyMasterEventRepository;
    @Autowired
    private LoyaltyPointHistoryRepository loyaltyPointHistoryRepository;
    @Autowired
    private LoyaltyEventOrderAmountRepository loyaltyEventOrderAmountRepository;
    @Autowired
    private LoyaltyEventFirstOrderRepository loyaltyEventFirstOrderRepository;
    @Autowired
    private OrderOutletRepository orderOutletRepository;
    @Autowired
    private CustomerDebHistoryRepository customerDebHistoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OutletRepository outletRepository;

    public void updatePointWhenSuccessOrder(OrderOutletEntity orderOutletEntity) throws Exception {
        //Find List OrderOutletItems
        LoyaltyMemberEntity loyaltyMemberEntity = loyaltyMemberRepository.findByCustomerIdAndOutletOutletId(orderOutletEntity.getCustomerId(), orderOutletEntity.getOutlet().getOutletId());
        List<LoyaltyOrderHistoryEntity> loyaltyOrderHistoryEntities = loyaltyOrderHistoryRepository.findByOrOrderOutletCode(orderOutletEntity.getCode());
        if (loyaltyOrderHistoryEntities != null && loyaltyOrderHistoryEntities.size() > 0) {
            // save loyalty point history
            Integer loyaltyPoint = 0;
            Boolean hasNewGetPoint = Boolean.FALSE;
            for (LoyaltyOrderHistoryEntity loyaltyOrderHistoryEntity : loyaltyOrderHistoryEntities) {
                LoyaltyOutletEventEntity loyaltyOutletEventEntity = loyaltyOutletEventRepository.findById(loyaltyOrderHistoryEntity.getLoyaltyOutletEventId()).get();
                LoyaltyMasterEventEntity loyaltyMasterEventEntity = loyaltyMasterEventRepository.findById(loyaltyOutletEventEntity.getLoyaltyMasterEventId()).get();
                // exclude event used point to pay
                if (loyaltyMasterEventEntity.getEventType() != null && !(loyaltyMasterEventEntity.getEventType().equals(CoreConstants.MASTER_EVENT_TYPE_PAID_BY_POINT))) {
                    // in case first order event
                    Integer totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(orderOutletEntity.getCustomerId(), loyaltyOutletEventEntity.getLoyaltyOutletEventId());
                    if (loyaltyOutletEventEntity.getMaxPlay() == null || totalPlay < loyaltyOutletEventEntity.getMaxPlay() && loyaltyMemberEntity != null) {
                        Integer point = loyaltyOrderHistoryEntity.getPoint();
                        //loyaltyMemberEntity = loyaltyPointHistoryRepository.saveLoyaltyHistory(loyaltyMemberEntity, loyaltyOutletEventEntity, loyaltyPointHistoryLocalBean, outletLocalBean, point);

                        //save loyalty point history
                        this.saveLoyaltyHistory(loyaltyMemberEntity, loyaltyOutletEventEntity, point);
                    }
                }
                loyaltyOrderHistoryRepository.delete(loyaltyOrderHistoryEntity);
            }
            // update loyalty member loyalty point
            if (loyaltyMemberEntity != null) {
                loyaltyMemberEntity.setPoint((loyaltyMemberEntity.getPoint() != null ? loyaltyMemberEntity.getPoint() : 0) + loyaltyPoint);
            }
        }
        if (orderOutletEntity.getDebt() != null && orderOutletEntity.getDebt() > 0 && loyaltyMemberEntity != null) {
            // 1. Set total Debt
            Double debtValue = orderOutletEntity.getDebt();
            Double totalDebt = loyaltyMemberEntity.getTotalDebt();
            if (totalDebt == null) {
                totalDebt = 0d;
            }
            totalDebt += debtValue;
            loyaltyMemberEntity.setTotalDebt(totalDebt);

            // 2. Update debt
            Integer creditTerms = loyaltyMemberEntity.getCreditTerms() != null ? loyaltyMemberEntity.getCreditTerms() : 7;
            Timestamp paymentDueDate = loyaltyMemberEntity.getPaymentDueDate() != null ? loyaltyMemberEntity.getPaymentDueDate() : new Timestamp(System.currentTimeMillis());
            Double creditLimit = loyaltyMemberEntity.getCreditLimit() != null ? loyaltyMemberEntity.getCreditLimit() : 0D;
            Double balance = creditLimit - totalDebt;
            Double paymentDueAmount = totalDebt;
            // 3. Update Payment Due Date
            Timestamp now = new Timestamp(System.currentTimeMillis());
            CustomerDebtHistoryEntity historyEntity = new CustomerDebtHistoryEntity();
            if (loyaltyMemberEntity.getPaymentDueDate() == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(paymentDueDate);
                calendar.add(calendar.DATE, creditTerms);
                paymentDueDate = new Timestamp(calendar.getTimeInMillis());
            }
            // new Payment Due Date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(calendar.DATE, creditTerms);
            Timestamp paymentDueDateNewOrder = new Timestamp(calendar.getTimeInMillis());

            historyEntity.setOrderOutlet(orderOutletEntity);
            historyEntity.setLoyaltyMember(loyaltyMemberEntity);
            historyEntity.setAmount(debtValue);
            historyEntity.setType(CoreConstants.DEBT_TYPE.DEBT_CREDIT.getType());
            historyEntity.setCreatedDate(now);
            historyEntity.setCreditBalance(balance);
            historyEntity.setPaymentDueAmount(paymentDueAmount);
            historyEntity.setPaymentDueDate(paymentDueDateNewOrder);
            UserEntity loginUser = new UserEntity();
            if (orderOutletEntity.getReferedBy() != null && orderOutletEntity.getReferedBy().getUserId() != null) {
                loginUser.setUserId(orderOutletEntity.getReferedBy().getUserId());
            }
            historyEntity.setCreatedBy(loginUser);
            loyaltyMemberEntity.setPaymentDueDate(paymentDueDate);
            customerDebHistoryRepository.save(historyEntity);

            loyaltyMemberEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            loyaltyMemberRepository.save(loyaltyMemberEntity);
        }

    }

    public void saveLoyaltyOrderHistory(LoyaltyOutletEventEntity outletEvent, String orderOutletCode, Integer point, Long customerId) throws DuplicateKeyException {
        LoyaltyOrderHistoryEntity loyaltyOrderHistoryEntity = new LoyaltyOrderHistoryEntity();
        loyaltyOrderHistoryEntity.setLoyaltyOutletEventId(outletEvent.getLoyaltyOutletEventId());
        loyaltyOrderHistoryEntity.setOrderOutletCode(orderOutletCode);
        loyaltyOrderHistoryEntity.setPoint(point);
        loyaltyOrderHistoryEntity.setCustomerId(customerId);
        loyaltyOrderHistoryRepository.save(loyaltyOrderHistoryEntity);
    }

    private void saveLoyaltyPointHistory(LoyaltyMemberEntity loyaltyMemberEntity, LoyaltyOutletEventEntity loyaltyOutletEventEntity, int point) {
        LoyaltyPointHistoryEntity history = new LoyaltyPointHistoryEntity();
        history.setOutletid(loyaltyMemberEntity.getOutlet().getOutletId());
        history.setLoyaltyOutletEventId(loyaltyOutletEventEntity.getLoyaltyOutletEventId());
        history.setPoint(point);
        history.setCompletionDate(new Timestamp(System.currentTimeMillis()));
        history.setCustomerId(loyaltyMemberEntity.getCustomerId());
        loyaltyPointHistoryRepository.save(history);
    }

    public Integer calculateLoyaltyPoint(OrderOutletEntity orderOutletEntity, Long outletId, Long customerId, LoyaltyMemberEntity loyaltyMemberEntity) {

        Double price = orderOutletEntity.getTotalPrice() - orderOutletEntity.getDeliveryPrice();
        String orderOutletCode = orderOutletEntity.getCode();
        Integer loyaltyPoint = 0;
        Integer totalPlay;
        Integer totalPlayOrder;

        //1. from order amount
        List<LoyaltyEventOrderAmountEntity> lstLoyaltyEventOrderAmount = loyaltyEventOrderAmountRepository.findByOrderAmount(orderOutletEntity.getOutlet().getOutletId(), price, new Timestamp(System.currentTimeMillis()));

        LoyaltyEventOrderAmountEntity eventOrderAmount = LoyaltyOutletEventBeanUtils.selectOneEventOrderAmount(lstLoyaltyEventOrderAmount);
        if (eventOrderAmount != null && eventOrderAmount.getLoyaltyEventOrderAmountId() != null) {
            Integer totalPlayInOrder = 0;
            totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, eventOrderAmount.getLoyaltyOutletEvent().getLoyaltyOutletEventId());
            totalPlayOrder = loyaltyOrderHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, eventOrderAmount.getLoyaltyOutletEvent().getLoyaltyOutletEventId());
            totalPlay += (totalPlayOrder + 1); // including this order

            if (eventOrderAmount.getLoyaltyOutletEvent().getMaxPlay() == null || totalPlay <= eventOrderAmount.getLoyaltyOutletEvent().getMaxPlay()) {
                if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(eventOrderAmount.getLoyaltyOutletEvent(), loyaltyMemberEntity)) {

                    totalPlayInOrder = eventOrderAmount.getLoyaltyOutletEvent().getMaxPlayInOrder();
                    Integer point = eventOrderAmount.getLoyaltyOutletEvent().getPoint();

                    // increase by value
                    Double minAmount = eventOrderAmount.getOrderAmount();
                    Double p = (price / minAmount);
                    if (totalPlayInOrder != null) {
                        if (p.intValue() > totalPlayInOrder) {
                            p = Double.valueOf(totalPlayInOrder);
                        }
                    }
                    point = p.intValue() * point;

                    loyaltyPoint += point;

                    this.saveLoyaltyOrderHistory(eventOrderAmount.getLoyaltyOutletEvent(), orderOutletCode, point, customerId);
                }
            }
        }

        //2.from order product
        List<Long> productOutletSkuIds = new ArrayList<>();
        for (ProductOrderItemEntity entity : orderOutletEntity.getProductOrderItemEntities()) {
            if (entity != null && entity.getProductOutletSkuId() != null)
                productOutletSkuIds.add(entity.getProductOutletSkuId());
        }
        List<LoyaltyOutletEventEntity> eventOrderProducts = this.getEventByProductOutletSkuIds(productOutletSkuIds);

        LoyaltyOutletEventEntity loyaltyOutletEventEntity = LoyaltyOutletEventBeanUtils.selectOneEventOrderProduct(eventOrderProducts);

        if (loyaltyOutletEventEntity != null && loyaltyOutletEventEntity.getLoyaltyOutletEventId() != null) {

            loyaltyOutletEventEntity = loyaltyOutletEventRepository.getOne(loyaltyOutletEventEntity.getLoyaltyOutletEventId());

            Integer totalPlayInOrder = 0;
            totalPlay = loyaltyPointHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            Integer totalPlayOrderHistory = loyaltyOrderHistoryRepository.countByCustomerIdAndLoyaltyOutletEventId(customerId, loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            totalPlay += (totalPlayOrderHistory + 1); // including this order

            if (loyaltyOutletEventEntity.getMaxPlay() == null || (totalPlay <= loyaltyOutletEventEntity.getMaxPlay() && totalPlayInOrder <= loyaltyOutletEventEntity.getMaxPlayInOrder())) {
                if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(loyaltyOutletEventEntity, loyaltyMemberEntity)) {

                    Map<Long, Integer> mapItems = new HashMap<>();
                    for (ProductOrderItemEntity orderItemEntity : orderOutletEntity.getProductOrderItemEntities()) {
                        if (orderItemEntity != null && orderItemEntity.getProductOutletSkuId() != null) {
                            mapItems.put(orderItemEntity.getProductOutletSkuId(), orderItemEntity.getQuantity());
                        }
                    }

                    Map<Long, Integer> mapQuantity = this.getQuantitySkuApprovedOnLoyalty(loyaltyOutletEventEntity.getLoyaltyOutletEventId(), mapItems);

                    Set<Long> skuIds = mapQuantity.keySet();
                    for (Long skuId : skuIds) {
                        totalPlayInOrder += mapQuantity.get(skuId);  // total = each quantity of product in cart item + number product in program
                    }

                    if (loyaltyOutletEventEntity.getMaxPlayInOrder() != null && totalPlayInOrder > loyaltyOutletEventEntity.getMaxPlayInOrder()) {
                        totalPlayInOrder = loyaltyOutletEventEntity.getMaxPlayInOrder();
                    }

                    if (loyaltyOutletEventEntity.getMaxPlayInOrder() == null || totalPlayInOrder > 0) {
                        loyaltyPoint += (loyaltyOutletEventEntity.getPoint() * totalPlayInOrder);
                        this.saveLoyaltyOrderHistory(loyaltyOutletEventEntity, orderOutletCode, (loyaltyOutletEventEntity.getPoint() * totalPlayInOrder), customerId);
                    }
                }
            }
        }

        //3. from first order
        if (customerId != null) {
            List<LoyaltyEventFirstOrderEntity> eventsFirstOrder = loyaltyEventFirstOrderRepository.findByLoyaltyOutletEventOutletIdAndLoyaltyOutletEventStatus(outletId, new Timestamp(System.currentTimeMillis()));

            LoyaltyOutletEventEntity eventFirstOrder = LoyaltyOutletEventBeanUtils.selectOneEventFirstOrder(eventsFirstOrder);

            if (eventFirstOrder != null && eventFirstOrder.getLoyaltyOutletEventId() != null) {
                // count total order of customer
                Integer totalOrder = orderOutletRepository.countByCustomerId(customerId) == null ? 0 : orderOutletRepository.countByCustomerId(customerId);
                if (eventFirstOrder.getMaxPlay() == null || totalOrder <= eventFirstOrder.getMaxPlay()) { // old order + a new order => total Order
                    if (LoyaltyOutletEventBeanUtils.checkCustomerGroupApproveLoyalty(eventFirstOrder, loyaltyMemberEntity)) {
                        Integer point = eventFirstOrder.getPoint();
                        loyaltyPoint += point;

                        this.saveLoyaltyOrderHistory(eventFirstOrder, orderOutletCode, point, customerId);
                    }
                }
            }
        }

        return loyaltyPoint;
    }

    public Map<Long, Integer> getQuantitySkuApprovedOnLoyalty(Long loyaltyOutletEventId, Map<Long, Integer> cartItems) {
        Map<Long, Integer> result = new LinkedHashMap<>();

        List<Object> skuIdInEvent = loyaltyOutletEventRepository.findCorrespondingSkuJoinInEventOrder(loyaltyOutletEventId);

        for (Object skuId : skuIdInEvent) {
            for (Long item : cartItems.keySet()) {
                if (Long.valueOf(skuId.toString()).equals(item)) {
                    result.put(Long.valueOf(skuId.toString()), cartItems.get(item));
                    break;
                }
            }
        }
        return result;
    }

    public List<LoyaltyOutletEventEntity> getEventByProductOutletSkuIds(List<Long> productOutletSkuIds) {
        List<Object[]> results = loyaltyOutletEventRepository.getEventByProductOutletSkuIds(productOutletSkuIds, new Timestamp(System.currentTimeMillis()));
        if (results != null && results.size() > 0) {
            List<LoyaltyOutletEventEntity> loyaltyOutletEventEntities = new ArrayList<>();
            for (Object[] arr : results) {
                LoyaltyOutletEventEntity loyaltyOutletEventEntity = new LoyaltyOutletEventEntity();
                loyaltyOutletEventEntity.setLoyaltyOutletEventId(((BigInteger) arr[0]).longValue());
                loyaltyOutletEventEntity.setLoyaltyMasterEventId(((BigInteger) arr[1]).longValue());
                loyaltyOutletEventEntity.setOutletId(((BigInteger) arr[2]).longValue());
                loyaltyOutletEventEntity.setStatus(arr[3].toString());
                loyaltyOutletEventEntity.setPoint((Integer) arr[4]);
                loyaltyOutletEventEntity.setName(arr[5].toString());
                loyaltyOutletEventEntity.setMaxPlay(arr[6] != null ? (Integer) arr[6] : null);
                loyaltyOutletEventEntity.setStartDate(arr[8] != null ? (Timestamp) arr[8] : null);
                loyaltyOutletEventEntity.setEndDate(arr[9] != null ? (Timestamp) arr[9] : null);
                loyaltyOutletEventEntities.add(loyaltyOutletEventEntity);
            }
            return loyaltyOutletEventEntities;
        }
        return null;
    }

    public LoyaltyMemberEntity saveLoyaltyHistory(LoyaltyMemberEntity loyaltyMemberEntity, LoyaltyOutletEventEntity loyaltyOutletEventEntity, Integer point) throws DuplicateKeyException {
        this.saveLoyaltyPointHistory(loyaltyMemberEntity, loyaltyOutletEventEntity, point);
        Integer pointLM = loyaltyMemberEntity.getPoint() + point;
        loyaltyMemberEntity.setPoint(pointLM);
        Integer monthExpired = 0;
        if (loyaltyMemberEntity.getOutlet().getLoyaltyPointExpiredMonth() == null) {
            OutletEntity outletEntity = outletRepository.findById(loyaltyMemberEntity.getOutlet().getOutletId()).get();
            monthExpired = outletEntity.getLoyaltyPointExpiredMonth();
        } else {
            monthExpired = loyaltyMemberEntity.getOutlet().getLoyaltyPointExpiredMonth();
        }
        loyaltyMemberEntity.setPointExpiredDate(CommonBeanUtils.calculatorExpiredFromNowWithMonthInput(monthExpired));
        return loyaltyMemberEntity;
    }

    public void saveHistoryUsedPoint(Long outletId, Long customerId, Integer usedPoint, String orderOutletCode) {
        try {
            OutletEntity outletEntity = outletRepository.getOne(outletId);
            CustomerEntity customerEntity = customerRepository.getOne(customerId);

            String eventType = CoreConstants.MASTER_EVENT_TYPE_PAID_BY_POINT;
            String title = CoreConstants.MASTER_EVENT_TITLE_PAID_BY_POINT;

            List<LoyaltyOutletEventEntity> lstLoyaltyOutletEventEntity = loyaltyOutletEventRepository.findByOutletIdAndMasterEventAndTitle(outletId, eventType, title);
            LoyaltyOutletEventEntity loyaltyOutletEventEntity = lstLoyaltyOutletEventEntity.size() > 0 && lstLoyaltyOutletEventEntity != null ? lstLoyaltyOutletEventEntity.get(0) : null;

            // create new if not exists
            if (loyaltyOutletEventEntity == null || loyaltyOutletEventEntity.getLoyaltyOutletEventId() == null) {
                // find loyaltyMasterEvent - create new if not exists
                List<LoyaltyMasterEventEntity> lstLoyaltyMasterEventEntity = loyaltyMasterEventRepository.findByEventTypeAndTitle(eventType, title);
                LoyaltyMasterEventEntity loyaltyMasterEventEntity = new LoyaltyMasterEventEntity();
                if (lstLoyaltyMasterEventEntity != null && lstLoyaltyMasterEventEntity.size() > 0) {
                    loyaltyMasterEventEntity = lstLoyaltyMasterEventEntity.get(0);
                }
                if (loyaltyMasterEventEntity == null || loyaltyMasterEventEntity.getLoyaltyMasterEventId() == null) {
                    // create new
                    loyaltyMasterEventEntity = new LoyaltyMasterEventEntity();
                    loyaltyMasterEventEntity.setTitle(title);
                    loyaltyMasterEventEntity.setEventType(eventType);
                    loyaltyMasterEventEntity.setCreatedBy(outletId);
                    loyaltyMasterEventEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    loyaltyMasterEventEntity.setStatus(CoreConstants.ACTIVE);
                    loyaltyMasterEventEntity.setDescription(CoreConstants.ACTIVE);
                    loyaltyMasterEventEntity.setImage(CoreConstants.DEFAULT_IMAGE);

                    loyaltyMasterEventEntity = loyaltyMasterEventRepository.save(loyaltyMasterEventEntity);
                } else if (CoreConstants.INACTIVE.equals(loyaltyMasterEventEntity.getStatus())) {
                    loyaltyMasterEventEntity.setStatus(CoreConstants.ACTIVE);
                    loyaltyMasterEventEntity = loyaltyMasterEventRepository.save(loyaltyMasterEventEntity);
                }

                // find LoyaltyOutletEvent with event "pay by point" - create new if not exists
                List<LoyaltyOutletEventEntity> lstLoyaltyPayByPoint = loyaltyOutletEventRepository.
                        findByOutletIdAndLoyaltyMasterEventId(outletId, loyaltyMasterEventEntity.getLoyaltyMasterEventId());
                if (lstLoyaltyPayByPoint != null && lstLoyaltyPayByPoint.size() > 0) {
                    loyaltyOutletEventEntity = lstLoyaltyPayByPoint.get(0);
                }

                if (loyaltyOutletEventEntity == null || loyaltyOutletEventEntity.getLoyaltyOutletEventId() == null) {
                    // create new
                    loyaltyOutletEventEntity = new LoyaltyOutletEventEntity();
                    loyaltyOutletEventEntity.setLoyaltyMasterEventId(loyaltyMasterEventEntity.getLoyaltyMasterEventId());
                    loyaltyOutletEventEntity.setStatus(CoreConstants.ACTIVE);
                    loyaltyOutletEventEntity.setOutletId(outletEntity.getOutletId());
                    loyaltyOutletEventEntity.setName(CoreConstants.OUTLET_EVENT_PAID_BY_POINT_NAME);
                    loyaltyOutletEventEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    loyaltyOutletEventEntity = loyaltyOutletEventRepository.save(loyaltyOutletEventEntity);
                }
            } else if (CoreConstants.INACTIVE.equals(loyaltyOutletEventEntity.getStatus())) {
                // update status
                loyaltyOutletEventEntity.setStatus(CoreConstants.ACTIVE);
                loyaltyOutletEventEntity = loyaltyOutletEventRepository.save(loyaltyOutletEventEntity);
            }

            // save loyalty order history
            LoyaltyOrderHistoryEntity orderHistoryEntity = new LoyaltyOrderHistoryEntity();
            orderHistoryEntity.setCustomerId(customerEntity.getCustomerId());
            orderHistoryEntity.setLoyaltyOutletEventId(loyaltyOutletEventEntity.getLoyaltyOutletEventId());
            orderHistoryEntity.setOrderOutletCode(orderOutletCode);
            orderHistoryEntity.setPoint(-usedPoint);
            loyaltyOrderHistoryRepository.save(orderHistoryEntity);

            // save loyalty point history
            LoyaltyPointHistoryEntity history = new LoyaltyPointHistoryEntity();
            history.setOutletid(outletEntity.getOutletId());
            history.setLoyaltyOutletEventId(loyaltyOutletEventEntity.getLoyaltyOutletEventId());

            // point will be negative
            history.setPoint(-usedPoint);
            history.setCompletionDate(new Timestamp(System.currentTimeMillis()));
            history.setCustomerId(customerEntity.getCustomerId());
            loyaltyPointHistoryRepository.save(history);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reApplyUsedPoint(OrderOutletEntity orderOutletEntity) {
        List<LoyaltyMemberEntity> loyaltyMemberEntities = loyaltyMemberRepository.findByCustomerIdAndOutletOutletIdAndActive(orderOutletEntity.getCustomerId(), orderOutletEntity.getOutlet().getOutletId(), 1);

        //check customer has used loyalty point for pay invoice
        if (orderOutletEntity.getPointRedeemed() != null && orderOutletEntity.getAmountRedeemed() != null) {
            LoyaltyMemberEntity loyaltyMemberEntity = loyaltyMemberEntities.get(0);
            Double amountRedeemed = 0d;
            if (loyaltyMemberEntity != null && loyaltyMemberEntity.getLoyaltyMemberId() != null) {

                //udpate date for loyalty member
                loyaltyMemberEntity.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                OutletEntity outletEntity = outletRepository.findById(orderOutletEntity.getOutlet().getOutletId()).get();
                Integer monthExpired = outletEntity.getLoyaltyPointExpiredMonth();
                loyaltyMemberEntity.setPointExpiredDate(CommonBeanUtils.calculatorExpiredFromNowWithMonthInput(monthExpired != null ? monthExpired : 0));
                loyaltyMemberRepository.save(loyaltyMemberEntity);
            }

            //update price for order ( total price - price exchange by point)
            Double totalPrice = orderOutletEntity.getTotalPrice();
            orderOutletEntity.setTotalPrice(totalPrice - orderOutletEntity.getAmountRedeemed() > 0 ? totalPrice - orderOutletEntity.getAmountRedeemed() : 0);
        }
    }
}
