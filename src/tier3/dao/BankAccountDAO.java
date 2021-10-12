package tier3.dao;


import model.Account;

public interface BankAccountDAO {
    Account create(String cpr, int accountNr);
    Account read(int accountNr);
    Account read(String cpr);
    boolean update(Account account);
    boolean delete(int accountNr);
}
