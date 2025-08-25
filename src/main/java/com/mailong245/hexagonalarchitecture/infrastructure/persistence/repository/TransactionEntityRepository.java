package com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository;

import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {
}