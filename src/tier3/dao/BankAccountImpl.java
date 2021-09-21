package tier3.dao;


import model.Account;

import java.math.BigDecimal;
import java.sql.*;

public class BankAccountImpl implements BankAccountDAO {
    private static BankAccountImpl instance;

    private BankAccountImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized BankAccountImpl getInstance()
            throws SQLException {
        if (instance == null) {
            instance = new BankAccountImpl();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank_system",
                "postgres", "admin");
    }

    @Override
    public Account create(String username, int accountNr) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO account(account_number, balance, owner) VALUES (?, ?, ?);");
            statement.setInt(1, accountNr);
            statement.setBigDecimal(2, BigDecimal.ZERO);
            statement.setString(3, username);
            statement.executeUpdate();
            return new Account(accountNr, BigDecimal.ZERO, username);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account read(int accountNr) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM account WHERE account_number = ?");
            statement.setInt(1, accountNr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createAccount(resultSet);
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account read(String cpr) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM account JOIN \"user\" u on u.cpr = account.owner WHERE u.cpr = ?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createAccount(resultSet);
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Account createAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getInt("account_number"),
                resultSet.getBigDecimal("balance"),
                resultSet.getString("owner")
        );
    }

    @Override
    public boolean update(Account account) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE account SET balance = ? WHERE account_number = ?");
            statement.setBigDecimal(1, account.getBalance() );
            statement.setInt(2, account.getNumber() );
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Account account) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM account WHERE account_number = ?");
            statement.setInt(1, account.getNumber() );
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
