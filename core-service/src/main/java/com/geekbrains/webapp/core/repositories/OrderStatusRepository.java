package com.geekbrains.webapp.core.repositories;

import com.geekbrains.webapp.core.model.Order;
import com.geekbrains.webapp.core.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>{
    Optional<OrderStatus> findByOrder(Order order);

}
