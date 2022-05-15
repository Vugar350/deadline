package ru.netology.sql;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbInteraction {

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        return connection;
    }

    public static void clean() {
        val auth = "DELETE FROM auth_codes";
        val transactions = "DELETE FROM card_transactions";
        val card = "DELETE FROM cards";
        val user = "DELETE FROM users";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
        ) {
            runner.update(conn, auth);
            runner.update(conn, transactions);
            runner.update(conn, card);
            runner.update(conn, user);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static String getCode() {
        try (val conn = getConnection();
             val countStmt = conn.createStatement()) {
            val sql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return null;
    }
}
