package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import other.custom.Alert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chuna
 */
public class DatabaseHandler {

    private static DatabaseHandler handler;
    private static Connection conn = null;
    private static Statement stm = null;
    private static final String hostName = "localhost";
    private static final String dbName = "DiemDanh";
    private static final String userName = "root";
    private static final String password = "";

    public DatabaseHandler() {
        createConnection();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    private void createConnection() {
        try {
            conn = new ConnectMySQL(hostName, dbName, userName, password).getConnectionMySQL();
        } catch (SQLException | ClassNotFoundException ex) {
            int select = Alert.showErrorDialog("Không thể kết nối đến cơ sở dữ liệu\n" + "Thử lại lần nữa?", "Lỗi kết nối cơ sở dữ liệu");
            if (select == Alert.YES) {
                createConnection();
            } else {
                System.exit(0);
            }
        }
    }

    public void closeConnection() {
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException | NullPointerException e) {
            Alert.showErrorDialog("Error:" + e.getMessage(), "Lỗi đóng cơ sở dữ liệu");
        }
    }

    public boolean isExistTable(String TABLE_NAME, String query) {
        try {
            stm = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
//                System.err.println("Dữ liệu trong bảng " + TABLE_NAME + " đã sẵn sàng!");
                return true;
            } else {
                stm.execute(query);
                return false;
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println(e.getMessage() + " ... setupDatabase");
        } finally {

        }
        return false;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stm = conn.createStatement();
            result = stm.executeQuery(query);
        } catch (SQLException | NullPointerException e) {
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            stm = conn.createStatement();
            stm.execute(query);
        } catch (SQLException | NullPointerException e) {
            return false;
        } finally {

        }
        return true;
    }
}
