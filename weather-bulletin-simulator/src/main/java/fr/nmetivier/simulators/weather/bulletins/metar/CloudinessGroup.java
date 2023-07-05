package fr.nmetivier.simulators.weather.bulletins.metar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class CloudinessGroup implements Group {

    private final Map<Integer, CloudDensity> cloudsSteps;
    public CloudinessGroup(final Map<Integer, CloudDensity> cloudsSteps) {
        this.cloudsSteps = Collections.unmodifiableMap(cloudsSteps);
    }
    
    public Map<Integer, CloudDensity> getCloudsSteps() {
        return cloudsSteps;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (cloudsSteps.isEmpty()) {
            builder.append(CloudDensity.NO_SIGNIFICATIVE_CLOUD.getCode());
        } else {
            cloudsSteps.entrySet().forEach(entry -> {
                builder.append(" ");
                if (entry.getValue() == CloudDensity.UNDETERMINATE) {
                    if (entry.getKey() <= 0) {
                        builder.append(entry.getValue().getCode());
                    }
                } else {
                    builder.append(entry.getValue().getCode());
                    if (entry.getKey() <= 0) {
                        builder.append("///");
                    } else {
                        builder.append(entry.getKey());
                    }
                }
            });
        }
        return builder.toString();
    }

    public static final class Builder {
        private final Map<Integer, CloudDensity> cloudsSteps = new HashMap<>();

        public Builder cloudCover(final int feet, CloudDensity density, String cloudType) {
            this.cloudsSteps.put(feet, density);
            return this;
        }
        public Builder cloudCover(final int feet, CloudDensity density) {
            return this.cloudCover(feet, density, null);
        }

        public Builder noSignificativeCloud() {
            this.cloudsSteps.clear();
            return this;
        }

        public final CloudinessGroup build() {
            return new CloudinessGroup(this.cloudsSteps);
        }
    } 
}
