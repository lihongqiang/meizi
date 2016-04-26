package com.meiziaccess.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by user-u1 on 2016/4/14.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
