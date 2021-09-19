package model;

import java.io.Serializable;

public class User
        implements Serializable {

    public enum Type {
        CLERK("Clerk"),
        ADMIN("Admin"),
        CUSTOMER("Customer");

        private final String type;

        Type(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }
    }


    private final String username;
    private final String password;
    private final Type type;

    public User(String username, String password, Type type) {
        if (username == null || username.trim().equals("")) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        this.username = username;

        if (password == null || password.length() <= 5) {
            throw new IllegalArgumentException("Password must be longer than 5 characters.");
        }
        this.password = password;

        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type.getType();
    }
}
