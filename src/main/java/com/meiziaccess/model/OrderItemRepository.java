package com.meiziaccess.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by user-u1 on 2016/5/6.
 */
//@Repository
//public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
//
//    @Transactional
//    @Modifying
//    @Query("update OrderItem item set item.url = :url where item.order_id = :orderId and item.sub_item_id = :itemId")
//    int updateUrlById(@Param("orderId") Integer orderId, @Param("itemId") Integer itemId, @Param("url") String url);
//
//    @Transactional
//    @Modifying
//    @Query("update OrderItem item set item.status = :status where item.order_id = :orderId and item.sub_item_id = :itemId")
//    int updateStatusById(@Param("orderId") Integer orderId, @Param("itemId") Integer itemId, @Param("status") Integer status);
//}
