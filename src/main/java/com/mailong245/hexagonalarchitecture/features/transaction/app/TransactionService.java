package com.mailong245.hexagonalarchitecture.features.transaction.app;

import com.mailong245.hexagonalarchitecture.domain.model.Transaction;
import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.domain.port.external.NotificationService;
import com.mailong245.hexagonalarchitecture.domain.port.external.PaymentService;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.TransactionRepository;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.UserRepository;
import com.mailong245.hexagonalarchitecture.features.transaction.web.request.CreateTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepositoryExtAdapter;
    private final NotificationService notificationService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    public List<Transaction> getAllTransaction() {
        return transactionRepositoryExtAdapter.getTransactionList();
    }

    public Transaction getTransactionById(String id) {
        return transactionRepositoryExtAdapter.getTransactionDetailById(id);
    }


    public Transaction createTransaction(CreateTransactionRequest request) {
        try {
            User user = userRepository.findById(request.userId()).orElse(null);

            Transaction transaction = Transaction.builder()
                    .user(user)
                    .amount(request.amount())
                    .build();

            Transaction result = transactionRepositoryExtAdapter.createTransaction(transaction);
            paymentService.charges(new BigDecimal(transaction.amount()));

            notificationService.sendNotification("Send notification to customer");
            return result;
        } catch (InterruptedException e) {
            log.error("Error occurred while creating transaction" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }

}
