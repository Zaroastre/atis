package fr.nmetivier.simulators.weather.bulletins;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class WeatherReportClient implements Closeable {
    private Socket socket;
    private BufferedWriter bufferedWriter;

    public final boolean isConnected() {
        return (this.socket != null && this.socket.isConnected());
    }

    public final void connect(final InetAddress host, final int port) throws IOException {
        this.socket = new Socket(host, port);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));

    }

    public final void send(final WeatherReport report) throws IOException {
        try {
            this.bufferedWriter.write(report.toString());
            this.bufferedWriter.flush();
        } catch(SocketException exception) {
            this.close();
        }
    }

    @Override
    public void close() throws IOException {
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }
    }
}
