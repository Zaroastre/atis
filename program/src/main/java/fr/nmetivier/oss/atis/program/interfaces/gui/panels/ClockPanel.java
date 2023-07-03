package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClockPanel extends JPanel {
    private final Thread clock;
    private final JLabel clockLabel;

    public ClockPanel() {
        super(new BorderLayout());
        this.clockLabel = new JLabel();
        this.clock = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    try {
                        clockLabel.setText(
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                        + " UTC");
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        this.setup();
    }

    private final void setup() {
        this.add(this.clockLabel);
        this.clock.start();
    }
}
