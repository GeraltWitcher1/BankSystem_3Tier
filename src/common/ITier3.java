package common;


import model.Account;
import model.Transaction;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ITier3
	extends Remote
{
	Account createAccount(String cpr, int accountNr) throws RemoteException;
	Account getAccount(int accountNumber) throws RemoteException;
	Account getAccount(String cpr) throws RemoteException;
	boolean updateAccount(Account account) throws RemoteException;
	boolean deleteAccount(int accountNr) throws RemoteException;

	boolean createUser(User user) throws RemoteException;
	User getUser(String cpr) throws RemoteException;
	boolean updateUser(User user) throws RemoteException;
	boolean deleteUser(String cpr) throws RemoteException;

	boolean addTransaction(Transaction transaction) throws RemoteException;

	
	String T3_SERVICE_NAME = "T3";
}
