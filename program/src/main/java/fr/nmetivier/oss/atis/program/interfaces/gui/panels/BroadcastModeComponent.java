package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.nmetivier.oss.atis.core.data.TransmitionMode;

public class BroadcastModeComponent implements Paneled {

    private final JPanel panel;
    private final JButton permanentModeButton;
    private final JButton automaticModeButton;
    private TransmitionMode transmitionMode = TransmitionMode.AUTOMATIC;

    public BroadcastModeComponent() {
        this.panel = new JPanel(new BorderLayout());
        this.permanentModeButton = new JButton(TransmitionMode.PERMANENT.name() + " Mode");
        this.automaticModeButton = new JButton(TransmitionMode.AUTOMATIC.name() + " Mode");
        this.setup();
    }

    private final void bindEventListeners() {
        this.permanentModeButton.addActionListener((event) -> {
            this.transmitionMode = TransmitionMode.PERMANENT;
            permanentModeButton.setEnabled(false);
            automaticModeButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, String.format("A.T.I.S is in %S mode.", this.transmitionMode.name()));
        });
        this.automaticModeButton.addActionListener((event) -> {
            this.transmitionMode = TransmitionMode.AUTOMATIC;
            automaticModeButton.setEnabled(false);
            permanentModeButton.setEnabled(true);
            JOptionPane.showMessageDialog(null, String.format("A.T.I.S is in %S mode.", this.transmitionMode.name()));
        });
    }

    private final void setup() {
        this.permanentModeButton
                .setToolTipText("The VHF antenna always broadcasts the audio message of the weather report.");
        this.automaticModeButton
                .setToolTipText("The VHF antenna broadcasts the audio message at the request of aircraft pilots.");
        this.permanentModeButton.setSelected(this.transmitionMode == TransmitionMode.PERMANENT);
        this.permanentModeButton.setEnabled(!this.permanentModeButton.isSelected());
        this.automaticModeButton.setSelected(this.transmitionMode == TransmitionMode.AUTOMATIC);
        this.automaticModeButton.setEnabled(!this.automaticModeButton.isSelected());
        this.bindEventListeners();
        this.panel.add(this.automaticModeButton, BorderLayout.WEST);
        this.panel.add(this.permanentModeButton, BorderLayout.EAST);
    }

    @Override
    public final JPanel getPanel() {
        return this.panel;
    }
}
