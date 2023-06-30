package fr.nmetivier.oss.atis.core.data.messages;

import java.util.UUID;

public class WeatherBulletinIdentifier {
    private final UUID value;

    public WeatherBulletinIdentifier(final UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
