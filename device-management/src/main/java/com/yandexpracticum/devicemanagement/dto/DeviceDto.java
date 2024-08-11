package com.yandexpracticum.devicemanagement.dto;

import com.yandexpracticum.devicemanagement.entity.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {
    private DeviceType.TypeName deviceType;
    private Long houseId;
    private String serialNumber;
}
