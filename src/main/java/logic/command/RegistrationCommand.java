package logic.command;

import data.service.ChatService;
import data.service.PlayerService;
import logic.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RegistrationCommand implements Command {

    private Logger logger = LoggerFactory.getLogger(RegistrationCommand.class);

    private TelegramLongPollingBot bot;
    private Message message;
    private String regName;


    @Override
    public void execute() {
        ChatService chatService = new ChatService();
        Sender sender = new Sender(bot);
        try (PlayerService playerService = new PlayerService()) {
            long chatId = message.getChatId();
            long id = message.getFrom().getId();
            String name = chooseName();

            playerService.addPlayer(id, chatId, name);
            sender.send(name + ", ты в игре!", chatService.getChat(chatId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void setBot(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void setArgument(String arg) {
        this.regName = arg;
    }

    //TODO
    private String chooseName() {
        if (!regName.isEmpty()) {
           return regName;
        }
        String firstName = message.getFrom().getFirstName();
        if (firstName != null) {
            return firstName;
        }
        return message.getFrom().getUserName();
    }
}
