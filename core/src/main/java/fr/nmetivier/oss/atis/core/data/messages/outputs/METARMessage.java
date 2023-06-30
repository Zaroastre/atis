package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public final class METARMessage extends ObservationMessage {

//     code de modification;

// identificateur METAR/SPECI/LWIS;
// identificateur de la station;
// date;
// information sur le vent;
// visibilité horizontale dominante;
// temps présent;
// groupe nuages;
// température / point de rosée;
// calage altimétrique et
// remarques


    public METARMessage(final String content, final LocalDateTime issueDate) {
        super(content, issueDate);
    }
    
}
