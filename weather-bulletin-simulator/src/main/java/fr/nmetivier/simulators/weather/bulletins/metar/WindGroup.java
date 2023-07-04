package fr.nmetivier.simulators.weather.bulletins.metar;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class WindGroup implements Group {
    // 32010G25KT or 32025KT or 220V36010G25KT
    private final int windSpeed;
    private final int current;
    private final int from;
    private final int to;
    private final int gustSpeed;

    public WindGroup(int windSpeed, int from, int current, int to, int gustSpeed) {
        this.windSpeed = windSpeed;
        this.current = current;
        this.from = from;
        this.to = to;
        this.gustSpeed = gustSpeed;
    }

    public int getWindSpeed() {
        return windSpeed;
    }
    public int getCurrent() {
        return current;
    }
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getGustSpeed() {
        return gustSpeed;
    }

    private final String addPadding(Object value, int requiredSize, String paddingCharacter) {
        StringBuilder builder = new StringBuilder(value.toString());
        while (builder.toString().length() < requiredSize) {
            builder.insert(0, paddingCharacter);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.addPadding(this.current, 3, "0"));

        if (this.gustSpeed > 0) {
            stringBuilder.append(this.addPadding(this.windSpeed, 2, "0"));
            stringBuilder.append("G");
            stringBuilder.append(this.addPadding(this.gustSpeed, 2, "0"));
        } else {
            stringBuilder.append(this.addPadding(this.windSpeed, 2, "0"));
        }
        stringBuilder.append("KT");
        if (this.from != this.to) {
            stringBuilder.append(" ");
            stringBuilder.append(this.addPadding(this.from, 3, "0"));
            stringBuilder.append("V");
            stringBuilder.append(this.addPadding(this.to, 3, "0"));
        }
        return stringBuilder.toString();
    }

    public static final class Builder {
        private int windSpeed = 0;
        private int from = 0;
        private int to = 0;
        private int current = 0;
        private int gustSpeed = 0;

        public Builder wind(final int speed) {
            this.windSpeed = speed;
            return this;
        }

        public Builder direction(final int direction) {
            return this.direction(direction, direction, direction);
        }

        public Builder direction(final int from, final int current, final int to) {
            this.from = from;
            this.current = current;
            this.to = to;
            return this;
        }

        public Builder gust(final int speed) {
            this.gustSpeed = speed;
            return this;
        }

        public WindGroup build() {
            return new WindGroup(windSpeed, from, current, to, gustSpeed);
        }
    }
}
