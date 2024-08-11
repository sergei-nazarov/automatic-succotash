package com.yandexpracticum.devicemanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LightState extends DeviceState {
    private int brightness;
}
