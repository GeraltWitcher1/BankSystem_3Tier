package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class Transaction implements Serializable {
    public static final String DEPOSIT = "Deposit";
    public static final String WITHDRAWAL = "Withdrawal";

    private Account account;
    private String userCpr;
    private BigDecimal amount;
    private String type;

    public Transaction(Account account, String userCpr, BigDecimal amount, String type) {

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        this.account = account;

        if (userCpr == null || !userCpr.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("CPR must be 10 digit long.");
        }
        this.userCpr = userCpr;

        if (amount == null || amount.equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("Amount cannot be null or zero.");
        }
        this.amount = amount;

        if (type == null ||
                !Arrays.asList(new String[] {DEPOSIT, WITHDRAWAL}).contains(type)) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public String getUserCpr() {
        return userCpr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}
