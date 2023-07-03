package fr.nmetivier.oss.atis.stubs;

import java.io.File;
import java.util.List;

public interface AudioPlayer {
    void play(List<File> playlist);
    void stop();
    void repeatPlaylist(boolean enableRepeat);
    void repeatTrap(boolean enableRepeat);
}
