package tier3;


import common.ITier3;
import model.Account;
import model.Transaction;
import model.User;
import tier3.dao.*;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Tier3Controller
        extends UnicastRemoteObject
        implements ITier3 {

    private BankAccountDAO bankAccountDAO;
    private UserAccountDAO userDAO;
    private TransactionDAO transactionDAO;


    public Tier3Controller()
            throws RemoteException {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind(T3_SERVICE_NAME, this);

            bankAccountDAO = BankAccountImpl.getInstance();
            userDAO = UserAccountImpl.getInstance();
            transactionDAO = TransactionImpl.getInstance();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public Account createAccount(String cpr, int accountNr) throws RemoteException {
        return bankAccountDAO.create(cpr, accountNr);
    }

    @Override
    public Account getAccount(int accountNumber)
            throws RemoteException {
        return bankAccountDAO.read(accountNumber);
    }

    @Override
    public Account getAccount(String cpr)
            throws RemoteException {
        return bankAccountDAO.read(cpr);
    }

    @Override
    public boolean updateAccount(Account account)
            throws RemoteException {
        return bankAccountDAO.update(account);
    }

    @Override
    public boolean deleteAccount(int accountNr) throws RemoteException {
        return bankAccountDAO.delete(accountNr);
    }


    @Override
    public boolean createUser(User user) throws RemoteException {
        return userDAO.create(user);
    }

    @Override
    public User getUser(String cpr) throws RemoteException {
        return userDAO.read(cpr);
    }

    @Override
    public boolean updateUser(User user) throws RemoteException {
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String cpr) throws RemoteException {
        return userDAO.delete(cpr);
    }

    @Override
    public boolean addTransaction(Transaction transaction) throws RemoteException {
        return transactionDAO.create(transaction);
    }
}
