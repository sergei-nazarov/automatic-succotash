package com.yandexpracticum.devicemanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandexpracticum.devicemanagement.entity.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${kafka.topics.device-update}")
    private String deviceUpdateTopicName;

    public void sendDeviceUpdate(Device device) {
        try {
            String deviceJson = objectMapper.writeValueAsString(device);
            var result = kafkaTemplate.send(deviceUpdateTopicName, device.getId().toString(), deviceJson);
            result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
