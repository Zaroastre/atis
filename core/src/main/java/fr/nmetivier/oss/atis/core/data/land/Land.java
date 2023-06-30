package fr.nmetivier.oss.atis.core.data.land;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class Land {
    private final LandIdentifier identifier;
    private final String name;
    private final Locale locale;
    private final Set<Airport> airports;

    public Land(final LandIdentifier identifier, final String name, Set<Airport> airports, final Locale locale) {
        this.identifier = identifier;
        this.name = name;
        this.airports = Collections.unmodifiableSet(airports);
        this.locale = locale;
    }
    public LandIdentifier getIdentifier() {
        return identifier;
    }
    public String getName() {
        return name;
    }
    public Set<Airport> getAirports() {
        return airports;
    }
    public Locale getLocale() {
        return locale;
    }
}
