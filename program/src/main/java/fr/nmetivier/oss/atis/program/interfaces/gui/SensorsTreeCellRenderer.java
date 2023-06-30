package fr.nmetivier.oss.atis.program.interfaces.gui;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import fr.nmetivier.oss.atis.core.data.sensors.Sensor;

public class SensorsTreeCellRenderer implements TreeCellRenderer {
    private final JLabel label;

    SensorsTreeCellRenderer() {
        label = new JLabel();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        if (userObject instanceof Sensor<?> sensor) {
            URL imageUrl;
            String text;
            if (sensor.getValue() != null && sensor.getValue().isPresent()) {
                imageUrl = getClass().getResource("/icons/16/green-led.png");
                text = String.format("%s: %s %s", sensor.getName(), sensor.getValue().get(), "");
            } else {
                imageUrl = getClass().getResource("/icons/16/red-led.png");
                text = String.format("%s: UNAVAILABLE", sensor.getName());
            }
            if (imageUrl != null) {
                label.setIcon(new ImageIcon(imageUrl));
            }
            label.setText(text);
        } else {
            label.setIcon(null);
            label.setText(value.toString());
        }
        return label;
    }
}
