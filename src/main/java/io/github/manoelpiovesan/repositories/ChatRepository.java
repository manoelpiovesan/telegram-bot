package io.github.manoelpiovesan.repositories;

import io.github.manoelpiovesan.entities.Chat;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class ChatRepository implements PanacheRepository<Chat> {
}
