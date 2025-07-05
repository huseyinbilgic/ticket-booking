package com.algofusion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algofusion.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
