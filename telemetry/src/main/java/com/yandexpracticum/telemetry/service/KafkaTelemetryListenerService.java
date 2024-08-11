package com.yandexpracticum.telemetry.service;

import com.yandexpracticum.telemetry.dto.TelemetryDto;
import com.yandexpracticum.telemetry.entity.TelemetryData;
import com.yandexpracticum.telemetry.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaTelemetryListenerService {

    private final TelemetryRepository telemetryRepository;

    @KafkaListener(topics = "${kafka.topic.telemetry_updates}")
    public void listen(TelemetryDto dto) {
        telemetryRepository.save(toEntity(dto));
    }

    private TelemetryData toEntity(TelemetryDto dto) {
        TelemetryData telemetryData = new TelemetryData();
        telemetryData.setTelemetryType(dto.getTelemetryType());
        telemetryData.setDeviceId(dto.getDeviceId());
        telemetryData.setTimestamp(dto.getTimestamp());
        telemetryData.setVal(dto.getValue());
        return telemetryData;
    }

}
