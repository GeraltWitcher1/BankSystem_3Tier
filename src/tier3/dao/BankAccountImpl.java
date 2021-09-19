package tier3.dao;

import model.Account;
import model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Random;

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
    public boolean createUserAccount(User user) throws SQLException {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement userStatement = connection.prepareStatement(
                    "INSERT INTO \"user\"(type, username, password) VALUES (?, ?, ?);");
            userStatement.setString(1, user.getType());
            userStatement.setString(2, user.getUsername());
            userStatement.setString(3, user.getPassword());
            userStatement.executeUpdate();

            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account(account_number, balance, owner) VALUES (?, ?, ?);");
            accountStatement.setString(1, getRandomAccountNr());
            accountStatement.setBigDecimal(2, BigDecimal.ZERO);
            accountStatement.setString(3, user.getUsername());
            accountStatement.executeUpdate();

            connection.commit();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
    }

    private static String getRandomAccountNr() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public Account getAccount(int accountNr) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from account where account_number = ?"
            );
            statement.setString(1, Integer.toString(accountNr));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createAccount(resultSet);
            }
            return null;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean isTaken(String username) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from \"user\" where username = ?"
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            return false;
        }
    }

    private Account createAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                Integer.parseInt( resultSet.getString("account_number") ),
                Double.parseDouble( resultSet.getString("balance") )
                );
    }

    @Override
    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE account SET balance = ? WHERE account_number = ?");
            statement.setBigDecimal(1, BigDecimal.valueOf( account.getBalance() ));
            statement.setString(2, Integer.toString( account.getNumber() ));
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean login(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from \"user\" where username = ? and password = ? and type = ?"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getType());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int getAccountNumber(String username) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "select account_number from account join \"user\" u on u.username = account.owner where username = ?"
            );
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Integer.parseInt( resultSet.getString("account_number") );
            }
            return -1;

        } catch (SQLException e) {
            return -1;
        }
    }
}
