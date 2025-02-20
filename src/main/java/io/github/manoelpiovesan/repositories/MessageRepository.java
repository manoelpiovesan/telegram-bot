package io.github.manoelpiovesan.repositories;

import io.github.manoelpiovesan.entities.PGChat;
import io.github.manoelpiovesan.entities.PGMessage;
import io.github.manoelpiovesan.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.camel.component.telegram.model.IncomingMessage;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class MessageRepository implements PanacheRepository<PGMessage> {

    @Inject
    ChatRepository chatRepository;

    @Inject
    UserRepository userRepository;

    /**
     * Create
     *
     * @param incomingMessage Message
     */
    @Transactional
    public PGMessage store(IncomingMessage incomingMessage) {

        String chatId = incomingMessage.getChat().getId();
        Long telUserId = incomingMessage.getFrom().getId();

        // Registering user if not exists
        if (userRepository.findByTelegramId(telUserId) == null) {
            userRepository.create(new User(incomingMessage));
        }

        PGChat chat = chatRepository.findByTelegramId(chatId);
        // Registering chat if not exists
        if (chat == null) {
            chat = chatRepository.create(new PGChat(incomingMessage), telUserId);
        }

        // Registering message
        PGMessage pgMessage = new PGMessage(incomingMessage);

        pgMessage.chat = chat;
        pgMessage.persist();

        return pgMessage;
    }

}
