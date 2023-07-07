package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.util.Optional;

public class METARWeatherBulletinFilter implements WeatherBulletinFilter {

    @Override
    public Optional<String> filter(final int asciiCode) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'filter'");
        System.out.println(asciiCode);
        return Optional.empty();
    }

    @Override
    public String getType() {
        return "METAR";
    }

}
