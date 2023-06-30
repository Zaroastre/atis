package fr.nmetivier.oss.atis.program;

import fr.nmetivier.oss.atis.program.interfaces.gui.ATISWindow;

public class ATISApplication {
    
    public static void main(String[] args) {
        final ATISService service = new DefaultATISService();
        try (final ATISWindow window = new ATISWindow(service)) {
            window.run();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
