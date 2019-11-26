package data.service;

import data.DataAccessException;
import data.ConnectionPool;
import data.entity.Chat;
import data.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class StatisticsService implements AutoCloseable{

    Logger logger = LoggerFactory.getLogger(StatisticsService.class);
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    //default ctor

    public Player getLastWinner(Chat chat, String game) {
        String query =
                "select p.id, p.telegram_id, p.chat_id, p.name " +
                        "    from  " +
                        "         statistics AS s " +
                        "        inner join " +
                        "         players AS p " +
                        "        on s.winner_id = p.id " +
                        "    where " +
                        "        p.chat_id = " + chat.getId() +
                        "        and " +
                        "        s.game_id =  " +
                        "            (select g.id  " +
                        "                from games AS g " +
                        "                where g.name = \"" + game + "\") " +
                        "    order by s.date desc " +
                        "    limit 1;";
        try (Statement st =
                     connectionPool.getConnection().createStatement()
        ) {
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                return new Player(
                        rs.getLong("id"),
                        rs.getLong("telegram_id"),
                        rs.getLong("chat_id"),
                        rs.getString("name")
                        );
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException("");
        }
        return null;
    }

    public void addWinner(Player player, String game, LocalDate date) {
        String query =
                "insert into statistics " +
                "   (game_id, winner_id)" +
                "   select id, " + player.getId() +
                "       from games AS g" +
                "       where g.name = \"" + game + "\"";
        try (Statement st =
                     connectionPool.getConnection().createStatement()
        ) {
            st.executeUpdate(query);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataAccessException("");
        }
    }

    public LocalDate getLastGameDate(Chat chat, String game) {
        String query =
                        "select s.date " +
                        "    from  " +
                        "         statistics AS s " +
                        "        inner join " +
                        "         players AS p " +
                        "        on s.winner_id = p.id " +
                        "    where " +
                        "        p.chat_id = " + chat.getId() +
                        "        and " +
                        "        s.game_id =  " +
                        "            (select g.id  " +
                        "                from games AS g " +
                        "                where g.name = \"" + game + "\") " +
                        "    order by s.date desc " +
                        "    limit 1;";
        try (Statement st =
                     connectionPool.getConnection().createStatement()
        ) {
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                return rs.getDate("date")
                        .toLocalDate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException("");
        }
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