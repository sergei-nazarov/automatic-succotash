package com.yandexpracticum.telemetry.repository;

import com.yandexpracticum.telemetry.entity.TelemetryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface TelemetryRepository extends JpaRepository<TelemetryData, Long> {

    @Query("""
       SELECT t FROM TelemetryData t
       WHERE t.deviceId = :deviceId
       AND (t.telemetryType, t.timestamp, t.id) IN (
           SELECT t2.telemetryType, MAX(t2.timestamp), MAX(t2.id)
           FROM TelemetryData t2
           WHERE t2.deviceId = :deviceId
           GROUP BY t2.telemetryType
       )
       """)
    Stream<TelemetryData> findLatestTelemetryByDeviceIdGroupedByType(@Param("deviceId") Long deviceId);

    @Query("SELECT t FROM TelemetryData t WHERE t.deviceId = :deviceId AND t.timestamp BETWEEN :startDate AND :endDate")
    Stream<TelemetryData> findAllByDeviceIdAndTimestampBetween(
            @Param("deviceId") Long deviceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
