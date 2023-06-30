package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public final class TAFMessage extends PrevisionalMessage {
    
//     identificateur du bulletin;
// code de modification/correction;
// identificateur de l'aérodrome;
// date;
// période de couverture;
// information sur le vent;
// visibilité horizontale dominante;
// temps présent;
// groupe de nuages;
// groupes de changement temporaire ou permanent et
// remarques

    public TAFMessage(final String content, final LocalDateTime issueDate) {
        super(content, issueDate);
    }
}
