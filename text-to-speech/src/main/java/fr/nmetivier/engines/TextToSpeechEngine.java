package fr.nmetivier.engines;

import java.io.File;

import fr.nmetivier.TextToSpeechException;

public interface TextToSpeechEngine {
    File synthesize(final File textFile) throws TextToSpeechException;
    File synthesize(final String text) throws TextToSpeechException;
}
