package fr.nmetivier.simulators.weather.bulletins.metar;

import java.time.LocalDateTime;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class IdentificationGroup implements Group {

    private final String messageType;
    private final String icao;
    private final LocalDateTime date;
    private final boolean isAutomatic;
    
    
    public IdentificationGroup(String messageType, String icao, LocalDateTime date,  boolean isAutomatic) {
        this.messageType = messageType;
        this.icao = icao;
        this.isAutomatic = isAutomatic;
        this.date = date;
    }
    
    public String getMessageType() {
        return messageType;
    }

    public String getIcao() {
        return icao;
    }

    public boolean isAutomatic() {
        return this.isAutomatic;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        StringBuilder dateTimeValue = new StringBuilder();
        if (String.valueOf(this.date.getDayOfMonth()).length() == 1) {
            dateTimeValue.append("0");
        }
        dateTimeValue.append(this.date.getDayOfMonth());
        if (String.valueOf(this.date.getHour()).length() == 1) {
            dateTimeValue.append("0");
        }
        dateTimeValue.append(this.date.getHour());
        if (String.valueOf(this.date.getMinute()).length() == 1) {
            dateTimeValue.append("0");
        }
        dateTimeValue.append(this.date.getMinute());
        dateTimeValue.append("Z");
        return String.format(
            "%s %S %S %s",
            this.messageType,
            this.icao,
            dateTimeValue.toString(),
            (isAutomatic) ? "AUTO" : ""
            );
    }

    public static final class Builder {
        private final String messageType = "METAR";
        private String icao = null;
        private LocalDateTime date = LocalDateTime.now();
        private boolean isAutomatic = true;
        
        public Builder icao(String icao) {
            this.icao = icao;
            return this;
        }

        public Builder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder automatic(boolean isAutomatic) {
            this.isAutomatic = isAutomatic;
            return this;
        }

        public IdentificationGroup build() {
            return new IdentificationGroup(messageType, icao, date, isAutomatic);
        }
    }
}
