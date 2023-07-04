package fr.nmetivier;

import java.util.Locale;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;


/**
 * Hello world!
 *
 */
public class App {
    
    public static void main(String[] args) {
        try {
            // setting properties as Kevin Dictionary
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.AlanVoiceDirectory");
            // registering speech engine
            Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
            // create a Synthesizer that generates voice
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            // allocates a synthesizer
            synthesizer.allocate();
            // resume a Synthesizer
            synthesizer.resume();
            // speak the specified text until the QUEUE become empty
            synthesizer.speakPlainText("Don't limit yourself. Many people limit themselves to what they think they can do. You can go as far as your mind lets you. What you believe, remember, you can achieve.", null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            // deallocating the Synthesizer
            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
