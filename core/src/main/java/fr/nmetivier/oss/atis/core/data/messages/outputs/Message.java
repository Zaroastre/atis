package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public interface Message {
    String getContent();
    LocalDateTime getIssueDate();
}
