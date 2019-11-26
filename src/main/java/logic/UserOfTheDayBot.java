package logic;

import logic.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserOfTheDayBot extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(UserOfTheDayBot.class);

    private final String name;
    private final String token;
    private final CommandFactory factory;

    public UserOfTheDayBot(String name, String token) {
        this.name = name;
        this.token = token;
        this.factory = new CommandFactory(this);
    }


    /**
     * Метод для приема сообщений.
     *
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            factory.createCommand(update.getMessage())
                    .execute();
        } catch (Exception e) {
            //TODO
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return name;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return token;
    }

}