package tier2;


import common.ITier2;
import common.ITier3;
import model.Account;
import model.User;

import java.math.BigDecimal;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import static common.ITier3.T3_SERVICE_NAME;

public class Tier2Controller
        extends UnicastRemoteObject
        implements ITier2 {

    private ITier3 tier3;

    public Tier2Controller()
            throws RemoteException {
        try {
            LocateRegistry.createRegistry(2020);
            Naming.rebind(T2_SERVICE_NAME, this);

            tier3 = (ITier3) Naming.lookup(T3_SERVICE_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean withdraw(int accountNumber, BigDecimal amount)
            throws RemoteException {
        Account account = tier3.getAccount(accountNumber);

        if (account == null)
            return false;
        else if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(account.getBalance()) > 0 )
            return false;
        else {
            account.updateBalance(amount.negate());
            return tier3.updateAccount(account);
        }
    }

    @Override
    public boolean deposit(int accountNumber, BigDecimal amount)
            throws RemoteException {
        Account account = tier3.getAccount(accountNumber);

        if (account == null)
            return false;
        else if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return false;
        else {
            account.updateBalance(amount);
            return tier3.updateAccount(account);
        }
    }

    @Override
    public Account createUserAndBankAccount(User user) throws RemoteException {
        // TODO 19.09.2021 (by Ion): do some logic checks
        // if user is already in the database

        Account newAcc = null;
        if (tier3.createUser(user)) {
            newAcc = tier3.createAccount(user.getCpr(), getRandomBankAccountNr());
        }
        return newAcc;
    }

    private int getRandomBankAccountNr() {
        return new Random().nextInt(999999) + 100000;
    }

    @Override
    public boolean login(User user) throws RemoteException {
        User tmp = tier3.getUser(user.getCpr());

        return user.getCpr().equals(tmp.getCpr()) &&
                user.getPassword().equals(tmp.getPassword()) &&
                user.getType().equals(tmp.getType());
    }

    @Override
    public int getAccountNr(String cpr) throws RemoteException {
        return tier3.getAccount(cpr).getNumber();
    }

    @Override
    public String getBalance(int accountNumber) throws RemoteException {
        // TODO 19.09.2021 (by Ion): security checks???
        Account account = tier3.getAccount(accountNumber);
        return account.getBalance().toPlainString();
    }
}
