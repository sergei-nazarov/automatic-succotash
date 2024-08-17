package com.yandexpracticum.telemetry.service;

import com.yandexpracticum.telemetry.dto.TelemetryDto;
import com.yandexpracticum.telemetry.entity.TelemetryData;
import com.yandexpracticum.telemetry.entity.TelemetryType;
import com.yandexpracticum.telemetry.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;

    @Transactional(readOnly = true)
    public Map<TelemetryType, TelemetryDto> getLatestTelemetry(Long deviceId) {
        return telemetryRepository.findLatestTelemetryByDeviceIdGroupedByType(deviceId)
                .collect(Collectors.toMap(
                        TelemetryData::getTelemetryType,
                        this::toDto
                ));
    }

    @Transactional(readOnly = true)
    public Map<TelemetryType, List<TelemetryDto>> getTelemetryHistory(Long deviceId, LocalDateTime startDate, LocalDateTime endDate) {
        return telemetryRepository.findAllByDeviceIdAndTimestampBetween(deviceId, startDate, endDate)
                .map(this::toDto)
                .collect(Collectors.groupingBy(TelemetryDto::getTelemetryType));
    }

    private TelemetryDto toDto(TelemetryData data) {
        return new TelemetryDto(
                data.getDeviceId(),
                data.getTimestamp(),
                data.getTelemetryType(),
                data.getVal());
    }
}
