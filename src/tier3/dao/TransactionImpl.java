package tier3.dao;

import model.Transaction;

import java.sql.*;
import java.util.ArrayList;

public class TransactionImpl implements TransactionDAO {
    private static TransactionImpl instance;

    private TransactionImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized TransactionImpl getInstance()
            throws SQLException {
        if (instance == null) {
            instance = new TransactionImpl();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank_system",
                "postgres", "admin");
    }

    @Override
    public boolean create(Transaction transaction) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO transaction(account_number, user_cpr, amount, date_time, type) VALUES (?, ?, ?, ?, ?);");
            statement.setInt(1, transaction.getAccount().getNumber());
            statement.setString(2, transaction.getUserCpr());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setString(5, transaction.getType());

            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODO: 24.09.2021 (by Ion) Implement the readAll and delete Transactions
    @Override
    public ArrayList<Transaction> readAllFor(String accountNr) {
        return null;
    }

    @Override
    public boolean deleteFor(Transaction transaction) {
        return false;
    }


}
