package com.mailong245.hexagonalarchitecture.features.transaction.web;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.features.transaction.app.TransactionService;
import com.mailong245.hexagonalarchitecture.features.transaction.web.request.CreateTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/v1/transaction")
    public ResponseEntity<List<Transaction>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @GetMapping("/v1/transaction/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") String id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping("/v1/transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody @Validated CreateTransactionRequest request) {
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }

}
