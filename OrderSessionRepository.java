package com.example.demo.repository;

import com.example.demo.model.OrderSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderSessionRepository extends JpaRepository<OrderSession, Long> {
    Optional<OrderSession> findByTableNo(int tableNo);
}