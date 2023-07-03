package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import fr.nmetivier.oss.atis.core.data.TransmitionMode;

public class ModePanel extends JPanel {

    private final JToggleButton permanentModeButton;
    private final JToggleButton automaticModeButton;

    public ModePanel() {
        super(new BorderLayout());
        this.permanentModeButton = new JToggleButton(TransmitionMode.PERMANENT.name());
        this.automaticModeButton = new JToggleButton(TransmitionMode.AUTOMATIC.name());
        this.setup();
    }

    private final void bindEventListeners() {
        this.permanentModeButton.addItemListener((itemEvent) -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                permanentModeButton.setEnabled(false);
                automaticModeButton.setEnabled(true);
                automaticModeButton.doClick();
            }
        });
        this.automaticModeButton.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                automaticModeButton.setEnabled(false);
                permanentModeButton.setEnabled(true);
                permanentModeButton.doClick();
            }
        });
    }

    public void setup() {
        this.permanentModeButton
                .setToolTipText("The VHF antenna always broadcasts the audio message of the weather report.");
        this.automaticModeButton
                .setToolTipText("The VHF antenna broadcasts the audio message at the request of aircraft pilots.");
        this.permanentModeButton.setSelected(true);
        this.permanentModeButton.setEnabled(!this.permanentModeButton.isSelected());
        this.automaticModeButton.setSelected(false);
        this.automaticModeButton.setEnabled(!this.automaticModeButton.isSelected());
        this.bindEventListeners();
        this.add(this.automaticModeButton, BorderLayout.WEST);
        this.add(this.permanentModeButton, BorderLayout.EAST);
    }
}
