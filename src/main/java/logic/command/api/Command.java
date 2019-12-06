package logic.command.api;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    void execute();

    default void setBot(TelegramLongPollingBot bot) {
        //do nothing
    }

    default void setMessage(Message message) {
        //do nothing
    }

    default void setArgument(String arg) {
    	//do nothing
    }

}