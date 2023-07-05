package fr.nmetivier.simulators.weather.bulletins;

import java.util.function.Consumer;

public interface WeatherReportGenerator<T extends WeatherReport> {
    T generate();
    void generate(int internalInMilliseconds, Consumer<T> callback);
    void stop();
}
