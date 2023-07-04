package fr.nmetivier.oss.atis.core.data.sensors;

import java.util.Optional;

public final class WeatherSensor<T> implements Sensor<T> {

    private final String name;
    private final T value;
    private final MeasureUnit measureUnit;

    public WeatherSensor(final String name, final T value, final MeasureUnit measureUnit) {
        this.name = name;
        this.value = value;
        this.measureUnit = measureUnit;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Optional<T> getValue() {
        return Optional.ofNullable(this.value);
    }

    @Override
    public MeasureUnit getMeasureUnit() {
        return this.measureUnit;
    }

}
