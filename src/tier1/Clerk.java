package tier1;

import common.ITier2;
import model.User;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static common.ITier2.T2_SERVICE_NAME;

public class Clerk implements RemoteSender {

    private ITier2 tier2;
    private User me;
    private boolean isLoggedIn;

    public Clerk() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
            tier2 = (ITier2) Naming.lookup(T2_SERVICE_NAME);

            me = null;
            isLoggedIn = false;

        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean login(User me) throws RemoteException {
        isLoggedIn = tier2.login(me, this);
        if (isLoggedIn)
            this.me = me;
        return isLoggedIn;
    }

    public void logout() throws RemoteException {
        tier2.logout(me.getCpr(), this);
        UnicastRemoteObject.unexportObject(this, true);
    }

    public void withdraw(BigDecimal amount) throws RemoteException {
        int accountNr = tier2.getAccountNr(
                me.getCpr()
        );
        boolean success = tier2.withdraw(
                accountNr,
                me.getCpr(),
                amount
        );

        System.out.println(success? "The transaction was a success" : "Transaction Denied");
    }

    public void deposit(BigDecimal amount) throws RemoteException {
        int accountNr = tier2.getAccountNr(
                me.getCpr()
        );
        boolean success = tier2.deposit(
                accountNr,
                me.getCpr(),
                amount
        );

        System.out.println(success? "The transaction was a success" : "Transaction Denied");
    }

    @Override
    public void showBalance(int accountNr, BigDecimal balance) throws RemoteException {
        System.out.printf("The balance of your account (#%d) is %s\n", accountNr, balance.toPlainString());
    }
}
