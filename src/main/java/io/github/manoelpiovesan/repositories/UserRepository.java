package io.github.manoelpiovesan.repositories;

import io.github.manoelpiovesan.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {


    /**
     * Find by Telegram ID
     *
     * @param telegramId Telegram ID
     * @return User
     */
    public User findByTelegramId(Long telegramId) {
        return find("telUserId", telegramId).firstResult();
    }

    /**
     * Create User
     *
     * @param user User
     * @return User
     */
    public User create(User user) {
        persist(user);
        return user;
    }
}
