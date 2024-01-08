package com.iche.xpresspayapi.repository;

import com.iche.xpresspayapi.model.AirtimeVTUTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirtimeVTUTransactionRepository extends JpaRepository<AirtimeVTUTransaction, Long> {
}
