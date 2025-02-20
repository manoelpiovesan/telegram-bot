package io.github.manoelpiovesan.resources;

import io.github.manoelpiovesan.repositories.MessageRepository;
import io.github.manoelpiovesan.telegram.TelegramService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Manoel Rodrigues
 */
@Path("/t")
public class TelegramMessageResource {


    @Inject
    TelegramService telegramSender;

    @Inject
    MessageRepository messageRepository;

    /**
     * Send a message to a chat
     *
     * @param message Message to send
     */
    @POST
    @Path("/chat/{chatId}")
    @Consumes("application/json")
    public Response sendMessage(@PathParam("chatId") String chatId, String message) {
        try {
            telegramSender.sendMessage(chatId, message);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

    /**
     * List all messages
     *
     * @return List of messages
     */
    @GET
    @Path("/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        return Response.ok(messageRepository.listAll()).build();
    }

    /**
     * Get messages by chat ID
     *
     * @param chatId Chat ID
     * @return Message
     */
    @GET
    @Path("/chat/{chatId}")
    public Response getByChatId(@PathParam("chatId") String chatId) {
        return Response.ok(messageRepository.findByChatId(chatId)).build();
    }
}
