package fr.nmetivier.oss.atis.program;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import fr.nmetivier.oss.atis.core.TransmitionMode;
import fr.nmetivier.oss.atis.core.data.Airport;
import fr.nmetivier.oss.atis.core.data.Land;
import fr.nmetivier.oss.atis.core.data.Runway;
import fr.nmetivier.oss.atis.core.data.RunwaySide;
import fr.nmetivier.oss.atis.core.data.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.WeatherBulletinIdentifier;
import fr.nmetivier.oss.atis.core.data.WeatherReportFrame;
import fr.nmetivier.oss.atis.core.data.WeatherSensor;

public class DefaultATISService implements ATISService {
    private final Land land;
    private Consumer<WeatherBulletin> onNewWeatherBulletinHandler = null;

    private final Thread bulletinGenerator;

    public DefaultATISService() {
        this.land = new Land("LND01", List.of(
                new Airport("TLS", "LFBO", List.of(
                        new Runway(
                                33,
                                RunwaySide.RIGHT,
                                List.of(
                                    new WeatherSensor<>("Drew Point", 0.2F),
                                    new WeatherSensor<>("Visibility", 10),
                                    new WeatherSensor<>("Wind", 14),
                                    new WeatherSensor<>("Preasure", 1021),
                                    new WeatherSensor<>("Humidity", 64),
                                    new WeatherSensor<>("Temperature", 21),
                                    new WeatherSensor<>("Clouds", "Some"),
                                    new WeatherSensor<>("Rain", 0)
                                )),
                        new Runway(
                                33,
                                RunwaySide.LEFT,
                                List.of(
                                    new WeatherSensor<>("Drew Point", 0.1F),
                                    new WeatherSensor<>("Visibility", 10),
                                    new WeatherSensor<>("Wind", 13),
                                    new WeatherSensor<>("Preasure", 1022),
                                    new WeatherSensor<>("Humidity", 65),
                                    new WeatherSensor<>("Temperature", 21),
                                    new WeatherSensor<>("Clouds", "Some"),
                                    new WeatherSensor<>("Rain", 0)))),
                        new ArrayList<>()),
                new Airport("CDG", "LFPG", List.of(
                        new Runway(
                                8,
                                RunwaySide.LEFT,
                                List.of(
                                    new WeatherSensor<>("Drew Point", 0.06F),
                                    new WeatherSensor<>("Visibility", 9),
                                    new WeatherSensor<>("Wind", 31),
                                    new WeatherSensor<>("Preasure", 987),
                                    new WeatherSensor<>("Humidity", 86),
                                    new WeatherSensor<>("Temperature", 23),
                                    new WeatherSensor<>("Clouds", "Some"),
                                    new WeatherSensor<>("Rain", 10))),
                        new Runway(
                                8,
                                RunwaySide.RIGHT,
                                List.of(
                                    new WeatherSensor<>("Drew Point", 0.05F),
                                    new WeatherSensor<>("Visibility", 9),
                                    new WeatherSensor<>("Wind", null),
                                    new WeatherSensor<>("Preasure", 989),
                                    new WeatherSensor<>("Humidity", 87),
                                    new WeatherSensor<>("Temperature", 23),
                                    new WeatherSensor<>("Clouds", "Some"),
                                    new WeatherSensor<>("Rain", 10))),
                        new Runway(
                                9,
                                RunwaySide.LEFT,
                                List.of(
                                    new WeatherSensor<>("Drew Point", 0.2F),
                                    new WeatherSensor<>("Visibility", 10),
                                    new WeatherSensor<>("Wind", 14),
                                    new WeatherSensor<>("Preasure", 1021),
                                    new WeatherSensor<>("Humidity", 64),
                                    new WeatherSensor<>("Temperature", 23),
                                    new WeatherSensor<>("Clouds"),
                                    new WeatherSensor<>("Rain", null))),
                        new Runway(
                                9,
                                RunwaySide.RIGHT,
                                List.of(
                                    new WeatherSensor<>("Drew Point", 0.2F),
                                    new WeatherSensor<>("Visibility", 10),
                                    new WeatherSensor<>("Wind", 14),
                                    new WeatherSensor<>("Preasure", 1025),
                                    new WeatherSensor<>("Humidity", 64),
                                    new WeatherSensor<>("Temperature", 23),
                                    new WeatherSensor<>("Clouds", "Some"),
                                    new WeatherSensor<>("Rain", 0)))),
                        new ArrayList<>())));
        this.bulletinGenerator = new Thread() {
            public void run() {
                while (!this.isInterrupted()) {
                    Random random = new Random();
                    int chance = random.nextInt(0, 100);
                    WeatherBulletin bulletin = new WeatherBulletin(
                        "LFBO",
                        (chance > 10) ? new WeatherReportFrame("LFBO...") : null, 
                        new HashMap<>(),
                        List.of(), 
                        null);
                    if (onNewWeatherBulletinHandler != null) {   
                        onNewWeatherBulletinHandler.accept(bulletin);
                    }
                    try {
                        Thread.sleep(1*1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };

        this.bulletinGenerator.start();
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
}
