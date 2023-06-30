package fr.nmetivier.oss.atis.program.interfaces.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.nmetivier.oss.atis.core.data.TransmitionMode;
import fr.nmetivier.oss.atis.core.data.land.Airport;
import fr.nmetivier.oss.atis.core.data.land.Runway;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletinIdentifier;
import fr.nmetivier.oss.atis.program.ATISService;

public final class ATISWindow implements Runnable, Closeable {

    private final ATISService atisService;

    private final JFrame frame;
    private final JPanel root;

    // Logo
    private final JLabel logoLabel;
    private final JPanel logoPanel;

    // Clock
    private final Thread clockThread;
    private final JLabel clockLabel;
    private final JPanel clockPanel;

    // Mode
    private final JToggleButton permanentModeButton;
    private final JToggleButton automaticModeButton;
    private final JPanel modePanel;

    // Land
    private final DefaultMutableTreeNode landTreeNode;
    private final DefaultTreeModel landTreeModel;
    private final JTree landTree;
    private final JPanel landPanel;

    // Sensors
    private final DefaultMutableTreeNode sensorsTreeNode;
    private final DefaultTreeModel sensorsTreeModel;
    private final JTree sensorsTree;
    private final JPanel sensorsPanel;

    // Bulletins
    private final DefaultTableModel bulletinsTableModel;
    private final JTable bulletinsTable;
    private final JPanel bulletinsPanel;

    private final JLabel weatherBulletinFrameLabel;
    private final JPanel weatherBulletinFramePanel;
    private final JTextPane weatherBulletinDecodedText;
    private final JPanel weatherBulletinDecodedPanel;
    private final JTextPane weatherBulletinReadableText;
    private final JPanel weatherBulletinReadablePanel;

    private final Airport selectedAirport = null;

    public ATISWindow(final ATISService atisService) {
        this.atisService = atisService;
        // Create children components
        this.logoLabel = new JLabel("OSS ATIS");
        this.logoPanel = new JPanel(new BorderLayout());
        this.clockLabel = new JLabel();
        this.clockPanel = new JPanel(new BorderLayout());
        this.clockThread = new Thread() {
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
        this.permanentModeButton = new JToggleButton(TransmitionMode.PERMANENT.name());
        this.automaticModeButton = new JToggleButton(TransmitionMode.AUTOMATIC.name());
        this.modePanel = new JPanel(new BorderLayout());
        final JPanel headerPanel = new JPanel(new BorderLayout());
        this.landTreeNode = new DefaultMutableTreeNode(this.atisService.getLand().getName());
        this.landTreeModel = new DefaultTreeModel(landTreeNode);
        this.landTree = new JTree(this.landTreeModel);
        final TitledBorder landBorder = new TitledBorder("Control Center");
        this.landPanel = new JPanel(new BorderLayout());
        this.sensorsTreeNode = new DefaultMutableTreeNode("NOTHING");
        this.sensorsTreeModel = new DefaultTreeModel(sensorsTreeNode);
        this.sensorsTree = new JTree(this.sensorsTreeModel);
        final TitledBorder sensorsBorder = new TitledBorder("Airport Sensors");
        this.sensorsPanel = new JPanel(new BorderLayout());
        final JPanel leftPanel = new JPanel(new BorderLayout());
        final String[] bulletinsTableHeader = new String[] {
                "ID", "Date", "Type", "Airport", "Duration", "Is Broadcasting", "Actions"
        };
        this.bulletinsTableModel = new DefaultTableModel(null, bulletinsTableHeader);
        this.bulletinsTable = new JTable(this.bulletinsTableModel);
        this.bulletinsPanel = new JPanel(new BorderLayout());

        this.weatherBulletinFrameLabel = new JLabel();
        this.weatherBulletinFramePanel = new JPanel(new BorderLayout());
        this.weatherBulletinDecodedText = new JTextPane();
        this.weatherBulletinDecodedPanel = new JPanel(new BorderLayout());
        this.weatherBulletinReadableText = new JTextPane();
        this.weatherBulletinReadablePanel = new JPanel(new BorderLayout());
        final JPanel weatherBulletinDetailsPanel = new JPanel(new BorderLayout());

        final JPanel centerPanel = new JPanel(new BorderLayout());
        this.root = new JPanel(new BorderLayout());
        this.frame = new JFrame("Open-Source ATIS System");

        // Setup children components
        this.permanentModeButton
                .setToolTipText("The VHF antenna always broadcasts the audio message of the weather report.");
        this.automaticModeButton
                .setToolTipText("The VHF antenna broadcasts the audio message at the request of aircraft pilots.");
        this.permanentModeButton.setSelected(true);
        this.permanentModeButton.setEnabled(!this.permanentModeButton.isSelected());
        this.automaticModeButton.setSelected(false);
        this.automaticModeButton.setEnabled(!this.automaticModeButton.isSelected());
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
        this.atisService.getLand().getAirports().forEach(airport -> {
            DefaultMutableTreeNode airportNode = new DefaultMutableTreeNode(airport);
            airport.getRunways().forEach(runway -> {
                DefaultMutableTreeNode runwayNode = new DefaultMutableTreeNode(runway);
                airportNode.add(runwayNode);
            });
            landTreeNode.add(airportNode);
        });
        landTree.addTreeSelectionListener(event -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) landTree.getLastSelectedPathComponent();
            if (selectedNode.isLeaf()) {
                Runway runway = (Runway) selectedNode.getUserObject();
                sensorsTreeNode.removeAllChildren();
                runway.getSensors().forEach(sensor -> {
                    DefaultMutableTreeNode sensorNode = new DefaultMutableTreeNode(sensor);
                    sensorsTreeNode.add(sensorNode);
                });
                sensorsTreeModel.reload(sensorsTreeNode);
            }
        });
        this.bulletinsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.sensorsPanel.setBorder(sensorsBorder);
        this.landPanel.setBorder(landBorder);
        this.weatherBulletinFrameLabel.setText("No weather report available");
        this.weatherBulletinDecodedText.setText("No weather report to decode");
        this.weatherBulletinReadableText.setText("No decoded weather report to make readable");
        this.weatherBulletinDecodedText.setEditable(false);
        this.weatherBulletinReadableText.setEditable(false);
        weatherBulletinDetailsPanel
                .setPreferredSize(new Dimension((int) weatherBulletinDetailsPanel.getPreferredSize().getWidth(), 400));
        this.weatherBulletinDecodedText
                .setPreferredSize(new Dimension(600, (int) weatherBulletinDecodedText.getPreferredSize().getHeight()));
        this.weatherBulletinReadableText
                .setPreferredSize(new Dimension(600, (int) weatherBulletinReadableText.getPreferredSize().getHeight()));
        leftPanel.setPreferredSize(new Dimension(200, (int) leftPanel.getPreferredSize().getHeight()));
        this.bulletinsTable.getSelectionModel().addListSelectionListener((event) -> {
            try {
                WeatherBulletinIdentifier selectedWeatherBulletinIdentifier = (WeatherBulletinIdentifier) this.bulletinsTable.getValueAt(this.bulletinsTable.getSelectedRow(), 0);
                Optional<WeatherBulletin> existingBulletin = atisService.findWeatherBulletin(selectedWeatherBulletinIdentifier);
                existingBulletin.ifPresent((bulletin) -> {
                    StringBuilder decodedDataStringBuilder = new StringBuilder();
                    bulletin.getDecodedData().entrySet().forEach(entry -> {
                        decodedDataStringBuilder.append(String.format("%s = %s".format(entry.getKey(), entry.getValue().toString())));
                        decodedDataStringBuilder.append("\n");
                    });
                    weatherBulletinDecodedText.setText(decodedDataStringBuilder.toString());

                    StringBuilder readableDataStringBuilder = new StringBuilder();
                    bulletin.getReadableData().forEach(sentence -> {
                        readableDataStringBuilder.append(sentence);
                        readableDataStringBuilder.append("\n");
                    });
                    weatherBulletinReadableText.setText(readableDataStringBuilder.toString());

                });
            } catch (Exception ignoreTheLostSelection) {
                // Ignore when the selected row is deleted.
            }
        });

        // Display children components
        this.logoPanel.add(this.logoLabel);
        // headerPanel.add(this.logoPanel, BorderLayout.WEST);
        this.clockPanel.add(this.clockLabel);
        headerPanel.add(this.clockPanel, BorderLayout.WEST);
        this.modePanel.add(this.automaticModeButton, BorderLayout.WEST);
        this.modePanel.add(this.permanentModeButton, BorderLayout.EAST);
        headerPanel.add(this.modePanel, BorderLayout.EAST);
        this.root.add(headerPanel, BorderLayout.NORTH);
        this.landTree.setCellRenderer(new LandTreeCellRenderer());
        this.landPanel.add(this.landTree);
        leftPanel.add(this.landPanel, BorderLayout.NORTH);
        this.sensorsTree.setCellRenderer(new SensorsTreeCellRenderer());
        this.sensorsPanel.add(this.sensorsTree);
        leftPanel.add(this.sensorsPanel, BorderLayout.SOUTH);
        this.root.add(leftPanel, BorderLayout.WEST);
        this.bulletinsPanel.add(new JScrollPane(this.bulletinsTable), BorderLayout.CENTER);
        this.weatherBulletinFramePanel.add(this.weatherBulletinFrameLabel, BorderLayout.CENTER);
        weatherBulletinDetailsPanel.add(this.weatherBulletinFramePanel, BorderLayout.NORTH);
        this.weatherBulletinDecodedPanel.add(this.weatherBulletinDecodedText, BorderLayout.CENTER);
        weatherBulletinDetailsPanel.add(this.weatherBulletinDecodedPanel, BorderLayout.WEST);
        this.weatherBulletinReadablePanel.add(this.weatherBulletinReadableText, BorderLayout.CENTER);
        weatherBulletinDetailsPanel.add(this.weatherBulletinReadablePanel, BorderLayout.EAST);
        this.bulletinsPanel.add(weatherBulletinDetailsPanel, BorderLayout.SOUTH);

        centerPanel.add(this.bulletinsPanel, BorderLayout.CENTER);
        this.root.add(centerPanel, BorderLayout.CENTER);
        this.frame.add(this.root);
        this.frame.pack();
        this.clockThread.start();
        this.atisService.addEventListenerOnNewWeatherBulletin((theNewWeatherBulletin) -> {
            Optional<Airport> competentAirport = atisService.getLand()
                    .getAirports()
                    .stream()
                    .filter(airport -> airport.getIcao().equalsIgnoreCase(theNewWeatherBulletin.getAirportICAO()))
                    .findFirst();
            competentAirport.ifPresentOrElse((airport) -> {

                airport.getWeatherBulletins().add(theNewWeatherBulletin);
                airport.getWeatherBulletins().removeIf((bulletin) -> {
                    return bulletin.getIssueDateTime().isBefore(LocalDateTime.now().minus(1, ChronoUnit.HOURS));
                });

                while (bulletinsTableModel.getRowCount() > 30) {
                    bulletinsTableModel.removeRow(bulletinsTableModel.getRowCount() - 1);
                }

                Object[] propertiesToDispay = new Object[] {
                    theNewWeatherBulletin.getIdentifier(),
                        theNewWeatherBulletin.getIssueDateTime(),
                        theNewWeatherBulletin.getType(),
                        theNewWeatherBulletin.getAirportICAO(),
                        theNewWeatherBulletin.getDuration(),
                        false,
                        null
                };

                airport.getRunways().forEach(runway -> {
                    
                });

                bulletinsTableModel.insertRow(0, propertiesToDispay);

            }, () -> {
                System.out.println("Your current configuration does not support this airport: "
                        + theNewWeatherBulletin.getAirportICAO());
            });
        });
    }

    public void addNewWeatherBulletin(final WeatherBulletin weatherBulletin) {

    }

    @Override
    public void run() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setPreferredSize(screenSize);
        this.frame.setMinimumSize(screenSize);
        this.frame.setMaximumSize(screenSize);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setResizable(false);
        // this.frame.setUndecorated(true);
        this.frame.setVisible(true);
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

}
