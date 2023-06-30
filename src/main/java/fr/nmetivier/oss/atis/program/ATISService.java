package fr.nmetivier.oss.atis.program;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import fr.nmetivier.oss.atis.core.TransmitionMode;
import fr.nmetivier.oss.atis.core.data.Land;
import fr.nmetivier.oss.atis.core.data.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.WeatherBulletinIdentifier;

public interface ATISService {
    Land getLand();
    void changeMode(TransmitionMode mode);
    void startAudioStreaming();
    void stopAudioStreaming();
    void playAudio(Set<File> audioFiles);
    void addEventListenerOnNewWeatherBulletin(Consumer<WeatherBulletin> callback);
    Optional<WeatherBulletin> findWeatherBulletin(final WeatherBulletinIdentifier identifier);
}
