package com.mailong245.hexagonalarchitecture.infrastructure.external.adapter;

import com.mailong245.hexagonalarchitecture.domain.port.external.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PaymentServiceAdapter implements PaymentService {

    @Override
    public void charges(BigDecimal amount) throws InterruptedException {
        log.info("Start call payment service for charging amount {}", amount);
        Thread.sleep(5000);
        log.info("End call payment service for charging amount {}", amount);
    }
}
