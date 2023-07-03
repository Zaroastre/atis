package fr.nmetivier.oss.atis.stubs;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import fr.nmetivier.oss.atis.core.data.TransmitionMode;
import fr.nmetivier.oss.atis.core.data.land.Airport;
import fr.nmetivier.oss.atis.core.data.land.Land;
import fr.nmetivier.oss.atis.core.data.land.LandIdentifier;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletinIdentifier;
import fr.nmetivier.oss.atis.core.data.messages.WeatherReportFrame;

public class DefaultATISService implements ATISService {
    private final Land land;
    private Consumer<WeatherBulletin> onNewWeatherBulletinHandler = null;

    private final Thread bulletinGenerator;

    public DefaultATISService() {
        this.land = new Land(
            new LandIdentifier(),
            "",
            Set.of(),
            Locale.FRENCH
        );

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
