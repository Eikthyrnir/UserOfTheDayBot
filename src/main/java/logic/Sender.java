package logic;

import data.entity.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Sender {

    private static final Logger log = LoggerFactory.getLogger(Sender.class);

    private TelegramLongPollingBot bot;

    public Sender(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public void send(String message, Chat chat) {
        sendMsg(message, chat);
    }


    /**
     * Метод для настройки сообщения и его отправки.
     *
     * @param chat   чат
     * @param msg    Строка, которую необходимот отправить в качестве сообщения.
     */
    private synchronized void sendMsg(String msg, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(msg);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("message sending failed: " + e.getMessage(), e);
        }
    }
}