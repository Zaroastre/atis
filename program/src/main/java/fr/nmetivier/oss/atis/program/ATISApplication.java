package fr.nmetivier.oss.atis.program;

import java.io.IOException;

import fr.nmetivier.oss.atis.program.interfaces.gui.windows.ATISWindow;
import fr.nmetivier.oss.atis.program.interfaces.tcp.TcpServer;
import fr.nmetivier.oss.atis.program.interfaces.tcp.WeatherBulletinServer;
import fr.nmetivier.oss.atis.stubs.ATISService;
import fr.nmetivier.oss.atis.stubs.DefaultATISService;

public class ATISApplication {
    
    public static void main(String[] args) {

        final ATISService service = new DefaultATISService();
        final TcpServer server = new WeatherBulletinServer(9090, service);
        try (final ATISWindow window = new ATISWindow(service)) {
            window.run();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

}
