package com.mailong245.hexagonalarchitecture.infrastructure.external.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyByte;
import static org.mockito.Mockito.mockStatic;


@ExtendWith(MockitoExtension.class)
public class PaymentServiceAdapterTest {

    @InjectMocks
    private PaymentServiceAdapter paymentServiceAdapter;

    @Test
    public void testCharges() {
        assertDoesNotThrow(() -> paymentServiceAdapter.charges(new BigDecimal("1000000")));
    }

}
