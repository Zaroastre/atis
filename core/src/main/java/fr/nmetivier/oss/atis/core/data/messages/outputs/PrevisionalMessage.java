package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public abstract class PrevisionalMessage extends InformationMessage {
    
    public PrevisionalMessage(String content, final LocalDateTime issueDate) {
        super(content, issueDate);
    }
}
