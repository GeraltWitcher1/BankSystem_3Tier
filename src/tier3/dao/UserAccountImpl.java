package tier3.dao;

import model.Account;
import model.User;

import java.math.BigDecimal;
import java.sql.*;

public class UserAccountImpl implements UserAccountDAO {
    private static UserAccountImpl instance;

    private UserAccountImpl() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized UserAccountImpl getInstance()
            throws SQLException {
        if (instance == null) {
            instance = new UserAccountImpl();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank_system",
                "postgres", "admin");
    }

    @Override
    public boolean create(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"user\"(cpr, password, type) VALUES (?, ?, ?);");
            statement.setString(1, user.getCpr());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getType());
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User read(String cpr) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM \"user\" WHERE cpr = ?");
            statement.setString(1, cpr);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getString("cpr"),
                resultSet.getString("password"),
                resultSet.getString("type")
        );
    }

    @Override
    public boolean update(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE \"user\" SET password = ? and type = ? WHERE cpr = ?");
            statement.setString(1, user.getPassword() );
            statement.setString(2, user.getType() );
            statement.setString(3, user.getCpr() );
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM \"user\" WHERE cpr = ?");
            statement.setString(1, user.getCpr() );
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
