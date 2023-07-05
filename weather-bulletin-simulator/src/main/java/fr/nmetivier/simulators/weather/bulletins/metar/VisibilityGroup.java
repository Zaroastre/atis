package fr.nmetivier.simulators.weather.bulletins.metar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class VisibilityGroup implements Group {
    private final int minimumHorizontalVisibility;
    private final Map<String, Integer> sectorsVisibility;
    private final List<RunwayVisibilityGroup> runwaysVisibility;

    public VisibilityGroup(final int minimumHorizontalVisibility, final Map<String, Integer> sectorsVisibility, final List<RunwayVisibilityGroup> runwaysVisibility) {
        this.minimumHorizontalVisibility = minimumHorizontalVisibility;
        this.sectorsVisibility = Collections.unmodifiableMap(sectorsVisibility);
        this.runwaysVisibility = Collections.unmodifiableList(runwaysVisibility);
    }

    public int getMinimumHorizontalVisibility() {
        return minimumHorizontalVisibility;
    }
    public List<RunwayVisibilityGroup> getRunwaysVisibility() {
        return runwaysVisibility;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.minimumHorizontalVisibility);
        this.sectorsVisibility.entrySet().forEach(entry -> {
            builder.append(" ");
            builder.append(entry.getValue());
            builder.append(entry.getKey());
        });
        this.runwaysVisibility.forEach(runwayVisibility -> {
            builder.append(" ");
            builder.append(runwayVisibility.toString());

        });
        
        return builder.toString();
    }

    public static final class Builder {
        private int north = 0;
        private int northEast = 0;
        private int northWest = 0;
        private int south = 0;
        private int southEast = 0;
        private int southWest = 0;
        private int east = 0;
        private int west = 0;
        private int minimumHorizontalVisibility = 0;
        private final List<RunwayVisibilityGroup> runwayVisibilities = new ArrayList<>();
        public final Builder minimumHorizontalVisibility(final int minimumHorizontalVisibility) {
            this.minimumHorizontalVisibility = minimumHorizontalVisibility;
            return this;
        }
        public final Builder runway(int runwayName, String side, int minimumVisibility, int maximumVisibility, Tendency tendency) {
            this.runwayVisibilities.add(new RunwayVisibilityGroup.Builder()
                    .runwayName(runwayName)
                    .side(side)
                    .minimumVisibility(minimumVisibility)
                    .maximumVisibility(maximumVisibility)
                    .tendance(tendency)
                    .build()
            );
            return this;
        }
        public final Builder runway(int runwayName, String side, int visibility, Tendency tendance) {
            return this.runway(runwayName, side, visibility, visibility, tendance);
        }
        public final Builder north(final int north) {
            this.north = north;
            return this;
        }
        public final Builder northEast(final int northEast) {
            this.northEast = northEast;
            return this;
        }
        public final Builder northWest(final int northWest) {
            this.northWest = northWest;
            return this;
        }
        public final Builder south(final int south) {
            this.south = south;
            return this;
        }
        public final Builder southEast(final int southEast) {
            this.southEast = southEast;
            return this;
        }
        public final Builder southWest(final int southWest) {
            this.southWest = southWest;
            return this;
        }
        public final Builder east(final int east) {
            this.east = east;
            return this;
        }
        public final Builder west(final int west) {
            this.west = west;
            return this;
        }
        public final VisibilityGroup build() {
            Map<String, Integer> sectorsVisibility = new HashMap<>();
            if (north > 0) {
                sectorsVisibility.put("N", north);
            }
            if (south > 0) {
                sectorsVisibility.put("S", south);
            }
            if (east > 0) {
                sectorsVisibility.put("E", east);
            }
            if (west > 0) {
                sectorsVisibility.put("W", west);
            }
            if (northEast > 0) {
                sectorsVisibility.put("NE", northEast);
            }
            if (northWest > 0) {
                sectorsVisibility.put("NW", northWest);
            }
            if (southEast > 0) {
                sectorsVisibility.put("SE", southEast);
            }
            if (southWest > 0) {
                sectorsVisibility.put("SW", southWest);
            }
            return new VisibilityGroup(minimumHorizontalVisibility, sectorsVisibility, runwayVisibilities);
        }
    }

    public static class RunwayVisibilityGroup implements Group {
        private final int runwayName;
        private final String side;
        private final int minimumVisibility;
        private final int maximumVisibility;
        private final Tendency tendance;

        public RunwayVisibilityGroup(int runwayName, String side, int minimumVisibility, int maximumVisibility, Tendency tendance) {
            this.runwayName = runwayName;
            this.side = side;
            this.minimumVisibility = minimumVisibility;
            this.maximumVisibility = maximumVisibility;
            this.tendance = tendance;
        }
        public int getMaximumVisibility() {
            return maximumVisibility;
        }
        public int getMinimumVisibility() {
            return minimumVisibility;
        }
        public int getRunwayName() {
            return runwayName;
        }
        public String getSide() {
            return side;
        }
        public Tendency getTendance() {
            return tendance;
        }
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("R");
            builder.append(this.runwayName);
            builder.append(this.side);
            builder.append("/");
            builder.append(this.minimumVisibility);
            if (this.minimumVisibility != this.maximumVisibility) {
                builder.append("V");
                builder.append(this.maximumVisibility);
            }
            builder.append(this.tendance.name().charAt(0));
            return builder.toString();
        }

        public static final class Builder {
            private int runwayName = 0;
            private String side = "C";
            private int minimumVisibility = 0;
            private int maximumVisibility = 0;
            private Tendency tendance = Tendency.NOTHING;

            public final Builder runwayName(final int runwayName) {
                this.runwayName = runwayName;
                return this;
            }
            public final Builder side(final String side) {
                this.side = side;
                return this;
            }
            public final Builder minimumVisibility(final int minimumVisibility) {
                this.minimumVisibility = minimumVisibility;
                return this;
            }
            public final Builder maximumVisibility(final int maximumVisibility) {
                this.maximumVisibility = maximumVisibility;
                return this;
            }
            public final Builder tendance(final Tendency tendance) {
                this.tendance = tendance;
                return this;
            }

            public final RunwayVisibilityGroup build() {
                return new RunwayVisibilityGroup(this.runwayName, this.side, this.minimumVisibility, this.maximumVisibility, this.tendance);
            }
        }
        
    }

    
}
