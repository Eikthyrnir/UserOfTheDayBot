package data.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId() == player.getId() &&
                getTelegramId() == player.getTelegramId() &&
                getChatId() == player.getChatId() &&
                getName().equals(player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTelegramId(), getChatId(), getName());
    }
}