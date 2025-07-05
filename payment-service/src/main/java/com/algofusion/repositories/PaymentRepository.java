package com.algofusion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algofusion.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
