package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.jpa;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.TransactionRepository;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.TransactionEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.TransactionEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionRepositoryJpaAdapter implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Transaction> getTransactionList() {
        return transactionEntityRepository.findAll()
                .stream()
                .map(transactionEntity -> modelMapper.map(transactionEntity, Transaction.class))
                .toList();
    }

    @Override
    public Transaction getTransactionDetailById(String id) {
        return transactionEntityRepository.findById(Long.valueOf(id))
                .map(transactionEntity -> modelMapper.map(transactionEntity, Transaction.class))
                .orElse(null);
    }

    @Transactional(
            isolation = Isolation.READ_UNCOMMITTED
    )
    @Override
    public Transaction createTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = modelMapper.map(transaction, TransactionEntity.class);
        Transaction result = modelMapper.map(transactionEntityRepository.save(transactionEntity), Transaction.class);
        log.info("Save successful");
        return result;
    }

}
