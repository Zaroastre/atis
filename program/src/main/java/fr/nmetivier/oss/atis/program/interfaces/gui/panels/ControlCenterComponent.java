package fr.nmetivier.oss.atis.program.interfaces.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.nmetivier.oss.atis.core.data.land.Land;

public final class ControlCenterComponent implements Paneled {
    
    private final Land land;
    private final JPanel panel;
    private final DefaultMutableTreeNode treeNode;
    private final DefaultTreeModel treeModel;
    private final JTree tree;

    public ControlCenterComponent(final Land land) {
        this.land = land;
        this.panel = new JPanel(new BorderLayout());
        this.treeNode = new DefaultMutableTreeNode(land.getName());
        this.treeModel = new DefaultTreeModel(treeNode);
        this.tree = new JTree(this.treeModel);
        this.setup();
    }

    private void expandAllNodes(final JTree tree, final int startingIndex, final int rowCount){
        for (int i=startingIndex; i<rowCount; ++i){
            tree.expandRow(i);
        }

        if (rowCount != tree.getRowCount()) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }


    private final void bindEventListeners() {
        land.getAirports().forEach(airport -> {
            DefaultMutableTreeNode airportNode = new DefaultMutableTreeNode(airport);
            airport.getRunways().forEach(runway -> {
                DefaultMutableTreeNode runwayNode = new DefaultMutableTreeNode(runway);
                airportNode.add(runwayNode);
            });
            treeNode.add(airportNode);
        });
    }

    private final void setup() {
        this.tree.setCellRenderer(new LandTreeCellRenderer());
        this.panel.setBorder(new TitledBorder("Control Center (Airports)"));
        this.bindEventListeners();
        this.panel.add(this.tree);
        this.expandAllNodes(tree, 0, tree.getRowCount());
    }

    public final void addTreeSelectionListener(final TreeSelectionListener treeSelectionListener) {
        this.tree.addTreeSelectionListener(treeSelectionListener);
    }

    public final Object getLastSelectedPathComponent() {
        return this.tree.getLastSelectedPathComponent();
    }
    @Override
    public JPanel getPanel() {
        return this.panel;
    }
}
