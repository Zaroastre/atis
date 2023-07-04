package fr.nmetivier.simulators.weather.bulletins.metar;

import fr.nmetivier.simulators.weather.bulletins.Group;
import fr.nmetivier.simulators.weather.bulletins.WeatherReport;

public final class Metar implements WeatherReport {
    private final Group identificationGroup;
    private final Group windGroup;
    private final Group visibilityGroup;
    private final Group currentWeatherGroup;
    private final Group nebulosityGroup;
    private final Group temperatureGroup;
    private final Group qnhGroup;
    private final Group otherGroup;

    public Metar(final IdentificationGroup identificationGroup, final WindGroup windGroup, final VisibilityGroup visibilityGroup, final TemperatureGroup temperatureGroup, final QnhGroup qnhGroup) {
        this.identificationGroup = identificationGroup;
        this.windGroup = windGroup;
        this.visibilityGroup = visibilityGroup;
        this.currentWeatherGroup = null;
        this.nebulosityGroup = null;
        this.temperatureGroup = temperatureGroup;
        this.qnhGroup = qnhGroup;
        this.otherGroup = null;
    }

    @Override
    public String toString() {
        return String.format(
            "%s %s %s %s %s %s %s %s",
            this.identificationGroup.toString(),
            this.windGroup.toString(),
            this.visibilityGroup.toString(),
             "", "", "", 
            this.temperatureGroup.toString(),
            this.qnhGroup.toString()
            // this.visibilityGroup.toString(),
            // this.currentWeatherGroup.toString(),
            // this.nebulosityGroup.toString(),
            // this.temperatureGroup.toString(),
            // this.qnhGroup.toString(),
            // this.otherGroup.toString()
        ).trim();
    }
}
