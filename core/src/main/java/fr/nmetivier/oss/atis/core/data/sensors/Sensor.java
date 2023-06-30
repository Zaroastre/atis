package fr.nmetivier.oss.atis.core.data.sensors;

import java.util.Optional;

/**
 * WeatherSensor
 */
public interface Sensor<T> {
    String getName();
    Optional<T> getValue();
    MeasureUnit getMeasureUnit();
}