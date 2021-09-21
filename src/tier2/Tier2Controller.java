/*
 * 12.09.2018 Original version
 */


package tier2;


import common.ITier2;
import common.ITier3;
import model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Tier2Controller
        extends UnicastRemoteObject
        implements ITier2 {

    private BranchImp branchImp;

    public Tier2Controller(int regNumber, ITier3 tier3)
            throws RemoteException {
        branchImp = new BranchImp(regNumber, tier3);
    }

    @Override
    public boolean withdraw(int accountNumber, double amount)
            throws RemoteException {

        return branchImp.withdraw(accountNumber, amount);
    }

    @Override
    public boolean deposit(int accountNumber, double amount)
            throws RemoteException {
        return branchImp.deposit(accountNumber, amount);
    }

    @Override
    public String getBalance(int accountNumber) throws RemoteException {
        return branchImp.getBalance(accountNumber);
    }

    @Override
    public boolean createUserAccount(User user) throws RemoteException {
        return branchImp.createUserAccount(user);
    }

    @Override
    public boolean login(User user) throws RemoteException {
       return branchImp.login(user);
    }

    @Override
    public int getMainAccountNr(String username) throws RemoteException {
        return branchImp.getMainAccountNr(username);
    }
}
