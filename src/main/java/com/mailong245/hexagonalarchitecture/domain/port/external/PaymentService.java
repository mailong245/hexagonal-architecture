package com.mailong245.hexagonalarchitecture.domain.port.external;

import java.math.BigDecimal;

public interface PaymentService {
    void charges(BigDecimal amount) throws InterruptedException;
}
