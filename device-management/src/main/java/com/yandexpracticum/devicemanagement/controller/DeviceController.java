package com.yandexpracticum.devicemanagement.controller;

import com.yandexpracticum.devicemanagement.dto.DeviceDto;
import com.yandexpracticum.devicemanagement.entity.Device;
import com.yandexpracticum.devicemanagement.model.HeaterState;
import com.yandexpracticum.devicemanagement.model.LightState;
import com.yandexpracticum.devicemanagement.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Tag(name = "Device API", description = "API для управления устройствами")

public class DeviceController {

    private final DeviceService deviceService;

    @Operation(summary = "Получить информацию об устройстве",
            description = "Возвращает информацию об устройстве по указанному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Устройство найдено",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "404", description = "Устройство не найдено")
    })
    @GetMapping("/{device_id}")
    public ResponseEntity<Device> getDevice(@PathVariable("device_id") Long deviceId) {
        return deviceService.getDeviceById(deviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Обновить состояние устройства",
            description = "Обновляет состояние устройства на основе переданных данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Состояние устройства успешно обновлено",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления")
    })
    @PutMapping("/{device_id}/state")
    public ResponseEntity<Device> updateDeviceState(
            @PathVariable("device_id") Long deviceId,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные состояния устройства",
                    content = @Content(
                            schema = @Schema(oneOf = {HeaterState.class, LightState.class}),
                            examples = {
                                    @ExampleObject(
                                            name = "HeaterStateExample",
                                            value = "{\"on\": true, \"temperature\": 24.0}"
                                    ),
                                    @ExampleObject(
                                            name = "LightStateExample",
                                            value = "{\"on\": false, \"brightness\": 80}"
                                    )
                            }
                    )
            )
            Map<String, Object> stateData) {
        return ResponseEntity.ok(deviceService.updateDeviceState(deviceId, stateData));
    }

    @Operation(summary = "Создать новое устройство",
            description = "Создает новое устройство на основе переданных данных.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Устройство успешно создано",
                    content = @Content(schema = @Schema(implementation = Device.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные устройства")
    })

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceDto deviceDto) {
        Device createdDevice = deviceService.createDevice(deviceDto);
        return ResponseEntity.status(201).body(createdDevice);
    }

    @Operation(summary = "Удалить устройство",
            description = "Удаляет устройство по указанному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Устройство успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Устройство не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }


}
