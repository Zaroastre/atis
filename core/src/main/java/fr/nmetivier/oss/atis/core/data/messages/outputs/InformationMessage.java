package fr.nmetivier.oss.atis.core.data.messages.outputs;

import java.time.LocalDateTime;

public class InformationMessage implements Message {
    protected final String content;
    protected final LocalDateTime issueDate;
    
    public InformationMessage(final String content, final LocalDateTime issueDate) {
        this.content = content;
        this.issueDate = issueDate;
    }

    @Override
    public final String getContent() {
        return this.content;
    }
    @Override
    public final LocalDateTime getIssueDate() {
        return this.issueDate;
    }
}
