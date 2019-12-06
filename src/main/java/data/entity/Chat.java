package data.entity;

import java.util.Objects;

public class Chat {

    private long id;


    public Chat(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return getId() == chat.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}