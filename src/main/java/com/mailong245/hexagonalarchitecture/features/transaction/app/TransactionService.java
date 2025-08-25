package com.mailong245.hexagonalarchitecture.features.transaction.app;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.domain.port.external.NotificationService;
import com.mailong245.hexagonalarchitecture.domain.port.external.PaymentService;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.TransactionRepository;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.UserRepository;
import com.mailong245.hexagonalarchitecture.features.transaction.web.CreateTransactionRequest;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    public List<Transaction> getAllTransaction() {
        return transactionRepository.getTransactionList();
    }

    public Transaction getTransactionById(String id) {
        return transactionRepository.getTransactionDetailById(id);
    }

    public Transaction createTransaction(CreateTransactionRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);

        Transaction transaction = Transaction.builder()
                .user(user)
                .amount(request.getAmount())
                .build();

        Transaction result = transactionRepository.createTransaction(transaction);
        paymentService.charges(new BigDecimal(transaction.getAmount()));
        notificationService.sendNotification("Send notification to customer");
        return result;
    }

}
