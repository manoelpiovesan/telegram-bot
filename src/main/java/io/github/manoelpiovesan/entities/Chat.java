package io.github.manoelpiovesan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * @author Manoel Rodrigues
 */
@Entity
@Table(name = "chats")
@SQLDelete(sql = "UPDATE chats SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at = '1970-01-01 00:00:00+00'")
public class Chat extends AbstractFullEntity {
    @Column(name = "title")
    public String title;

    @Column(name = "tel_chat_id", nullable = false)
    public Long telChatId;


    public Chat() {
    }

    /**
     * Constructor
     * @param title Chat Title
     * @param telChatId Telegram Chat ID
     */
    public Chat(String title, Long telChatId) {
        this.title = title;
        this.telChatId = telChatId;
    }
}
