package com.banvien.fc.order.repository;

import com.banvien.fc.order.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity,Long> {

    @Query(value = "select * from shoppingcart s  " +
            "where s.customerid = :customerId  and (s.referenceby = :salesmanId) and " +
            "((:outletId = -1) or (s.productoutletskuid in (select p.productoutletskuid from productoutletsku p where " +
            "p.productoutletid  in (select p2.productoutletid from productoutlet p2 where p2.outletid = :outletId )))) order by s.shoppingcartid asc",nativeQuery = true)
    List<ShoppingCartEntity> findShoppingCartForSalesman (@Param("customerId")Long customerId,@Param("salesmanId") Long salesmanId,@Param("outletId")Long outletId);

    @Query(value = "select * from shoppingcart s  " +
            "where s.customerid = :customerId  and s.referenceby is null and " +
            "((:outletId = -1) or (s.productoutletskuid in (select p.productoutletskuid from productoutletsku p where " +
            "p.productoutletid  in (select p2.productoutletid from productoutlet p2 where p2.outletid = :outletId )))) order by s.shoppingcartid asc",nativeQuery = true)
    List<ShoppingCartEntity> findShoppingCartForCustomer (@Param("customerId")Long customerId,@Param("outletId")Long outletId);


    @Transactional
    @Modifying
    @Query(" UPDATE ShoppingCartEntity SET temporaryOrderCode = :temporaryOrderCode WHERE shoppingCartId = :shoppingCartId")
    void updateShopping(@Param("shoppingCartId") Long shoppingCartId, @Param("temporaryOrderCode") String temporaryOrderCode);

    @Query(value = "select distinct (p2.outletid) from shoppingcart s " +
            "join productoutletsku p on s.productoutletskuid = p.productoutletskuid " +
            "join productoutlet p2  on p.productoutletid = p2.productoutletid " +
            "where (s.customerid = :customerId) and s.referenceby is null ",nativeQuery = true)
    List<Long> getListOutletIdFromShoppingCart(@Param("customerId")Long customerId);

}
