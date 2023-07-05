package fr.nmetivier.simulators.weather.bulletins.metar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class CurrentWeatherGroup implements Group {
    private final List<PrecipitationType> precipitations;

    public CurrentWeatherGroup(final List<PrecipitationType> precipitations) {
        this.precipitations = Collections.unmodifiableList(precipitations);
    }

    public List<PrecipitationType> getPrecipitations() {
        return precipitations;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        precipitations.forEach(preicpitation -> {
            builder.append(preicpitation.getCode());
        });
        return builder.toString();
    }

    public static final class Builder {
        private boolean drizzle = false;
        private boolean rain = false;
        private boolean snow = false;
        private boolean sleet = false;
        private boolean hail = false;
        private boolean grainSnow = false;
        private boolean rolledSnow = false;
        private boolean icePellet = false;
        private boolean cavok = false;

        public final Builder drizzle(final boolean drizzle) {
            this.drizzle = drizzle;
            return this;
        }
        
        public final Builder rain(final boolean rain) {
            this.rain = rain;
            return this;
        }
        
        public final Builder snow(final boolean snow) {
            this.snow = snow;
            return this;
        }
        
        public final Builder sleet(final boolean sleet) {
            this.sleet = sleet;
            return this;
        }
        
        public final Builder hail(final boolean hail) {
            this.hail = hail;
            return this;
        }
        
        public final Builder grainSnow(final boolean grainSnow) {
            this.grainSnow = grainSnow;
            return this;
        }
        
        public final Builder rolledSnow(final boolean rolledSnow) {
            this.rolledSnow = rolledSnow;
            return this;
        }
        
        public final Builder icePellet(final boolean icePellet) {
            this.icePellet = icePellet;
            return this;
        }
        public final Builder cavOK(final boolean cavok) {
            this.cavok = cavok;
            return this;
        }
        public final CurrentWeatherGroup build() {
            List<PrecipitationType> precipitationTypes = new ArrayList<>();
            if (this.drizzle) {
                precipitationTypes.add(PrecipitationType.DRIZZLE);
            }
            if (this.grainSnow) {
                precipitationTypes.add(PrecipitationType.GRAIN_SNOW);
            }
            if (this.hail) {
                precipitationTypes.add(PrecipitationType.HAIL);
            }
            if (this.icePellet) {
                precipitationTypes.add(PrecipitationType.ICE_PELLET);
            }
            if (this.rain) {
                precipitationTypes.add(PrecipitationType.RAIN);
            }
            if (this.rolledSnow) {
                precipitationTypes.add(PrecipitationType.ROLLED_SNOW);
            }
            if (this.sleet) {
                precipitationTypes.add(PrecipitationType.SLEET);
            }
            if (this.snow) {
                precipitationTypes.add(PrecipitationType.SNOW);
            }
            return new CurrentWeatherGroup(precipitationTypes);
        }
    }
}
