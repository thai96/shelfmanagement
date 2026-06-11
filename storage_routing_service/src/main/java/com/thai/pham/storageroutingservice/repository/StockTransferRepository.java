package com.thai.pham.storageroutingservice.repository;

import com.thai.pham.storageroutingservice.entity.StockTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockTransferRepository extends JpaRepository<StockTransfer, UUID> {}