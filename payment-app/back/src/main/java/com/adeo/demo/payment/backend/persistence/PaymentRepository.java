package com.adeo.demo.payment.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    /**
     * Find all payments associated with a specific order ID
     * 
     * @param orderId The ID of the order
     * @return List of payments for the given order
     */
    List<Payment> findByOrderId(Long orderId);
}
