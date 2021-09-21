package tier1;

import common.ITier2;
import model.User;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static common.ITier2.T2_SERVICE_NAME;

public class Customer {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            runCustomer();
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }

    }

    private static void runCustomer() throws MalformedURLException, NotBoundException, RemoteException {
        ITier2 tier2 = (ITier2) Naming.lookup(T2_SERVICE_NAME);
        System.out.println("Customer client running...");

        User me = null;

        boolean loggedIn = false;
        while (!loggedIn) {
            me = makeUser();
            loggedIn = tier2.login(me);
            if (!loggedIn) System.out.println("Incorrect username or password! Or invalid user access");
        }

        while (true) {
            System.out.println("Commands: close | withdraw ");
            String cmd = scan.nextLine();

            if (cmd.equals("close")) {
                break;
            }
            else if (cmd.equals("withdraw")) {
                int accountNr = tier2.getAccountNr(
                        me.getCpr()
                );
                boolean success = tier2.withdraw(
                        accountNr,
                        getAmount()
                );

                System.out.println(success? "The transaction was a success" : "Transaction Denied");
                System.out.println("Account balance: " + tier2.getBalance(accountNr));
            }
            else
                System.out.println("Error: Unknown command.");
        }

    }

    private static BigDecimal getAmount() {
        System.out.print("Please enter the amount: ");
        String amount = scan.nextLine();
        while (!amount.matches("^[0-9]+$")) {
            System.out.println("Please input a number!");
            System.out.print("Enter the amount: ");
            amount = scan.nextLine();
        }
        return BigDecimal.valueOf(Double.parseDouble(amount));
    }

    private static User makeUser() {
        System.out.println("Please login first!");

        String cpr = scan.nextLine();
        while (cpr == null || !cpr.matches("^[0-9]{10}$")) {
            System.out.println("CPR must be 10 digit long.");
            System.out.print("Try again! username: ");
            cpr = scan.nextLine();
        }

        System.out.print("password :");
        String password = scan.nextLine();
        while (password == null) {
            System.out.println("Password cannot be null");
            System.out.print("Try again! password: ");
            password = scan.nextLine();
        }

        return new User(cpr, password, User.CUSTOMER);
    }
}
