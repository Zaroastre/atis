package fr.nmetivier.oss.atis.core.data;

import java.util.List;

public class Airport {
    private final String name;
    private final String icao;
    private final List<Runway> runways;
    private final List<WeatherBulletin> weatherBulletins;

    public Airport(final String name, final String icao, final List<Runway> runways, final List<WeatherBulletin> weatherBulletins) {
        this.name = name;
        this.icao = icao;
        this.runways = runways;
        this.weatherBulletins = weatherBulletins;
    }
    public String getIcao() {
        return icao;
    }
    public String getName() {
        return name;
    }
    public List<Runway> getRunways() {
        return runways;
    }
    public List<WeatherBulletin> getWeatherBulletins() {
        return weatherBulletins;
    }
}
