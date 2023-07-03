package fr.nmetivier.oss.atis.stubs;

public final class WeatherFrame {
    private final String payload;
    private final String type;
    public WeatherFrame(final String payload, final String type) {
        this.payload = payload;
        this.type = type;
    }
    public String getPayload() {
        return payload;
    }
    public String getType() {
        return type;
    }
}
