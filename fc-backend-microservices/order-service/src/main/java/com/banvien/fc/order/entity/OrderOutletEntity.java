package com.banvien.fc.order.entity;

import com.banvien.fc.common.service.NumberUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "orderoutlet")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class OrderOutletEntity {

    private Long orderOutletID;
    private Long customerId;
    private OutletEntity outlet;
    private Double totalOriginalPrice;                  // Gia truoc khi app dung giam gia & khuyen mai: gia goc
    private Double totalStoreDiscountPrice;            // Gia giam tai cua hang: gia goc - gia sale
    private Double totalPromotionDiscountPrice;        // Giam gia theo khuyen mai
    private Double deliveryPrice;                      // Gia van chuyen
    private Double totalPrice;                         // Tong tien phai tra
    private Double saving;                             // Tiet kiem bao nhieu%
    private Integer totalItem;
    private String status;
    private String code;
    private String note;
    private Integer loyaltyPoint;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private OrderInformationEntity orderInformation;
    private Double debt;
    private UserEntity referedBy;
    private String saleChanel;
    private Timestamp shipDate;
    private Integer pointRedeemed; // point used to pay the order
    private Double amountRedeemed; // amount used point to redeem
    private Double totalWeight;
    private String grabTxId;
    private String grabGroupTxId;
    private String token;
    private String fromsource;
    private OutletSaleChannelEntity outletSaleChannel;

    private EasyOrderEntity easyOrder;
    private List<ProductOrderItemEntity> productOrderItemEntities;
    private Timestamp receiveStartTime;
    private Timestamp receiveEndTime;

    @Basic
    @Column(name = "receivestarttime")
    public Timestamp getReceiveStartTime() {
        return receiveStartTime;
    }

    public void setReceiveStartTime(Timestamp receiveStartTime) {
        this.receiveStartTime = receiveStartTime;
    }

    @Basic
    @Column(name = "receiveendtime")
    public Timestamp getReceiveEndTime() {
        return receiveEndTime;
    }

    public void setReceiveEndTime(Timestamp receiveEndTime) {
        this.receiveEndTime = receiveEndTime;
    }

    public OrderOutletEntity() {
    }


    public OrderOutletEntity(Long orderOutletID) {
        this.orderOutletID = orderOutletID;
    }

    @Column(name = "orderoutletid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getOrderOutletID() {
        return orderOutletID;
    }

    public void setOrderOutletID(Long orderOutletID) {
        this.orderOutletID = orderOutletID;
    }

    @Column(name = "customerid")
    @Basic
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @ManyToOne()
    @JoinColumn(name = "outletid", referencedColumnName = "outletid")
    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    @Column(name = "totalprice")
    @Basic
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = NumberUtils.calculate(totalPrice);
    }

    @Column(name = "totalitem")
    @Basic
    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    @Column(name = "status")
    @Basic
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "createddate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "note")
    @Basic
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "loyaltypoint")
    @Basic
    public Integer getLoyaltyPoint() {
        return loyaltyPoint;
    }

    public void setLoyaltyPoint(Integer loyaltyPoint) {
        this.loyaltyPoint = loyaltyPoint;
    }

    @Column(name = "saving")
    @Basic
    public Double getSaving() {
        return saving;
    }

    public void setSaving(Double saving) {
        this.saving = saving;
    }

    @Column(name = "deliveryprice")
    @Basic
    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    @Column(name = "totaloriginalprice")
    @Basic
    public Double getTotalOriginalPrice() {
        return totalOriginalPrice;
    }

    public void setTotalOriginalPrice(Double totalOriginalPrice) {
        this.totalOriginalPrice = totalOriginalPrice;
    }

    @Column(name = "totalstorediscountprice")
    @Basic
    public Double getTotalStoreDiscountPrice() {
        return totalStoreDiscountPrice;
    }

    public void setTotalStoreDiscountPrice(Double totalStoreDiscountPrice) {
        this.totalStoreDiscountPrice = totalStoreDiscountPrice;
    }

    @Column(name = "totalpromotiondiscountprice")
    @Basic
    public Double getTotalPromotionDiscountPrice() {
        return totalPromotionDiscountPrice;
    }

    public void setTotalPromotionDiscountPrice(Double totalPromotionDiscountPrice) {
        this.totalPromotionDiscountPrice = totalPromotionDiscountPrice;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "orderinformationid", referencedColumnName = "orderinformationid")
    public OrderInformationEntity getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(OrderInformationEntity orderInformation) {
        this.orderInformation = orderInformation;
    }

    @Column(name = "debt")
    @Basic
    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    @ManyToOne
    @JoinColumn(name = "referedby", referencedColumnName = "userid")
    public UserEntity getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(UserEntity referedBy) {
        this.referedBy = referedBy;
    }

    @Column(name = "salechanel")
    @Basic
    public String getSaleChanel() {
        return saleChanel;
    }

    public void setSaleChanel(String saleChanel) {
        this.saleChanel = saleChanel;
    }

    @Column(name = "shipdate")
    @Basic
    public Timestamp getShipDate() {
        return shipDate;
    }

    public void setShipDate(Timestamp shipDate) {
        this.shipDate = shipDate;
    }

    @Column(name = "pointredeemed")
    @Basic
    public Integer getPointRedeemed() {
        return pointRedeemed;
    }

    public void setPointRedeemed(Integer pointRedeemed) {
        this.pointRedeemed = pointRedeemed;
    }

    @Column(name = "modifieddate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name = "amountredeemed")
    @Basic
    public Double getAmountRedeemed() {
        return amountRedeemed;
    }

    public void setAmountRedeemed(Double amountRedeemed) {
        this.amountRedeemed = amountRedeemed;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    @ManyToOne
    @JoinColumn(name = "easyorderid", referencedColumnName = "easyorderid")
    public EasyOrderEntity getEasyOrder() {
        return easyOrder;
    }

    public void setEasyOrder(EasyOrderEntity easyOrder) {
        this.easyOrder = easyOrder;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderOutletId")
    public List<ProductOrderItemEntity> getProductOrderItemEntities() {
        return productOrderItemEntities;
    }

    public void setProductOrderItemEntities(List<ProductOrderItemEntity> productOrderItemEntities) {
        this.productOrderItemEntities = productOrderItemEntities;
    }

    @Column(name = "grabTxId")
    @Basic
    public String getGrabTxId() {
        return grabTxId;
    }

    public void setGrabTxId(String grabTxId) {
        this.grabTxId = grabTxId;
    }

    @Column(name = "grabGroupTxId")
    @Basic
    public String getGrabGroupTxId() {
        return grabGroupTxId;
    }

    public void setGrabGroupTxId(String grabGroupTxId) {
        this.grabGroupTxId = grabGroupTxId;
    }

    @Column(name = "token")
    @Basic
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "fromsource")
    @Basic
    public String getFromsource() {
        return fromsource;
    }

    public void setFromsource(String fromsource) {
        this.fromsource = fromsource;
    }

    @ManyToOne
    @JoinColumn(name = "outletsalechannelid", referencedColumnName = "outletsalechannelid")
    public OutletSaleChannelEntity getOutletSaleChannel() {
        return outletSaleChannel;
    }

    public void setOutletSaleChannel(OutletSaleChannelEntity outletSaleChannel) {
        this.outletSaleChannel = outletSaleChannel;
    }
}
