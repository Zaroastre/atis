package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.nmetivier.oss.atis.stubs.ATISService;

public class ControlCenterPanel extends JPanel {
    private final ATISService atisService;
    private final DefaultMutableTreeNode landTreeNode;
    private final DefaultTreeModel landTreeModel;
    private final JTree landTree;

    public ControlCenterPanel(final ATISService atisService) {
        super(new BorderLayout());
        this.atisService = atisService;
        this.landTreeNode = new DefaultMutableTreeNode(this.atisService.getLand().getName());
        this.landTreeModel = new DefaultTreeModel(landTreeNode);
        this.landTree = new JTree(this.landTreeModel);
        final TitledBorder landBorder = new TitledBorder("Control Center");
        this.setBorder(landBorder);
        this.setup();
    }

    private final void bindEventListeners() {
        this.atisService.getLand().getAirports().forEach(airport -> {
            DefaultMutableTreeNode airportNode = new DefaultMutableTreeNode(airport);
            airport.getRunways().forEach(runway -> {
                DefaultMutableTreeNode runwayNode = new DefaultMutableTreeNode(runway);
                airportNode.add(runwayNode);
            });
            landTreeNode.add(airportNode);
        });
    }

    private final void setup() {
        this.bindEventListeners();
        this.landTree.setCellRenderer(new LandTreeCellRenderer());
        this.add(this.landTree);
    }

    public final void addTreeSelectionListener(TreeSelectionListener treeSelectionListener) {
        this.landTree.addTreeSelectionListener(treeSelectionListener);
    }

    public final Object getLastSelectedPathComponent() {
        return this.landTree.getLastSelectedPathComponent();
    }
}
