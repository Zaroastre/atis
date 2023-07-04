package fr.nmetivier.simulators.weather.bulletins.metar;

import java.util.Collections;
import java.util.List;

import fr.nmetivier.simulators.weather.bulletins.Group;

public class VisibilityGroup implements Group {
    private final int minimumHorizontalVisibility;
    private final List<RunwayVisibilityGroup> runwaysVisibility;
    public VisibilityGroup(final int minimumHorizontalVisibility, final List<RunwayVisibilityGroup> runwaysVisibility) {
        this.minimumHorizontalVisibility = minimumHorizontalVisibility;
        this.runwaysVisibility = Collections.unmodifiableList(runwaysVisibility);
    }

    public int getMinimumHorizontalVisibility() {
        return minimumHorizontalVisibility;
    }
    public List<RunwayVisibilityGroup> getRunwaysVisibility() {
        return runwaysVisibility;
    }

    public class RunwayVisibilityGroup implements Group {

    }
}
