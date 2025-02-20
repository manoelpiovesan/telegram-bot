package io.github.manoelpiovesan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

/**
 * @author Manoel Rodrigues
 */
@Entity
@Table(name = "chats")
@SQLDelete(sql = "UPDATE chats SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at = '1970-01-01 00:00:00+00'")
public class PGChat extends AbstractFullEntity {
    @Column(name = "title")
    public String title;

    @Column(name = "tel_chat_id", nullable = false)
    public String telChatId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @JsonIgnore
    @OneToMany(mappedBy = "chat")
    public List<PGMessage> messages;


    public PGChat() {
    }

    /**
     * Constructor
     *
     * @param title     Chat Title
     * @param telChatId Telegram Chat ID
     */
    public PGChat(String title, String telChatId) {
        this.title = title;
        this.telChatId = telChatId;
    }

    /**
     * Constructor from IncomingMessage
     *
     * @param msgReceived Message Received
     */
    public PGChat(IncomingMessage msgReceived) {
        this.title = msgReceived.getChat().getTitle();
        this.telChatId = msgReceived.getChat().getId();
    }

    /**
     * To String
     *
     */
    @Override
    public String toString() {
        return "PGChat{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", telChatId='" + telChatId + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
