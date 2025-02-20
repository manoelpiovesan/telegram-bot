package io.github.manoelpiovesan.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * @author Manoel Rodrigues
 */
@Entity
@Table(name = "messages")
@SQLDelete(sql = "UPDATE messages SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at = '1970-01-01 00:00:00+00'")
public class Message extends AbstractFullEntity {

    @Column(name = "chat_id", nullable = false)
    public String chatId;

    @Column(name = "text")
    public String text;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    public Message() {
    }

    /**
     * Constructor
     *
     * @param chatId Chat ID
     * @param text   Message Text
     * @param user   User
     */
    public Message(String chatId, String text, User user) {
        this.chatId = chatId;
        this.text = text;
        this.user = user;
    }


    /**
     * Constructor from IncomingMessage
     *
     * @param msgReceived Message Received
     * @param user User
     */
    public Message(IncomingMessage msgReceived, User user) {
        this.chatId = msgReceived.getChat().getId();
        this.text = msgReceived.getText();
        this.user = user;
    }

}
