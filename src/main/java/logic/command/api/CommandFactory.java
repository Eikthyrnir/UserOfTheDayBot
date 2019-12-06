package logic.command.api;

import logic.command.impl.HandsomeFinderCommand;
import logic.command.impl.RegistrationCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);


    //an empty string cannot indicate any command
    private static final String NO_COMMAND  = "";
    private static final String NO_ARGUMENT = "";

    private TelegramLongPollingBot bot;

    //dependency injection?
    private Map<String, Class<?>> commandTexts = new HashMap<>(){
        {
            put("run", HandsomeFinderCommand.class);
            put("reg", RegistrationCommand.class);
        }
    };


    public CommandFactory(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    /**
     * trying to find a command by message text
     * @param message - message with command
     * @return appropriate command or empty Command, that do nothing
     */
    public Command createCommand(Message message) {
        try {
            CommandInfo commandInfo = parse(message.getText());

            Command command = selectCommand(commandInfo.name);
            command.setBot(bot);
            command.setMessage(message);
            command.setArgument(commandInfo.argument);

            return command;

        } catch (Exception e) {
            log.error(e.getMessage());

            return new DoNothingCommand();
        }
    }


    /*
     * all bot commands must be:
     * /name args or /name@botUsername args
     */
    private CommandInfo parse(String message) {
        message = message.trim();
        String[] parts = message.split("\\s",2);
        String commandName = parts[0];

        if (!commandName.startsWith("/")) {
            return new CommandInfo(NO_COMMAND, NO_ARGUMENT);
        }
        commandName = commandName.replace("@" + bot.getBotUsername(), "");

        String arg = "";
        if(parts.length == 2) {
            arg = parts[1].trim();
        }

        return new CommandInfo(commandName.substring(1), arg);
    }

    //TODO rewrite throws
    private Command selectCommand(String commandName)
                throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> commandClass = commandTexts.get(commandName);

        if (commandClass == null) {
            //no such command
            return new DoNothingCommand();
        }

        return (Command) commandClass
                .getConstructor()
                .newInstance();
    }
}