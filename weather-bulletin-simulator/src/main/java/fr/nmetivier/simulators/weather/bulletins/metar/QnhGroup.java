package fr.nmetivier.simulators.weather.bulletins.metar;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class QnhGroup implements Group {
    private final int pressure;

    public QnhGroup(int pressure) {
        this.pressure = pressure;
    }

    public int getPressure() {
        return pressure;
    }

    @Override
    public String toString() {
        return String.format("Q%s", this.pressure);
    }

    public static final class Builder {
        private int pressure = 0;

        public Builder pressure(final int pressure) {
            this.pressure = pressure;
            return this;
        }

        public QnhGroup build() {
            return new QnhGroup(pressure);
        }
    }
}
