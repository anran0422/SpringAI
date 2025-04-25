package com.anran.springAI.model.VO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class MessageVO implements Serializable {

    private String role;

    private String content;

    public MessageVO(Message message) {
        switch (message.getMessageType()) {
            case USER:
                role = "user";
                break;
            case ASSISTANT:
                role = "assistant";
                break;
            default:
                role = "";
                break;
        }
        this.content = message.getText();
    }
}
