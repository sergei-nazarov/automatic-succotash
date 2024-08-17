package com.yandexpracticum.devicemanagement.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandexpracticum.devicemanagement.model.DeviceState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DeviceStateJsonConverter implements AttributeConverter<DeviceState, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DeviceState attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при конвертации JSON", e);
        }
    }

    @Override
    public DeviceState convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, DeviceState.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при конвертации JSON", e);
        }
    }
}
