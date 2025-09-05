package com.mailong245.hexagonalarchitecture.infrastructure.external.adapter;

import com.mailong245.hexagonalarchitecture.domain.port.external.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceAdapterTest {

    @InjectMocks
    private NotificationServiceAdapter notificationServiceAdapter;

    @Test
    public void testSendNotification() {
        assertDoesNotThrow(() -> notificationServiceAdapter.sendNotification("Test message"));
    }

}
