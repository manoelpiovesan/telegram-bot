package io.github.manoelpiovesan.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

/**
 * @author Manoel Rodrigues
 */
@Entity
@Table(name = "messages")
@SQLDelete(sql = "UPDATE messages SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at = '1970-01-01 00:00:00+00'")
public class PGMessage extends AbstractFullEntity {

    // Chat<->Message
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    public PGChat chat;

    @Column(name = "text")
    public String text;

    @Column(name = "date")
    public Date date;

    public PGMessage() {
    }

    /**
     * Constructor from IncomingMessage
     *
     * @param msgReceived Message Received
     */
    public PGMessage(IncomingMessage msgReceived) {
        this.text = msgReceived.getText();
        this.date = Date.from(msgReceived.getDate());
    }


    /**
     * To String
     *
     */
    @Override
    public String toString() {
        return "PGMessage{" +
                "id=" + id +
                ", chat=" + chat.toString() +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }

}
