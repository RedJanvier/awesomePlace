package com.awesome.marketplace.orders.orders_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.awesome.marketplace.orders.orders_service.models.Order;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Integer> {
//    @Query("select * from orders or order by Or.created_at desc")
    public abstract List<Order> findAllByUserIdOrderByCreatedAtDesc(Integer userId);
    public abstract List<Order> findByIdAndUserId(Integer id, Integer userId);
}
