package tier2;

import common.ITier2;
import common.ITier3;
import model.Account;
import model.User;

import java.rmi.RemoteException;

public class BranchImp implements ITier2 {
    private final int regNumber;
    private ITier3 tier3;

    public BranchImp(int regNumber, ITier3 tier3) {
        this.regNumber = regNumber;
        this.tier3 = tier3;
    }

    @Override
    public boolean withdraw(int accountNumber, double amount)
            throws RemoteException {
        Account account = tier3.getAccount(accountNumber);

        if (account == null)
            return false;
        else if (amount <= 0.0 || amount > account.getBalance())
            return false;
        else {
            account.updateBalance(-amount);
            return tier3.updateAccount(account);
        }
    }

    @Override
    public boolean deposit(int accountNumber, double amount)
            throws RemoteException {
        Account account = tier3.getAccount(accountNumber);

        if (account == null)
            return false;
        else if (amount <= 0.0)
            return false;
        else {
            account.updateBalance(amount);
            return tier3.updateAccount(account);
        }
    }

    @Override
    public String getBalance(int accountNumber) throws RemoteException {
        // TODO 19.09.2021 (by Ion): security checks???
        Account account = tier3.getAccount(accountNumber);
        return Double.toString( account.getBalance() );
    }

    @Override
    public boolean createUserAccount(User user) throws RemoteException {
        // TODO 19.09.2021 (by Ion): do some logic checks
        // if username already in the database
        if (tier3.isTaken(user.getUsername())) {
            return false;
        }
        return tier3.createUserAccount(user);
    }

    @Override
    public boolean login(User user) throws RemoteException {
        return tier3.login(user);
    }

    @Override
    public int getMainAccountNr(String username) throws RemoteException {
        return tier3.getAccountNumber(username);
    }
}
