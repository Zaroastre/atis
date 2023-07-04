package fr.nmetivier.oss.atis.program.interfaces.tcp;

import java.io.IOException;

public class AircraftRemoteCommandServer implements TcpServer {

    private final int listenPort;
    private AircraftRemoteCommandServerRuntimeThread serverRuntimeThread = null; 

    public AircraftRemoteCommandServer(final int listenPort) {
        this.listenPort = listenPort;
    }

    @Override
    public void run() {
        if (this.serverRuntimeThread == null) {
            this.serverRuntimeThread = new AircraftRemoteCommandServerRuntimeThread(this.listenPort);
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
