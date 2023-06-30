package fr.nmetivier.oss.atis.program.interfaces.gui;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import fr.nmetivier.oss.atis.core.data.Airport;
import fr.nmetivier.oss.atis.core.data.Runway;

public class LandTreeCellRenderer implements TreeCellRenderer {
    private final JLabel label;

    LandTreeCellRenderer() {
        label = new JLabel();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        if (userObject instanceof Runway runway) {
            URL imageUrl = getClass().getResource("/icons/16/runway.png");
            if (imageUrl != null) {
                label.setIcon(new ImageIcon(imageUrl));
            }
            label.setText(runway.getName());
        } else if (userObject instanceof Airport airport) {
            URL imageUrl = getClass().getResource("/icons/16/airport.png");
            if (imageUrl != null) {
                label.setIcon(new ImageIcon(imageUrl));
            }
            label.setText(airport.getName());
        }
         else {
            label.setIcon(null);
            label.setText(value.toString());
        }
        return label;
    }

}
