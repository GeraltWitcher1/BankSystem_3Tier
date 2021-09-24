package common;


import model.Account;
import model.User;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ITier2
	extends Remote
{
	boolean withdraw(int accountNumber, String cpr, BigDecimal amount) throws RemoteException;
	boolean deposit(int accountNumber, String cpr, BigDecimal amount) throws RemoteException;

	Account createUserAndBankAccount(User user) throws RemoteException;

	int getAccountNr(String cpr) throws RemoteException;
	String getBalance(int accountNumber) throws RemoteException;
	boolean login(User user) throws RemoteException;
	
	
	String T2_SERVICE_NAME = "T2";
}
