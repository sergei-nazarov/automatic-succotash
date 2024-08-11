package com.yandexpracticum.devicemanagement.repository;

import com.yandexpracticum.devicemanagement.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
