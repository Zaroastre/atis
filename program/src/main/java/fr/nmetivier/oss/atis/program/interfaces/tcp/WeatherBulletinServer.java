package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.io.IOException;

import fr.nmetivier.oss.atis.stubs.WeatherBulletinRegistry;

public class WeatherBulletinServer implements TcpServer {

    private final int listenPort;
    private final WeatherBulletinRegistry registry;
    private WeatherBulletinServerRuntimeThread serverRuntimeThread = null; 

    public WeatherBulletinServer(final int listenPort, final WeatherBulletinRegistry registry) {
        this.listenPort = listenPort;
        this.registry = registry;
    }

    @Override
    public void run() {
        if (this.serverRuntimeThread == null) {
            this.serverRuntimeThread = new WeatherBulletinServerRuntimeThread(this.listenPort, this.registry);
            this.serverRuntimeThread.start();
        }
    }
    
    @Override
    public void close() throws IOException {
        if (this.serverRuntimeThread != null) {
            this.serverRuntimeThread.terminate();
            this.serverRuntimeThread = null;
        }
    }
}
