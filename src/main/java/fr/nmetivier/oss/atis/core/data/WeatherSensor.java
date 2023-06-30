package fr.nmetivier.oss.atis.core.data;

import java.util.Optional;

public class WeatherSensor<T> implements Sensor<T> {

    private final String name;
    private T value;

    public WeatherSensor(final String name) {
        this(name, null);
    }
    
    public WeatherSensor(final String name, final T value) {
        this.name = name;
        this.value = value;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Optional<T> getValue() {
        return Optional.ofNullable(this.value);
    }
}
