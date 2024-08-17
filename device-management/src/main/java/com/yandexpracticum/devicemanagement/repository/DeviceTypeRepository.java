package com.yandexpracticum.devicemanagement.repository;

import com.yandexpracticum.devicemanagement.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    DeviceType findByName(DeviceType.TypeName name);
}
