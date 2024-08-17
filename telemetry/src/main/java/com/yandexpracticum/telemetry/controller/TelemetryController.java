package com.yandexpracticum.telemetry.controller;

import com.yandexpracticum.telemetry.dto.TelemetryDto;
import com.yandexpracticum.telemetry.entity.TelemetryType;
import com.yandexpracticum.telemetry.service.TelemetryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
@Tag(name = "Telemetry API", description = "API для работы с телеметрией")
public class TelemetryController {

    private final TelemetryService telemetryService;

    @Operation(summary = "Получить последние данные телеметрии",
            description = "Возвращает последние доступные данные телеметрии для указанного устройства.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение данных"),
            @ApiResponse(responseCode = "204", description = "Данные телеметрии не найдены")
    })
    @GetMapping("{deviceId}/latest")
    public ResponseEntity<Map<TelemetryType, TelemetryDto>> getLatestTelemetry(
            @PathVariable("deviceId") @Parameter(description = "Идентификатор устройства") Long deviceId) {
        Map<TelemetryType, TelemetryDto> telemetryData = telemetryService.getLatestTelemetry(deviceId);

        if (telemetryData.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(telemetryData);
        }
    }

    @Operation(summary = "Получить историю данных телеметрии",
            description = "Возвращает историю данных телеметрии за указанный период для указанного устройства.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение данных"),
            @ApiResponse(responseCode = "204", description = "История данных телеметрии не найдена")
    })
    @GetMapping("{deviceId}/history")
    public ResponseEntity<Map<TelemetryType, List<TelemetryDto>>> getTelemetryHistory(
            @PathVariable("deviceId") @Parameter(description = "Идентификатор устройства") Long deviceId,

            @Parameter(description = "Начальная дата для фильтрации истории")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "startDate", defaultValue = "1970-01-01T00:00:00") LocalDateTime startDate,

            @Parameter(description = "Конечная дата для фильтрации истории")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(value = "endDate", defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime endDate) {
        return ResponseEntity.ok(telemetryService.getTelemetryHistory(deviceId, startDate, endDate));
    }
}
