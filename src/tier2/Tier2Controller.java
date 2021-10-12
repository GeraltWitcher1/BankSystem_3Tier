package tier2;


import common.ITier2;
import common.ITier3;
import model.Account;
import model.Transaction;
import model.User;
import tier1.RemoteSender;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static common.ITier3.T3_SERVICE_NAME;

public class Tier2Controller
        extends UnicastRemoteObject
        implements ITier2 {

    private ITier3 tier3;
    private HashMap<Integer, ArrayList<RemoteSender>> remoteUsers;

    public Tier2Controller()
            throws RemoteException {
        try {
            LocateRegistry.createRegistry(2020);
            Naming.rebind(T2_SERVICE_NAME, this);
            remoteUsers = new HashMap<>();

            tier3 = (ITier3) Naming.lookup(T3_SERVICE_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean withdraw(int accountNumber, String cpr, BigDecimal amount)
            throws RemoteException {
        Account account = tier3.getAccount(accountNumber);

        if (account == null)
            return false;
        else if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(account.getBalance()) > 0)
            return false;
        else {
            account.updateBalance(amount.negate());
            Transaction transaction = new Transaction(
                    account,
                    cpr,
                    amount,
                    Transaction.WITHDRAWAL

            );

            try {
                ArrayList<RemoteSender> users = remoteUsers.get(account.getNumber());
                for (var user : users) {
                    user.showBalance(account.getNumber(), account.getBalance());
                }
            }
            catch (Exception ignored) {}

            tier3.addTransaction(transaction);
            return tier3.updateAccount(account);
        }
    }

    @Override
    public boolean deposit(int accountNumber, String cpr, BigDecimal amount)
            throws RemoteException {
        Account account = tier3.getAccount(accountNumber);

        if (account == null)
            return false;
        else if (amount.compareTo(BigDecimal.ZERO) <= 0)
            return false;
        else {
            account.updateBalance(amount);
            Transaction transaction = new Transaction(
                    account,
                    cpr,
                    amount,
                    Transaction.DEPOSIT
            );

            try {
                ArrayList<RemoteSender> users = remoteUsers.get(account.getNumber());
                for (var user : users) {
                    user.showBalance(account.getNumber(), account.getBalance());
                }
            }
            catch (NullPointerException ignored) {}


            tier3.addTransaction(transaction);
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
    public boolean login(User user, RemoteSender remoteUser) throws RemoteException {
        User tmp = tier3.getUser(user.getCpr());

        boolean loggedIn = user.getCpr().equals(tmp.getCpr()) &&
                user.getPassword().equals(tmp.getPassword()) &&
                user.getType().equals(tmp.getType());

        if (loggedIn) {
            int accNr = tier3.getAccount(user.getCpr()).getNumber();
            if (remoteUsers.containsKey(accNr)) {
                remoteUsers.get(accNr).add(remoteUser);
            } else {
                remoteUsers.put(accNr, new ArrayList<>(List.of(remoteUser)));
            }
        }
        return loggedIn;
    }

    @Override
    public void logout(String cpr, RemoteSender remoteUser) throws RemoteException {
        int accNr = tier3.getAccount(cpr).getNumber();
        System.out.println(accNr);
        System.out.println(remoteUsers.get(accNr).remove(remoteUser));
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
