package data.service;

import data.entity.Chat;

public class ChatService{

    public Chat getChat(long chatId) {
        return new Chat(chatId);
    }

}
