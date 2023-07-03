package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

public class AirportSensorsPanel extends JPanel {
    private final DefaultMutableTreeNode sensorsTreeNode;
    private final DefaultTreeModel sensorsTreeModel;
    private final JTree sensorsTree;

    public AirportSensorsPanel() {
        super(new BorderLayout());
        this.sensorsTreeNode = new DefaultMutableTreeNode("NOTHING");
        this.sensorsTreeModel = new DefaultTreeModel(sensorsTreeNode);
        this.sensorsTree = new JTree(this.sensorsTreeModel);
        this.setup();
    }

    public final void setup() {
        this.setBorder(new TitledBorder("Airport Sensors"));
        this.sensorsTree.setCellRenderer(new SensorsTreeCellRenderer());
        this.add(this.sensorsTree);
    }

    public void removeAllChildren() {
        sensorsTreeNode.removeAllChildren();
    }
    
    public void add(MutableTreeNode child) {
        sensorsTreeNode.add(child);
    }

    public void reload() {
        this.sensorsTreeModel.reload(sensorsTreeNode);
    }
}
