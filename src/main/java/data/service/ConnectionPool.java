package data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;

enum ConnectionPool {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    private static final String url = "jdbc:mysql://localhost:3306/userofthedaybot";
    private static final String login = "root";
    private static final String password = "root";

    private Connection connection = null;
    private int currentUsedConnections = 0;

    //TODO ??????
    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }


    public static ConnectionPool getInstance() {
        INSTANCE.currentUsedConnections++;
        return INSTANCE;
    }


    public synchronized void close() throws SQLException {
        currentUsedConnections--;
        if(currentUsedConnections == 0) {
            try {
                connection.close();
            } finally {
                connection = null;
            }
        }
    }


    public Connection getConnection() {
        createConnection();
        return connection;
    }


    private synchronized void createConnection() {
        if(connection == null) {
            Properties properties = new Properties();
            properties.put("User", login);
            properties.put("password", password);
            properties.put("autoReconnect", "true");
            properties.put("characterUnicode", "true");
            properties.put("useUnicode", "true");
            properties.put("useSSL", "false");
            properties.put("useLegacyDatetimeCode", "false");
            properties.put("serverTimezone", "UTC");
            try {
                connection =  DriverManager.getConnection(url,properties);
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }
    }

}
