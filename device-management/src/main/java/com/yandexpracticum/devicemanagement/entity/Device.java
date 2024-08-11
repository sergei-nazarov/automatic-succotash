package com.yandexpracticum.devicemanagement.entity;

import com.yandexpracticum.devicemanagement.model.DeviceState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DeviceType type;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = DeviceStateJsonConverter.class)
    private DeviceState state;

    private Long houseId;
    private String serialNumber;
}
