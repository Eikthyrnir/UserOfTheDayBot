package logic.command;

import data.entity.Chat;
import data.entity.Player;
import data.service.ChatService;
import data.service.PlayerService;
import data.service.StatisticsService;
import logic.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class HandsomeFinderCommand implements Command {

    private Logger logger = LoggerFactory.getLogger(HandsomeFinderCommand.class);

    private static final String GAME_NAME = "красавчик";
    private static final int MESSAGE_DELAY = 1500;

    private TelegramLongPollingBot bot;
    private Message message;

    private String[] handsomeFindMessages = {
        "ВНИМАНИЕ \uD83D\uDD25",
                "Ищем красавчика в этом чате",
                "Гадаем на бинарных опционах \uD83D\uDCCA",
                "Анализируем лунный гороскоп \uD83C\uDF16",
                "Лунная призма дай мне силу \uD83D\uDCAB",
                "СЕКТОР ПРИЗ НА БАРАБАНЕ \uD83C\uDFAF"
    };


    @Override
    public void setBot(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute() {
        baseImpl();
    }

    //TODO is it possible to rewrite with 2 threads (1 for DB and 1 for spamming messages)?
    private void baseImpl() {
        Sender sender = new Sender(bot);
        ChatService chatService = new ChatService();
        try (PlayerService playerService = new PlayerService();
             StatisticsService statisticsService = new StatisticsService()) {
            Chat chat = chatService.getChat(message.getChatId());
            List<Player> players = playerService.getPlayersFromChat(chat);

            if(players.isEmpty()) {
                sender.send("Нет игроков", chat);
                return;
            }

            LocalDate today = LocalDate.now();
            LocalDate lastGameDate = statisticsService.getLastGameDate(chat, GAME_NAME);
            //false if lastGameDate == null
            if(today.equals(lastGameDate)) {
                sender.send("Красавчик дня - " + statisticsService.getLastWinner(chat, GAME_NAME).getName(), chat);
                return;
            }

            Player winner = players.get(ThreadLocalRandom.current().nextInt(0, players.size()));

            statisticsService.addWinner(winner, GAME_NAME, today);

            for(String handsomeFindMessage : handsomeFindMessages) {
                sender.send(handsomeFindMessage, chat);
                Thread.sleep(MESSAGE_DELAY);
            }
            sender.send("\uD83C\uDF89 Сегодня красавчик дня - " + winner.getName(), chat);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    public void pureJdbcImpl() {
        try {
            long chatId = message.getChatId();



        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
