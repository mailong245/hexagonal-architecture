package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.ext;

import com.mailong245.hexagonalarchitecture.app.config.RestTemplateConfig;
import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.TransactionRepository;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.TransactionEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.TransactionEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionRepositoryExtAdapter implements TransactionRepository {

    private final RestTemplate extTransactionRestTemplate;
    private final ModelMapper modelMapper;

    @Override
    public List<Transaction> getTransactionList() {

        ResponseEntity<List<Transaction>> responseTest = extTransactionRestTemplate.exchange(
                RestTemplateConfig.GET_TRANSACTION_URL,
                HttpMethod.GET,
                new HttpEntity<>(""),
                new ParameterizedTypeReference<>() {
                });

        return responseTest.getBody();
    }

    @Override
    public Transaction getTransactionDetailById(String id) {
//        return transactionEntityRepository.findById(Long.valueOf(id))
//                .map(transactionEntity -> modelMapper.map(transactionEntity, Transaction.class))
//                .orElse(null);
        return Transaction.builder().build();
    }

    @Transactional(
            isolation = Isolation.READ_UNCOMMITTED
    )
    @Override
    public Transaction createTransaction(Transaction transaction) {
//        TransactionEntity transactionEntity = modelMapper.map(transaction, TransactionEntity.class);
//        Transaction result = modelMapper.map(transactionEntityRepository.save(transactionEntity), Transaction.class);
//        log.info("Save successful");
//        return result;

        return Transaction.builder().build();
    }

}
