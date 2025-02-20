package io.github.manoelpiovesan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.apache.camel.component.telegram.model.IncomingMessage;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

/**
 * @author Manoel Rodrigues
 */
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at = '1970-01-01 00:00:00+00'")
public class User extends AbstractFullEntity{

    @Column(name = "tel_user_id", nullable = false)
    public Long telUserId;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "username")
    public String username;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    public List<PGChat> chats;


    public User() {
    }

    /**
     * Constructor
     * @param telUserId Telegram User ID
     * @param firstName First Name
     * @param lastName Last Name
     * @param username Username
     */
    public User(Long telUserId, String firstName, String lastName, String username) {
        this.telUserId = telUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    /**
     * Constructor from IncomingMessage
     *
     * @param msgReceived Message Received
     */
    public User(IncomingMessage msgReceived) {
        this.telUserId = msgReceived.getFrom().getId();
        this.firstName = msgReceived.getFrom().getFirstName();
        this.lastName = msgReceived.getFrom().getLastName();
        this.username = msgReceived.getFrom().getUsername();
    }


    /**
     * To String
     *
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", telUserId=" + telUserId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
