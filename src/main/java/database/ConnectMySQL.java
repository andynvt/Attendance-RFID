/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author chuna
 */
public class ConnectMySQL {

    private final String hostName;
    private final String dbName;
    private final String userName;
    private final String password;

    public ConnectMySQL(String hostName, String dbName, String userName, String password) {
        this.hostName = hostName;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    public Connection getConnectionMySQL() throws SQLException, ClassNotFoundException {
        String uRL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
        Connection cnn = DriverManager.getConnection(uRL, userName, password);
        return cnn;
    }
}
