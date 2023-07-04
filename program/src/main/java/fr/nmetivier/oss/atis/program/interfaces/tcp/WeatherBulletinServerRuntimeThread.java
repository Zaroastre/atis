package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.nmetivier.oss.atis.stubs.WeatherBulletinRegistry;

final class WeatherBulletinServerRuntimeThread extends Thread {
    private final int listenPort;
    private ServerSocket serverSocket;
    private final List<WeatherBulletinProviderConnection> connections = new ArrayList<>();
    private final WeatherBulletinRegistry registry;
    private boolean isRunning = false;

    WeatherBulletinServerRuntimeThread(final int listenPort, final WeatherBulletinRegistry registry) {
        this.registry = registry;
        this.listenPort = listenPort;
    }

    @Override
    public void run() {
        if (!this.isRunning) {
            System.out.println("Weather Bulletin Server is starting...");
            try {
                this.serverSocket = new ServerSocket(listenPort);
                this.isRunning = true;
                System.out.println(String.format(
                        "Weather Bulletin Server is started and listening on tcp://127.0.0.1:%s", listenPort));
                while (this.isRunning) {
                    Socket socket = this.serverSocket.accept();
                    WeatherBulletinProviderConnection connection = new WeatherBulletinProviderConnection(socket,
                            this.registry);
                    connections.add(connection);
                    connection.start();
                }
            } catch (IOException e) {
                this.isRunning = false;
            }
        }
    }

    void terminate() throws IOException {
        System.out.println("Weather Bulletin Server is shutting down...");
        this.isRunning = false;
        this.connections.forEach(connection -> {
            try {
                connection.close();
            } catch (IOException ignore) {

            }
        });
        this.connections.clear();
        this.serverSocket.close();
        System.out.println("Weather Bulletin Server is stopped");
    }
}
