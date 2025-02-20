package io.github.manoelpiovesan.telegram;

import io.github.manoelpiovesan.entities.PGMessage;
import io.github.manoelpiovesan.repositories.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class TelegramRoute extends RouteBuilder {
    @Inject
    MessageRepository msgRepository;

    @Inject
    TelegramService telegramService;

    /**
     * Configure the route
     */
    @Override
    public void configure() throws Exception {
        from("telegram:bots")
                .log("[${header.CamelTelegramChatId}]: ${body}")
                .process(this::onMessageReceived);
    }


    /**
     * Process the message received
     *
     * @param exchange Exchange
     */
    public void onMessageReceived(Exchange exchange) {

        IncomingMessage msg = exchange.getIn().getBody(IncomingMessage.class);
        PGMessage pgMsg = msgRepository.store(msg);

        if (pgMsg != null) {
            telegramService.sendMessage(msg.getChat().getId(), "Message stored! \n" + pgMsg.toString());
        }

    }


}
