/*
 * 12.09.2018 Original version
 */


package common;


import model.Account;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ITier3
	extends Remote
{
	Account getAccount(int accountNumber) throws RemoteException;
	
	boolean updateAccount(Account account) throws RemoteException;

	boolean createUserAccount(User user) throws RemoteException;

	boolean isTaken(String username) throws RemoteException;

	boolean login(User user) throws RemoteException;

	int getAccountNumber(String username) throws RemoteException;
	
	String T3_SERVICE_NAME = "T3";
}
