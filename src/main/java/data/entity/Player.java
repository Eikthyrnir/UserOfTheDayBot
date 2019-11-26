package data.entity;

public class Player {

    private long id;
    private long telegramId;
    private long chatId;
    private String name;


    public Player(long id, long telegramId, long chatId, String name) {
        this.id = id;
        this.telegramId = telegramId;
        this.chatId = chatId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public long getChatId() {
        return chatId;
    }

    public String getName() {
        return name;
    }

}
