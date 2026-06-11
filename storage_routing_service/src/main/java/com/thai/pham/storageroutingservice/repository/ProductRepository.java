package com.thai.pham.storageroutingservice.repository;

import com.thai.pham.storageroutingservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {}