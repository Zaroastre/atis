package fr.nmetivier.oss.atis.core.data.land;

import java.util.Set;

import fr.nmetivier.oss.atis.core.data.messages.outputs.Message;
import fr.nmetivier.oss.atis.core.data.sensors.WeatherSensor;

public class Station {
    private final StationIdentifier identifier;
    private final Set<Message> messages;
    private final Set<WeatherSensor<?>> weatherSensors;

    public Station(final StationIdentifier identifier, final Set<WeatherSensor<?>> weatherSensors, final Set<Message> messages) {
        this.identifier = identifier;
        this.weatherSensors = weatherSensors;
        this.messages = messages;
    }
    public StationIdentifier getIdentifier() {
        return identifier;
    }
    public Set<Message> getMessages() {
        return messages;
    }
    public Set<WeatherSensor<?>> getWeatherSensors() {
        return weatherSensors;
    }
}
