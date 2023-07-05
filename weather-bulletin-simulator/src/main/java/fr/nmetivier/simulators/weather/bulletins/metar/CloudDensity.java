package fr.nmetivier.simulators.weather.bulletins.metar;

public enum CloudDensity {
    SKY_CLEAR("SKC"),
    FEW("FEW"),
    SCATTERED("SCT"),
    BROKEN("BKN"),
    OVERCAST("OVC"),
    NO_SIGNIFICATIVE_CLOUD("NSC"),
    UNDETERMINATE("///");

    private final String code;

    private CloudDensity(final String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

}
