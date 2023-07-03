package fr.nmetivier.oss.atis.stubs;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import fr.nmetivier.oss.atis.core.data.TransmitionMode;
import fr.nmetivier.oss.atis.core.data.land.Land;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletinIdentifier;

public interface ATISService extends WeatherBulletinRegistry, AudioPlayer {
    Land getLand();
    void changeMode(TransmitionMode mode);
    void startAudioStreaming();
    void stopAudioStreaming();
    void playAudio(Set<File> audioFiles);
    void addEventListenerOnNewWeatherBulletin(Consumer<WeatherBulletin> callback);
    Optional<WeatherBulletin> findWeatherBulletin(final WeatherBulletinIdentifier identifier);
}
