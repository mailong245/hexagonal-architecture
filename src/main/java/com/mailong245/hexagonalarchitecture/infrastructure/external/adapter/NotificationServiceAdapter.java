package com.mailong245.hexagonalarchitecture.infrastructure.external.adapter;

import com.mailong245.hexagonalarchitecture.domain.port.external.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationServiceAdapter implements NotificationService {

    @Async
    @Override
    public void sendNotification(Object message) {
        log.info("message: {}", message.toString());
        log.info("Send notification success");
    }

}
