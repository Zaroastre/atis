package fr.nmetivier.oss.atis.core.data;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WeatherBulletin {
    private final WeatherBulletinIdentifier identifier;
    private final File audio;
    private final String type;
    private final LocalDateTime issueDateTime;
    private final int duration;
    private final WeatherReportFrame frame;
    private final String airportIcao;
    private final Map<String, Object> decodedData;
    private final List<String> readableData;

    public WeatherBulletin(final String airportIcao, final WeatherReportFrame frame, final Map<String, Object> decodedData, final List<String> readableData, final File audio) {
        this.identifier = new WeatherBulletinIdentifier(UUID.randomUUID());
        this.audio = audio;
        this.frame = frame;
        this.decodedData = decodedData;
        this.readableData = readableData;
        this.type = (frame != null) ? "METAR" : null;
        this.issueDateTime = LocalDateTime.now();
        this.duration = 85;
        this.airportIcao = airportIcao;
    }
    public WeatherBulletinIdentifier getIdentifier() {
        return identifier;
    }

    public File getAudio() {
        return audio;
    }
    public Map<String, Object> getDecodedData() {
        return decodedData;
    }
    public int getDuration() {
        return duration;
    }
    public WeatherReportFrame getFrame() {
        return frame;
    }
    public LocalDateTime getIssueDateTime() {
        return issueDateTime;
    }
    public List<String> getReadableData() {
        return readableData;
    }
    public String getType() {
        return type;
    }
    public String getAirportICAO() {
        return airportIcao;
    }
}
