package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    static String url = System.getProperty("db.url");
    static String user = System.getProperty("db.user");
    static String password = System.getProperty("db.password");

    @SneakyThrows
    public static void clearTables() throws SQLException {

        var deleteOrderEntity = "DELETE FROM order_entity";
        var deletePaymentEntity = "DELETE FROM payment_entity";
        var deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        var countSQL = "SELECT COUNT(*) FROM order_entity";
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(
                url, user, password);
        runner.update(conn, deleteOrderEntity);
        runner.update(conn, deletePaymentEntity);
        runner.update(conn, deleteCreditRequestEntity);
        var count = runner.query(conn, countSQL, new ScalarHandler<>());
    }


    public static String findPaymentStatus() throws SQLException {
        var statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getData(statusSQL);
    }

    public static String findCreditStatus() throws SQLException {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return getData(statusSQL);
    }

    public static String countRecords() throws SQLException {
        var countSQL = "SELECT COUNT(*) FROM order_entity";
        var runner = new QueryRunner();
        Long count = null;
        var conn = DriverManager.getConnection(
                url, user, password);
        count = runner.query(conn, countSQL, new ScalarHandler<>());
        return Long.toString(count);
    }

    private static String getData(String query) throws SQLException {
        var runner = new QueryRunner();
        String data;
        var conn = DriverManager.getConnection(
                url, user, password);
        data = runner.query(conn, query, new ScalarHandler<>());
        return data;
    }
}
