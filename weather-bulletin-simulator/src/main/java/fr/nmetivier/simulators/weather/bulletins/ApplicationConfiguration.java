package fr.nmetivier.simulators.weather.bulletins;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public final class ApplicationConfiguration {
    private final String host;
    private final int port;
    private final int intervalInMilliseconds;

    private ApplicationConfiguration(final String host, final int port, final int intervalInMilliseconds) {
        this.host = host;
        this.port = port;
        this.intervalInMilliseconds = intervalInMilliseconds;
    }

    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }
    public int getIntervalInMilliseconds() {
        return intervalInMilliseconds;
    }
    
    @Override
    public String toString() {
        return "ApplicationConfiguration [host=" + host + ", port=" + port + ", intervalInMilliseconds="
                + intervalInMilliseconds + "]";
    }

    public static final class Builder {
        private InputStream inputStream = null;
        private ResourceBundle bundle = null;

        public final Builder resource(final String resource) {
            this.bundle = ResourceBundle.getBundle(resource);
            return this;
        }

        public final Builder file(final File file) {
            if (file.exists() && file.isFile()) {
                try {
                    this.inputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return this;
        } 

        public final ApplicationConfiguration build() {
            final Properties properties = new Properties();        
            String host = "127.0.0.1";
            int port = 9_000;
            int intervalInMilliseconds = 1_000;
            if (this.inputStream != null) {
                boolean isLoaded = false;
                try {
                    properties.load(this.inputStream);
                    isLoaded = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    isLoaded = false;
                }
                if (isLoaded) {
                    host = properties.getProperty("host");
                    port = Integer.parseInt(properties.getProperty("port"));
                    intervalInMilliseconds = Integer.parseInt(properties.getProperty("scheduler.intervalInMilliseconds"));
                }
            } else if (this.bundle != null) {
                host = this.bundle.getString("host");
                port = Integer.parseInt(this.bundle.getString("port"));
                intervalInMilliseconds = Integer.parseInt(this.bundle.getString("scheduler.intervalInMilliseconds"));
            }
            return new ApplicationConfiguration(host, port, intervalInMilliseconds);
        }
    }
    
}
