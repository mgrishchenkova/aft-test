package redmine.dataBase;

import lombok.SneakyThrows;
import redmine.Property;

import java.sql.DriverManager;

public class Connection {
    private String dbHost;
    private Integer dbPort;
    private String dbUser;
    private String dbPass;
    private String dbName;
    private java.sql.Connection connection;

    private void initVariables() {
        dbHost = Property.getStringProperties("dbHost");
        dbPort = Property.getIntegerProperty("dbPort");
        dbUser = Property.getStringProperties("dbUser");
        dbPass = Property.getStringProperties("dbPass");
        dbName = Property.getStringProperties("dbName");
    }
    @SneakyThrows
    public void connect(){
        Class.forName("org.postgresql.Driver");
        String url=String.format("jdbc:postgresql://%s:%d/%s?user=%s&password=%s",dbHost,dbPort,dbName,dbUser,dbPass);
        connection= DriverManager.getConnection(url);
    }


}
