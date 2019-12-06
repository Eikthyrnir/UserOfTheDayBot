import logic.UserOfTheDayBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        String botName = "MyPidorDayJavaTestBot";
        String botToken = "679704139:AAH2skBefNITyGxXgjoKfwuYrpIhnA2daMo";

        TelegramLongPollingBot bot = new UserOfTheDayBot(botName, botToken);

        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            log.error("some unpredictable problems has occurred during bot registration: "
                    + e.getMessage());
        }
    }

}