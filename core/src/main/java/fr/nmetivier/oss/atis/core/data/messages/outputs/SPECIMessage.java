package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public final class SPECIMessage extends ObservationMessage {
    
    public SPECIMessage(final String content, final LocalDateTime issueDate) {
        super(content, issueDate);
    }
}
