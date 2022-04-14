package com.banvien.fc.order.entity;

import javax.persistence.*;

@Entity
@Table(name = "customerrewardorderitem")
public class CustomerRewardOrderItemEntity {
    private Long customerRewardOrderItemId;
    private OrderOutletEntity orderOutlet;
    private CustomerRewardEntity customerReward;

    @Id
    @Column(name = "customerrewardorderitemid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getCustomerRewardOrderItemId() {
        return customerRewardOrderItemId;
    }

    public void setCustomerRewardOrderItemId(Long customerRewardOrderItemId) {
        this.customerRewardOrderItemId = customerRewardOrderItemId;
    }

    @ManyToOne
    @JoinColumn(name = "orderOutletId", referencedColumnName = "orderOutletId")
    public OrderOutletEntity getOrderOutlet() {
        return orderOutlet;
    }

    public void setOrderOutlet(OrderOutletEntity orderOutlet) {
        this.orderOutlet = orderOutlet;
    }


    @ManyToOne
    @JoinColumn(name = "customerrewardid", referencedColumnName = "customerrewardid")
    public CustomerRewardEntity getCustomerReward() {
        return customerReward;
    }

    public void setCustomerReward(CustomerRewardEntity customerReward) {
        this.customerReward = customerReward;
    }
}
