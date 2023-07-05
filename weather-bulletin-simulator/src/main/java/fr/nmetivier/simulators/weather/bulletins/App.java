package fr.nmetivier.simulators.weather.bulletins;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import fr.nmetivier.simulators.weather.bulletins.metar.METARGenerator;
import fr.nmetivier.simulators.weather.bulletins.metar.Metar;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        // OTHER GROUP
        // TEMPO FM1400 TS1430 OVC015
        Consumer<Metar> callback = (metar) -> {
            System.out.println(metar);
        };
        WeatherReportGenerator<Metar> generator = new METARGenerator();
        generator.generate(1000, callback);
    }
}
