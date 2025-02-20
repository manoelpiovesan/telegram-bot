package io.github.manoelpiovesan.telegram;

import io.github.manoelpiovesan.repositories.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.telegram.model.IncomingMessage;

import java.time.Instant;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class TelegramRoute extends RouteBuilder {
    @Inject
    MessageRepository messageRepository;

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
        String message = exchange.getIn().getBody(String.class);
        Long chatId = exchange.getIn().getHeader("CamelTelegramChatId", Long.class);

        IncomingMessage msg = exchange.getIn().getBody(IncomingMessage.class);

        System.out.println("Message received: " + msg.toString() + " - " + chatId);

        msg.getDate();
        Instant timestamp = msg.getDate();

    }


}
