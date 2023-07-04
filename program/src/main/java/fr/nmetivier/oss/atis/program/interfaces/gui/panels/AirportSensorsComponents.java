package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.nmetivier.oss.atis.core.data.land.Airport;
import fr.nmetivier.oss.atis.core.data.land.Runway;
import fr.nmetivier.oss.atis.core.data.sensors.Sensor;

public class AirportSensorsComponents implements Paneled {
    private final JPanel panel;
    private DefaultMutableTreeNode treeNode;
    private DefaultTreeModel treeModel;
    private JTree tree;

    public AirportSensorsComponents() {
        this.panel = new JPanel(new BorderLayout());
        this.treeNode = new DefaultMutableTreeNode("Not airport selected");
        this.treeModel = new DefaultTreeModel(this.treeNode);
        this.tree = new JTree(this.treeModel);
        this.setup();
    }

    public final void setup() {
        this.panel.setBorder(new TitledBorder("Airport Sensors"));
        this.tree.setCellRenderer(new SensorsTreeCellRenderer());
        this.panel.add(this.tree);
    }
    
    @Override
    public final JPanel getPanel() {
        return this.panel;
    }

    public final void clearSensors() {
        this.treeNode.removeAllChildren();

    }

    public final void displaySensorsFromAirport(final Airport airport) {
        this.clearSensors();
        this.panel.remove(this.tree);
        this.treeNode = new DefaultMutableTreeNode(airport.getName());
        this.treeModel = new DefaultTreeModel(this.treeNode);
        this.tree = new JTree(this.treeModel);
        this.tree.setCellRenderer(new SensorsTreeCellRenderer());
        List<Sensor<?>> weatherSensors = airport.getRunways().stream().map(Runway::getSensors).flatMap(List::stream).toList();
        weatherSensors.forEach(sensor -> {
            DefaultMutableTreeNode sensorNode = new DefaultMutableTreeNode(sensor);
            this.treeNode.add(sensorNode);
        });
        this.treeModel.reload(this.treeNode);
        this.panel.add(this.tree);
    }

}
