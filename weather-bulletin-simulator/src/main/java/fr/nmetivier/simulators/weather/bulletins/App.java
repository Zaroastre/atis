package fr.nmetivier.simulators.weather.bulletins;

import java.io.IOException;
import java.net.InetAddress;
import java.util.function.Consumer;

import fr.nmetivier.simulators.weather.bulletins.metar.METARGenerator;
import fr.nmetivier.simulators.weather.bulletins.metar.Metar;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        final ApplicationConfiguration configuration = new ApplicationConfiguration.Builder()
                .resource("weather-bulletin-simulator")
                .build();
        try (
            final WeatherReportClient client = new WeatherReportClient()) {
                final Consumer<Metar> callback = (metar) -> {
                if (!client.isConnected()) {
                    try {
                        client.connect(InetAddress.getByName(configuration.getHost()), configuration.getPort());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (client.isConnected()) {
                    try {
                        client.send(metar);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            final WeatherReportGenerator<Metar> generator = new METARGenerator();
            generator.generate(configuration.getIntervalInMilliseconds(), callback);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
