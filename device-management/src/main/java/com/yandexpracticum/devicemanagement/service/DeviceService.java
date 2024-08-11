package com.yandexpracticum.devicemanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandexpracticum.devicemanagement.dto.DeviceDto;
import com.yandexpracticum.devicemanagement.entity.Device;
import com.yandexpracticum.devicemanagement.entity.DeviceType;
import com.yandexpracticum.devicemanagement.model.DeviceState;
import com.yandexpracticum.devicemanagement.model.HeaterState;
import com.yandexpracticum.devicemanagement.model.LightState;
import com.yandexpracticum.devicemanagement.repository.DeviceRepository;
import com.yandexpracticum.devicemanagement.repository.DeviceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public Device updateDeviceState(Long deviceId, Map<String, Object> stateData) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        DeviceState state;
        DeviceType.TypeName deviceTypeName = device.getType().getName();
        if (deviceTypeName.equals(DeviceType.TypeName.HEATER)) {
            state = objectMapper.convertValue(stateData, HeaterState.class);
        } else if (deviceTypeName.equals(DeviceType.TypeName.LIGHT)) {
            state = objectMapper.convertValue(stateData, LightState.class);
        } else {
            throw new IllegalArgumentException("Unknown device type");
        }
        kafkaProducerService.sendDeviceUpdate(device);
        device.setState(state);
        return deviceRepository.save(device);
    }

    public Device createDevice(DeviceDto deviceDto) {
        return deviceRepository.save(fromDto(deviceDto));
    }

    public void deleteDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Device not found"));

        deviceRepository.delete(device);
    }

    private Device fromDto(DeviceDto deviceDto) {
        Device device = new Device();
        device.setType(deviceTypeRepository.findByName(deviceDto.getDeviceType()));
        device.setHouseId(deviceDto.getHouseId());
        device.setSerialNumber(deviceDto.getSerialNumber());
        return device;
    }


}
