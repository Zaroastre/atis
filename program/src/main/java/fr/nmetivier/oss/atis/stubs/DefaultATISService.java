package fr.nmetivier.oss.atis.stubs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import fr.nmetivier.oss.atis.core.data.RunwaySide;
import fr.nmetivier.oss.atis.core.data.TransmitionMode;
import fr.nmetivier.oss.atis.core.data.land.Airport;
import fr.nmetivier.oss.atis.core.data.land.Land;
import fr.nmetivier.oss.atis.core.data.land.LandIdentifier;
import fr.nmetivier.oss.atis.core.data.land.Runway;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletinIdentifier;
import fr.nmetivier.oss.atis.core.data.sensors.WeatherSensor;

public class DefaultATISService implements ATISService {
    private final Land land;
    private Consumer<WeatherBulletin> onNewWeatherBulletinHandler = null;

    public DefaultATISService() {
        this.land = new Land(
            new LandIdentifier(),
            "SECTOR-01",
            Set.of(
                new Airport(
                    "Toulouse / Blagnac", 
                    "LFOB", 
                    List.of(
                        new Runway(
                            9, 
                            RunwaySide.CENTER, 
                            List.of(
                                new WeatherSensor<Float>("Humidity", 75.5F, null),
                                new WeatherSensor<String>("Cloud", null, null)
                            )
                        )
                    ), 
                    new ArrayList<>()
                ),
                new Airport(
                    "Barcelone-El Prat", 
                    "LEBL", 
                    List.of(
                        new Runway(
                            9, 
                            RunwaySide.CENTER, 
                            List.of(
                                new WeatherSensor<Float>("Humidity", 79F, null),
                                new WeatherSensor<Integer>("Preasure", 1016, null)
                            )
                        )
                    ), 
                    new ArrayList<>()
                )
            ),
            Locale.FRENCH
        );

    }
    @Override
    public void changeMode(TransmitionMode mode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addEventListenerOnNewWeatherBulletin(Consumer<WeatherBulletin> callback) {
        this.onNewWeatherBulletinHandler = callback;
    }

    @Override
    public void playAudio(Set<File> audioFiles) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startAudioStreaming() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopAudioStreaming() {
        // TODO Auto-generated method stub

    }

    @Override
    public Land getLand() {
        return this.land;
    }
    
    @Override
    public Optional<WeatherBulletin> findWeatherBulletin(WeatherBulletinIdentifier identifier) {
        return this.land
                .getAirports()
                .stream()
                .map(Airport::getWeatherBulletins)
                .flatMap(List::stream)
                .filter((bulletin) -> bulletin.getIdentifier().equals(identifier))
                .findFirst();
    }
    @Override
    public void play(List<File> playlist) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void register(WeatherFrame frame) {
        final WeatherBulletin bulletin = new WeatherBulletin(null, null, null, null, null);
        this.land
                .getAirports()
                .stream()
                .filter(airport -> airport.getIcao().equals(bulletin.getAirportICAO()))
                .findFirst()
                .ifPresent((airport) -> {
                    airport.getWeatherBulletins().add(bulletin);
                });
    }
    @Override
    public void repeatPlaylist(boolean enableRepeat) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void repeatTrap(boolean enableRepeat) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }
}
