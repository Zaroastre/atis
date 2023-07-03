package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;

import fr.nmetivier.oss.atis.stubs.WeatherBulletinRegistry;
import fr.nmetivier.oss.atis.stubs.WeatherFrame;

public final class WeatherBulletinProviderConnection extends Thread implements Closeable {
        private final Socket socket;
        private final WeatherBulletinRegistry registry;
        private final WeatherBulletinFilter bulletinFilter;

        WeatherBulletinProviderConnection(final Socket socket, final WeatherBulletinRegistry registry) {
            this.socket = socket;
            this.registry = registry;
            this.bulletinFilter = new METARWeatherBulletinFilter();
        }

        @Override
        public void run() {
            if (this.socket.isConnected()) {
                try (InputStream input = socket.getInputStream()) {
                    InputStreamReader reader = new InputStreamReader(input);
                    while (this.socket.isConnected()) {
                        final int asciiCode = reader.read();  // reads a single character
                        final Optional<String> protientialFrame = this.bulletinFilter.filter(asciiCode);
                        protientialFrame.ifPresent(frame -> {
                            this.registry.register(new WeatherFrame(frame, this.bulletinFilter.getType()));
                        });
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void close() throws IOException {
            this.socket.close();
        }
    }