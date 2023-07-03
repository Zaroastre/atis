package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fr.nmetivier.oss.atis.stubs.WeatherBulletinRegistry;

public class WeatherBulletinServer implements TcpServer {

    private final int listenPort;
    private ServerSocket serverSocket;
    private final List<WeatherBulletinProviderConnection> connections = new ArrayList<>();
    private final WeatherBulletinRegistry registry;

    public WeatherBulletinServer(final int listenPort, final WeatherBulletinRegistry registry) {
        this.registry = registry;
        this.listenPort = listenPort;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(listenPort);
            Socket socket = this.serverSocket.accept();
            WeatherBulletinProviderConnection connection = new WeatherBulletinProviderConnection(socket, this.registry);
            connections.add(connection);
            connection.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    public void close() throws IOException {
        this.connections.forEach(connection -> {
            try {
                connection.close();
            } catch (IOException ignore) {

            }
        });
        this.connections.clear();
        this.serverSocket.close();
    }
}
