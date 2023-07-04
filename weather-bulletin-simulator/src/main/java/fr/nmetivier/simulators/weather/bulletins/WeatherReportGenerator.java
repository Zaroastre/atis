package fr.nmetivier.simulators.weather.bulletins;

public interface WeatherReportGenerator<T extends WeatherReport> {
    T generate();
}
