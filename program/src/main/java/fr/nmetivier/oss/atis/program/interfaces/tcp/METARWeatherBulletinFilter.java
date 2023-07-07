package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.util.Optional;

public class METARWeatherBulletinFilter implements WeatherBulletinFilter {

    private StringBuilder report = null;

    @Override
    public Optional<String> filter(final int asciiCode) {
        Optional<String> filteredReport = Optional.empty();
        if (asciiCode == 1) {
            report = new StringBuilder();
        } else if (asciiCode == 4) {
            if (this.report != null && this.report.length() > 0) {
                filteredReport = Optional.ofNullable(this.report.toString());
            }
        } else {
            if (this.report != null) {
                this.report.append((char) asciiCode);
            }
        }
        return filteredReport;
    }

    @Override
    public String getType() {
        return "METAR";
    }

}
