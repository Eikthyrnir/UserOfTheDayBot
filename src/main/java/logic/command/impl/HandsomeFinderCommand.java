package logic.command.impl;

import data.entity.Chat;
import data.entity.Player;
import data.service.ChatService;
import data.service.PlayerService;
import data.service.StatisticsService;
import logic.Sender;
import logic.command.api.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class HandsomeFinderCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(HandsomeFinderCommand.class);


    private static final String GAME_NAME = "красавчик";
    private static final int MESSAGE_DELAY = 1500;

    private static final Lock lock = new ReentrantLock();
    private static Set<Chat> processedChats = new HashSet<>();

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


    private void baseImpl() {
        ChatService chatService = new ChatService();
        Chat chat = chatService.getChat(message.getChatId());

        if(!tryLock(chat)) {
            return;
        }

        Sender sender = new Sender(bot);
        try (PlayerService playerService = new PlayerService();
             StatisticsService statisticsService = new StatisticsService()
        ) {
            List<Player> players = playerService.fetchPlayersFromChat(chat);

            if(players.isEmpty()) {
                sender.send("Нет игроков", chat);
                return;
            }

            LocalDate today = LocalDate.now();
            LocalDate lastGameDate = statisticsService.fetchLastGameDate(chat, GAME_NAME);
            //false if lastGameDate == null
            if(today.equals(lastGameDate)) {
                sender.send("Красавчик дня - " + statisticsService.fetchLastWinner(chat, GAME_NAME).getName(), chat);
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
            log.error(e.getMessage(), e);
        } finally {
            unlock(chat);
        }
    }

    //TODO write nad compare with baseImpl()
    public void pureJdbcImpl() {
        try {
            long chatId = message.getChatId();



        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    private boolean tryLock(Chat chat) {
        try {
            lock.lock();

            if (processedChats.contains(chat)) {
                log.debug("already running in chat №" + chat.getId());

                return false;
            }
            processedChats.add(chat);

            log.debug("chat №" + chat.getId() + " locked");
        } finally {
            lock.unlock();
        }
        return true;
    }


    private void unlock(Chat chat) {
        lock.lock();
        processedChats.remove(chat);

        log.debug("chat №" + chat.getId() + " unlocked");

        lock.unlock();
    }
}