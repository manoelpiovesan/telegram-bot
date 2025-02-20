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

        IncomingMessage msg = exchange.getIn().getBody(IncomingMessage.class);

        if (msg.getText().startsWith("/")) {
            handleCommand(msg);
        } else {
            telegramService.sendMessage(msg.getChat().getId(), "What?");
        }

    }

    /**
     * Command handler
     */
    public void handleCommand(IncomingMessage message) {
        String command = message.getText().split(" ")[0];
        message.setText(message.getText().replace(command, "").trim());
        switch (command) {
            case "/store":
                if(message.getText().isEmpty()){
                    telegramService.sendMessage(message.getChat().getId(), "Message cannot be empty.");
                    return;
                }
                PGMessage pgMsg = messageRepository.store(message);
                telegramService.sendMessage(message.getChat().getId(), "Message \"" + pgMsg.text + "\" stored.");

                break;
            case "/start":
                telegramService.sendMessage(message.getChat().getId(), "Welcome to the bot!");
                break;
            case "/help":
                telegramService.sendMessage(message.getChat().getId(), "Help message");
                break;
            default:
                telegramService.sendMessage(message.getChat().getId(), "Command not found");
        }
    }


}
