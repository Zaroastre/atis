package fr.nmetivier.oss.atis.program.interfaces.gui.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.Closeable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import fr.nmetivier.oss.atis.core.data.land.Airport;
import fr.nmetivier.oss.atis.core.data.land.Runway;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletin;
import fr.nmetivier.oss.atis.core.data.messages.WeatherBulletinIdentifier;
import fr.nmetivier.oss.atis.program.interfaces.gui.panels.AirportSensorsComponents;
import fr.nmetivier.oss.atis.program.interfaces.gui.panels.ClockPanel;
import fr.nmetivier.oss.atis.program.interfaces.gui.panels.ControlCenterComponent;
import fr.nmetivier.oss.atis.program.interfaces.gui.panels.BroadcastModeComponent;
import fr.nmetivier.oss.atis.stubs.ATISService;

public final class ATISWindow implements Runnable, Closeable {

    private final ATISService atisService;

    private final JFrame frame;
    private final JPanel root;

    // Logo
    private final JLabel logoLabel;
    private final JPanel logoPanel;

    // Clock
    private final ClockPanel clockPanel;

    // Mode
    private final BroadcastModeComponent modePanel;

    // Land
    private final ControlCenterComponent controlCenterPanel;

    // Sensors
    private final AirportSensorsComponents airportSensorsPanel;

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
        this.clockPanel = new ClockPanel();
        this.modePanel = new BroadcastModeComponent();
        final JPanel headerPanel = new JPanel(new BorderLayout());
        this.controlCenterPanel = new ControlCenterComponent(this.atisService.getLand());
        this.airportSensorsPanel = new AirportSensorsComponents();
        
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
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        // Setup children components
        
        
        this.controlCenterPanel.addTreeSelectionListener(event -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) controlCenterPanel.getLastSelectedPathComponent();
            if (selectedNode.isLeaf()) {
                Airport airport = (Airport) selectedNode.getPreviousNode().getUserObject();
                airportSensorsPanel.clearSensors();
                airportSensorsPanel.displaySensorsFromAirport(airport);
            } else {
                Airport airport = (Airport) selectedNode.getUserObject();
                airportSensorsPanel.clearSensors();
                airportSensorsPanel.displaySensorsFromAirport(airport);
            }
        });
        this.bulletinsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
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
        headerPanel.add(this.clockPanel, BorderLayout.WEST);
        headerPanel.add(this.modePanel.getPanel(), BorderLayout.EAST);
        this.root.add(headerPanel, BorderLayout.NORTH);
        
        leftPanel.add(this.controlCenterPanel.getPanel(), BorderLayout.NORTH);
        leftPanel.add(this.airportSensorsPanel.getPanel(), BorderLayout.SOUTH);
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
        this.setTheme();
    }

    private final void setTheme() {
        try {
            // On change le look and feel en cours
            UIManager.setLookAndFeel( "javax.swing.plaf.nimbus.NimbusLookAndFeel" );
            // On applique ce nouveau look à la fenêtre intégral 
            SwingUtilities.updateComponentTreeUI( this.frame);
        } catch( Exception exception ) { 
            exception.printStackTrace(); 
        }
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
        this.frame.setVisible(true);
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

}
