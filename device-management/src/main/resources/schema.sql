INSERT INTO device_type (name) VALUES ('Heater');
INSERT INTO device_type (name) VALUES ('Light');

INSERT INTO device (type_id, house_id, serial_number, state)
VALUES (1, 1, 'SN-123456', '{"@class": "com.yandexpracticum.devicemanagement.model.HeaterState", "on": true, "temperature": 22.5}');
INSERT INTO device (type_id, house_id, serial_number, state)
VALUES (1, 1, 'SN-654321', '{"@class": "com.yandexpracticum.devicemanagement.model.HeaterState", "on": false, "temperature": 75}');
SELECT 1;
