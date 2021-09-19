package tier3.dao;

import model.Account;
import model.User;

import java.sql.SQLException;

public interface BankAccountDAO {
    boolean createUserAccount(User user) throws SQLException;

    Account getAccount(int accountNr);

    boolean isTaken(String username);

    boolean updateAccount(Account account);

    boolean login(User user);

    int getAccountNumber(String username);
}
