package com.yandexpracticum.devicemanagement.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        requireTypeIdForSubtypes = OptBoolean.FALSE
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HeaterState.class, name = "HeaterState"),
        @JsonSubTypes.Type(value = LightState.class, name = "LightState")
})
@Getter
@Setter
public abstract class DeviceState {
    private boolean isOn;
}

