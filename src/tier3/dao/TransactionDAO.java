package tier3.dao;

import model.Transaction;

import java.util.ArrayList;

public interface TransactionDAO {
    boolean create(Transaction transaction);
    ArrayList<Transaction> readAllFor(String accountNr);
    boolean deleteFor(Transaction transaction);
}
