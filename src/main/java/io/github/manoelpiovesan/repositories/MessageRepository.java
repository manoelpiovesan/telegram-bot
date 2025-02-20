package io.github.manoelpiovesan.repositories;

import io.github.manoelpiovesan.entities.Chat;
import io.github.manoelpiovesan.entities.Message;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class MessageRepository implements PanacheRepository<Message> {

    @Inject
    ChatRepository chatRepository;

    /**
     * Create
     *
     * @param chatId  Chat ID
     * @param message Message
     */
    @Transactional
    public void create(Message message, String chatId) {


    }

}
