package fr.nmetivier.oss.atis.core.data.land;

import java.util.List;

import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletin;

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
