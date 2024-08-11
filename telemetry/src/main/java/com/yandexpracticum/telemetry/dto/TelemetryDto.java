package com.yandexpracticum.telemetry.dto;

import com.yandexpracticum.telemetry.entity.TelemetryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TelemetryDto {
    private Long deviceId;
    private LocalDateTime timestamp;
    private TelemetryType telemetryType;
    private Double value;
}
