/*
 * 12.09.2018 Original version
 */


package common;


import model.Account;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ITier2
	extends Remote
{
	boolean withdraw(int accountNumber, double amount) throws RemoteException;
	boolean deposit(int accountNumber, double amount) throws RemoteException;
	String getBalance(int accountNumber) throws RemoteException;
	boolean createUserAccount(User user) throws RemoteException;
	boolean login(User user) throws RemoteException;
	int getMainAccountNr(String username) throws RemoteException;
	
	
	String T2_SERVICE_NAME = "T2";
}
