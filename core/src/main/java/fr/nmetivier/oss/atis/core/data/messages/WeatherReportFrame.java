package fr.nmetivier.oss.atis.core.data.messages;

public class WeatherReportFrame {
    private final String frame;

    public WeatherReportFrame(final String frame) {
        this.frame = frame;
    } 
    public String getFrame() {
        return frame;
    }
}
