/*
 * 12.09.2018 Original version
 */


package tier3;


import common.ITier3;
import model.Account;
import model.User;
import tier3.dao.BankAccountDAO;
import tier3.dao.BankAccountImpl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;


public class Tier3Controller
        extends UnicastRemoteObject
        implements ITier3 {

    private BankAccountDAO accountDAO;


    public Tier3Controller()
            throws RemoteException {
        try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind(T3_SERVICE_NAME, this);
            accountDAO = BankAccountImpl.getInstance();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }


    public Account getAccount(int accountNumber)
            throws RemoteException {
        return accountDAO.getAccount(accountNumber);
    }


    public boolean updateAccount(Account account)
            throws RemoteException {
        return accountDAO.updateAccount(account);
    }

    @Override
    public boolean createUserAccount(User user) throws RemoteException {
        try {
            return accountDAO.createUserAccount(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isTaken(String username) throws RemoteException {
        return accountDAO.isTaken(username);
    }

    @Override
    public boolean login(User user) throws RemoteException {
        return accountDAO.login(user);
    }

    @Override
    public int getAccountNumber(String username) throws RemoteException {
        return accountDAO.getAccountNumber(username);
    }
}
