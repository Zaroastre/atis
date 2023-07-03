package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.util.Optional;

public interface WeatherBulletinFilter {
    Optional<String> filter(final int asciiCode);
    String getType();
}
