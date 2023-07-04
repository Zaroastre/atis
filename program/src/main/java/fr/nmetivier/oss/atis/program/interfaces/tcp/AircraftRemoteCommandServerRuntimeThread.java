package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

final class AircraftRemoteCommandServerRuntimeThread extends Thread {
    private final int listenPort;
    private ServerSocket serverSocket;
    private final List<WeatherBulletinProviderConnection> connections = new ArrayList<>();
    private boolean isRunning = false;

    AircraftRemoteCommandServerRuntimeThread(int listenPort) {
        this.listenPort = listenPort;
    }

    @Override
    public void run() {
        if (!this.isRunning) {
            System.out.println("Aircraft Remote Command Server is starting...");
            try {
                this.serverSocket = new ServerSocket(listenPort);
                this.isRunning = true;
                System.out.println(String.format(
                        "Aircraft Remote Command Server is started and listening on tcp://127.0.0.1:%s", listenPort));
                while (this.isRunning) {
                    Socket socket = this.serverSocket.accept();
                    socket.close();
                }
            } catch (IOException e) {
                this.isRunning = false;
            }
        }
    }

    void terminate() throws IOException {
        System.out.println("Aircraft Remote Command Server is shutting down...");
        this.isRunning = false;
        this.connections.forEach(connection -> {
            try {
                connection.close();
            } catch (IOException ignore) {

            }
        });
        this.connections.clear();
        this.serverSocket.close();
        System.out.println("Aircraft Remote Command Server is stopped");
    }

}
