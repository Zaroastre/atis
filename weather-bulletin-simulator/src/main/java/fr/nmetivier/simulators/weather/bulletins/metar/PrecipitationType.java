package fr.nmetivier.simulators.weather.bulletins.metar;

public enum PrecipitationType {
    DRIZZLE("DZ"),
    RAIN("RA"),
    SNOW("SN"),
    SLEET("GS"),
    HAIL("GR"),
    GRAIN_SNOW("SG"),
    ROLLED_SNOW("GS"),
    ICE_PELLET("PL");

    private final String code;
    private PrecipitationType(final String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
