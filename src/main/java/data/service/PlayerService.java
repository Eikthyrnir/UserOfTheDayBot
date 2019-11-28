package data.service;

import data.DataAccessException;
import data.ConnectionPool;
import data.entity.Chat;
import data.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PlayerService implements AutoCloseable {

    private Logger logger = LoggerFactory.getLogger(PlayerService.class);
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    //default ctor

    public List<Player> fetchPlayersFromChat(Chat chat) {
        String query =
                "SELECT id, telegram_id, chat_id, name FROM players " +
                "WHERE chat_id = " + chat.getId() +
                "  AND status = 1";
        try(Statement st =
                    connectionPool.getConnection().createStatement()
        ) {
            List<Player> players = new ArrayList<>();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                players.add(new Player(
                   rs.getLong("id"),
                   rs.getLong("telegram_id"),
                   rs.getLong("chat_id"),
                   rs.getString("name")
                ));
            }

            return players;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException("");
        }
    }

    public void addPlayer(long telegramId, long chatId, String name) {
        String query =
                "INSERT IGNORE INTO players " +
                        "( telegram_id, name, chat_id ) "+
                        "VALUES " +
                        "( " +
                        telegramId + "," +
                        "\"" + name + "\"" + "," +
                        chatId
                        + " )";
        try (Statement st =
                     connectionPool.getConnection().createStatement()
        ) {
            st.executeUpdate(query);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException("");
        }
    }

    public void deletePlayer(Player player) {

    }

    public Player fetchRandPlayer(Chat chat) {
        //select 1 rand player from
        String query =
                "SELECT p.name " +
                        "FROM players AS p" +
                        "WHERE p.chatId = " + chat.getId() +
                        "  AND p.status = 1" +
                        "ORDER BY RAND()" +
                        "LIMIT 1";

        return null;
    }


    @Override
    public void close() {
        try {
            connectionPool.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException("");
        }
    }
}