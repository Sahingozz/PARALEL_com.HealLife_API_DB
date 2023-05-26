package hooks.db;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HooksDB {

    public static Connection connection;
    private static final String DB_URL = "jdbc:mysql://194.140.198.209/heallife_hospitaltraining";
    private static final String DB_USERNAME = "heallife_hospitaltraininguser";
    private static final String DB_PASSWORD = "PI2ZJx@9m^)3";

    @Before(order = 0)
    public void setUpDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Database bağlantısı gerçekleşti");
        } catch (SQLException e) {
            System.out.println("Database bağlantısı kurulamadı!!!");
            e.printStackTrace();
        }
    }

    @After(order = 0)
    public void tearDownDatabase() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database bağlantısı güvenli şekilde kapatıldı");
            }
        } catch (SQLException e) {
            System.out.println("Database bağlantısı kapatılamadı!!!");
            e.printStackTrace();
        }
    }

}
