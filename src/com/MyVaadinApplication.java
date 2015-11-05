package com;

import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.eclipse.jetty.util.log.Log;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * comes
 */
public class MyVaadinApplication extends UI {
    @Override
    public void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Add Personal Pronoun Phrase");
        VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);
        HorizontalLayout textInputLayout = new HorizontalLayout();

        VerticalLayout wordLayout = new VerticalLayout();

        wordLayout.addComponent(new TextField("Russian word"));

        textInputLayout.addComponent(wordLayout);
        mainLayout.addComponent(textInputLayout);

        Button saveButton = new Button("Add Phrase");
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getDbPool();
            }
        });
        mainLayout.addComponent(saveButton);
    }

    private SimpleJDBCConnectionPool getDbPool () {
        String path = System.getProperty("user.home") + "/db/russian.sqlite";
        SimpleJDBCConnectionPool pool;
        Logger.getLogger("testing").warning(path);
        try {
            pool = new SimpleJDBCConnectionPool(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:" + path, "", "");
        } catch (SQLException e) {
            Notification.show("sqlite error " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            Notification.show("runtime sqlite error " + e.getMessage());
            return null;
        }

        Notification.show("Got pool");
        return pool;

    }
}
