package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public final class SIGMETMessage extends InformationMessage {
    
    public SIGMETMessage(final String content, final LocalDateTime issueDate) {
        super(content, issueDate);
    }
}
