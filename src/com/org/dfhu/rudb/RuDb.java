package com.org.dfhu.rudb;

import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.ui.Notification;
import junit.framework.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class RuDb {

    public static class Tables {
        public static final String TEST = "test";
    }

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

    /**
     * get the first value from the selected table using the given sql where clause
     *
     * @param table table name from RuDb.Tables
     * @param where where clause without the leading "WHERE"
     * @return first value
     */
    public static TestRow first(String table, String where) {
        String sql = "SELECT * FROM " + table + " WHERE " + where + " LIMIT 1";
        try {
            Connection connection = getDbPool().reserveConnection();
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(sql);
            boolean result = results.next();
            TestRow row = new TestRow();
            if (!result) {
                return row;
            }
            row.kk = results.getString("kk");
            row.vv = results.getString("vv");
            return row;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
