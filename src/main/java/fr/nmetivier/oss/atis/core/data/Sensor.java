package fr.nmetivier.oss.atis.core.data;

import java.util.Optional;

public interface Sensor<T> {
    String getName();
    Optional<T> getValue();
    
}
