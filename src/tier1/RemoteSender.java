package tier1;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSender extends Remote {
    void showBalance(int accountNr, BigDecimal balance) throws RemoteException;
}
