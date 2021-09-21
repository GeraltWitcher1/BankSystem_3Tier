package tier3.dao;

import model.Account;
import model.User;

import java.sql.SQLException;

public interface BankAccountDAO {
    Account create(String username, int accountNr);
    Account read(int accountNr);
    boolean update(Account account);
    boolean delete(Account account);
}
