package fr.nmetivier.oss.atis.core.data;

import java.util.List;

public class Runway {
    private final int qfu;
    private final RunwaySide side;
    private final List<Sensor<?>> sensors;
    public Runway(final int qfu, final RunwaySide side, final List<Sensor<?>> sensors) {
        this.qfu = qfu;
        this.side = side;
        this.sensors = sensors;
    }
    public int getQfu() {
        return qfu;
    }
    public RunwaySide getSide() {
        return side;
    }
    public String getName() {
        return String.format("%s%s/%s%s", (qfu), side.name().charAt(0), (36-qfu), side.name().charAt(0));
    }
    public List<Sensor<?>> getSensors() {
        return sensors;
    }
}
