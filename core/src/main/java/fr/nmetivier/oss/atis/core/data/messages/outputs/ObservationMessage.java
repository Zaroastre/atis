package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public abstract class ObservationMessage extends InformationMessage {

    public ObservationMessage(String content, final LocalDateTime issueDate) {
        super(content, issueDate);
    }
    
}
