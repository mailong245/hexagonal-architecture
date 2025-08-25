package com.mailong245.hexagonalarchitecture.domain.port.persistence;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> getTransactionList();
    Transaction getTransactionDetailById(String id);
    Transaction createTransaction(Transaction transaction);

}
