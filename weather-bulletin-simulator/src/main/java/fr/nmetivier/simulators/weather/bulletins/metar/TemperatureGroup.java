package fr.nmetivier.simulators.weather.bulletins.metar;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class TemperatureGroup implements Group {
    private final int temperature;
    private final int drewPoint;

    public TemperatureGroup(int temperature, int drewPoint) {
        this.temperature = temperature;
        this.drewPoint = drewPoint;
    }
    public int getDrewPoint() {
        return drewPoint;
    }
    public int getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (this.temperature < 0) {
            builder.append("M");
            builder.append(-this.temperature);
        } else {
            builder.append(this.temperature);
        }
        builder.append("/");
        if (this.drewPoint < 0) {
            builder.append("M");
            builder.append(-this.drewPoint);
        } else {
            builder.append(this.drewPoint);
        }
        return builder.toString();
    }

    public static final class Builder {
        private int temperature = 0;
        private int drewPoint = 0;

        public Builder temperature(final int temperature) {
            this.temperature = temperature;
            return this;
        }
        public Builder drewPoint(final int drewPoint) {
            this.drewPoint = drewPoint;
            return this;
        }

        public TemperatureGroup build() {
            return new TemperatureGroup(temperature, drewPoint);
        }
    }
}
