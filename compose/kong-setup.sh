#!/bin/sh

until curl --output /dev/null --silent --head --fail http://kong:8001; do
    printf '.'
    sleep 5
done

curl -i -X POST http://kong:8001/services/ \
  --data name=device-management \
  --data url=http://device-management:8080

curl -i -X POST http://kong:8001/services/ \
  --data name=telemetry \
  --data url=http://telemetry:8080

curl -s -X POST http://kong:8001/services/device-management/routes \
  --data "paths[]=/devices/" \
  --data "strip_path=true"

curl -s -X POST http://kong:8001/services/telemetry/routes \
  --data "paths[]=/telemetry/" \
  --data "strip_path=true"

curl -i -X POST http://kong:8001/services/device-management/routes \
  --data "paths[]=/api/devices/"\
  --data "strip_path=false"

curl -i -X POST http://kong:8001/services/telemetry/routes \
  --data "paths[]=/api/telemetry/"\
  --data "strip_path=false"
