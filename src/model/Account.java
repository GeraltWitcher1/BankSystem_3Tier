/*
 * 12.09.2018 Original version
 */

package model;


import java.io.Serializable;
import java.math.BigDecimal;


public class Account
        implements Serializable {
    private int number;
    private BigDecimal balance;
    private String owner;


    public Account(int number, BigDecimal balance, String owner) {
        this.number = number;
        this.balance = balance;
        this.owner = owner;
    }


    public int getNumber() {
        return number;
    }


    public BigDecimal getBalance() {
        return balance;
    }


    public void updateBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
