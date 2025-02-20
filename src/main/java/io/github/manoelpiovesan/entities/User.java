package io.github.manoelpiovesan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    public List<Chat> chats;


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
}
