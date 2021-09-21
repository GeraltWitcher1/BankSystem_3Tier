package model;

import java.io.Serializable;
import java.util.Arrays;

public class User
        implements Serializable {

    public static final String CLERK = "Clerk";
    public static final String ADMIN = "Admin";
    public static final String CUSTOMER = "Customer";

    private final String cpr;
    private final String password;
    private final String type;

    public User(String cpr, String password, String type) {
        if (cpr == null || cpr.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("CPR must be 10 digits long.");
        }
        this.cpr = cpr;

        if (password == null || password.length() <= 5) {
            throw new IllegalArgumentException("Password must be longer than 5 characters.");
        }
        this.password = password;

        if (type == null ||
                Arrays.asList(new String[] {CLERK, CUSTOMER, ADMIN}).contains(type)) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public String getCpr() {
        return cpr;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
}
