package fr.nmetivier.simulators.weather.bulletins.metar;

import fr.nmetivier.simulators.weather.bulletins.Group;
import fr.nmetivier.simulators.weather.bulletins.WeatherReport;

public final class Metar implements WeatherReport {
    private final Group identificationGroup;
    private final Group windGroup;
    private final Group visibilityGroup;
    private final Group currentWeatherGroup;
    private final Group cloudinessGroup;
    private final Group temperatureGroup;
    private final Group qnhGroup;
    private final Group otherGroup;

    public Metar(final IdentificationGroup identificationGroup, final WindGroup windGroup, final VisibilityGroup visibilityGroup, CurrentWeatherGroup currentWeatherGroup, final CloudinessGroup cloudinessGroup, final TemperatureGroup temperatureGroup, final QnhGroup qnhGroup) {
        this.identificationGroup = identificationGroup;
        this.windGroup = windGroup;
        this.visibilityGroup = visibilityGroup;
        this.currentWeatherGroup = currentWeatherGroup;
        this.cloudinessGroup = cloudinessGroup;
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
            this.currentWeatherGroup.toString(), 
            this.cloudinessGroup.toString(),
            this.temperatureGroup.toString(),
            this.qnhGroup.toString(),
            ""
        ).trim().replace("  ", " ");
    }
}
