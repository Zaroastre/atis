package fr.nmetivier.oss.atis.core.data;

import java.util.List;

public class Land {
    private final String name;
    private final List<Airport> airports;
    
    public Land(final String name, final List<Airport> airports) {
        this.name = name;
        this.airports = airports;
    }
    public List<Airport> getAirports() {
        return airports;
    }
    public String getName() {
        return name;
    }
}
