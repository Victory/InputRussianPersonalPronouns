package com.org.dfhu.rudb;

import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.ui.Notification;

import java.sql.SQLException;
import java.util.logging.Logger;

public class RuDb {

    private static SimpleJDBCConnectionPool pool = getDbPool();


    public static SimpleJDBCConnectionPool getDbPool() {
        String path = System.getProperty("user.home") + "/db/russian.sqlite";
        Logger.getLogger("testing").warning(path);
        try {
            return new SimpleJDBCConnectionPool(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:" + path, "", "");
        } catch (SQLException e) {
            Notification.show("sqlite error " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            Notification.show("runtime sqlite error " + e.getMessage());
            return null;
        }
    }
}
