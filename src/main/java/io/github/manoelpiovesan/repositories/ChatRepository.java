package io.github.manoelpiovesan.repositories;

import io.github.manoelpiovesan.entities.PGChat;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class ChatRepository implements PanacheRepository<PGChat> {

    @Inject
    UserRepository userRepository;


    /**
     * Find by Telegram Chat ID
     *
     * @param telegramId Telegram Chat ID
     * @return Chat
     */
    public PGChat findByTelegramId(String telegramId) {
        return find("telChatId", telegramId).firstResult();
    }

    /**
     * Create Chat
     *
     * @param chat Chat
     * @return Chat
     */
    public PGChat create(PGChat chat, Long telUserId) {
        chat.user = userRepository.findByTelegramId(telUserId);
        persist(chat);
        return chat;
    }
}
