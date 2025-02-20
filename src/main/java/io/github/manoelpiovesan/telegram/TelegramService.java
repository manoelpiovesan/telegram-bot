package io.github.manoelpiovesan.telegram;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.telegram.model.InlineKeyboardButton;
import org.apache.camel.component.telegram.model.OutgoingTextMessage;
import org.apache.camel.component.telegram.model.ReplyKeyboardMarkup;
import org.apache.camel.component.telegram.model.ReplyMarkup;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Manoel Rodrigues
 */
@ApplicationScoped
public class TelegramService {
    @Inject
    ProducerTemplate producerTemplate;

    /**
     * Send a message to a chat
     *
     * @param chatId  Chat ID
     * @param message Message to send
     */
    public void sendMessage(String chatId, String message) {

        OutgoingTextMessage msg = new OutgoingTextMessage();
        msg.setChatId(chatId);
        msg.setText(message);

        producerTemplate.sendBody(msg);
    }

    /** !!! JUST TESTING !!!
     * Send a message with inline keyboard
     *
     * @param chatId  Chat ID
     * @param message Message to send
     */
    public void sendInlineKeyboard(String chatId, String message) {
        OutgoingTextMessage msg = new OutgoingTextMessage();
        msg.setChatId(chatId);
        msg.setText(message);

        InlineKeyboardButton buttonOptionOneI = InlineKeyboardButton.builder()
                .text("Option One - I").build();

        InlineKeyboardButton buttonOptionOneII = InlineKeyboardButton.builder()
                .text("Option One - II").build();

        InlineKeyboardButton buttonOptionTwoI = InlineKeyboardButton.builder()
                .text("Option Two - I").build();

        ReplyMarkup replyMarkup = ReplyKeyboardMarkup.builder().keyboard()
                .addRow(Arrays.asList(buttonOptionOneI, buttonOptionOneII))
                .addRow(Collections.singletonList(buttonOptionTwoI))
                .close()
                .oneTimeKeyboard(true)
                .build();

        msg.setReplyMarkup(replyMarkup);

        producerTemplate.sendBody(msg);
    }
}