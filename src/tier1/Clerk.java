package tier1;

import common.ITier2;
import model.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static common.ITier2.T2_SERVICE_NAME;

public class Clerk {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            runClerk();
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            e.printStackTrace();
        }

    }

    private static void runClerk() throws MalformedURLException, NotBoundException, RemoteException {
        ITier2 tier2 = (ITier2) Naming.lookup(T2_SERVICE_NAME);
        System.out.println("Clerk client running...");

        User me = null;

        boolean loggedIn = false;
        while (!loggedIn) {
            me = makeUser();
            loggedIn = tier2.login(me);
            if (!loggedIn) System.out.println("Incorrect username or password! Or invalid user access");
        }

        while (true) {
            System.out.println("Commands: close | deposit | withdraw ");
            String cmd = scan.nextLine();

            if (cmd.equals("close")) {
                break;
            }
            else if (cmd.equals("deposit")) {
                int accountNr = tier2.getMainAccountNr(
                        me.getCpr()
                );
                boolean success = tier2.deposit(
                        accountNr,
                        getAmount()
                );

                System.out.println(success? "The transaction was a success" : "Transaction Denied");
                System.out.println("Account balance: " + tier2.getBalance(accountNr));
            }
            else if (cmd.equals("withdraw")) {
                int accountNr = tier2.getMainAccountNr(
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

    private static double getAmount() {
        System.out.print("Please enter the amount: ");
        String amount = scan.nextLine();
        while (!amount.matches("^[0-9]+$")) {
            System.out.println("Please input a number!");
            System.out.print("Enter the amount: ");
            amount = scan.nextLine();
        }
        return Double.parseDouble(amount);
    }

    private static User makeUser() {
        System.out.println("Please login first!");

        System.out.print("username :");
        String username = scan.nextLine();
        while (username == null || username.length() <= 5) {
            System.out.println("Username must be longer the 5 characters");
            System.out.print("Try again! username: ");
            username = scan.nextLine();
        }

        System.out.print("password :");
        String password = scan.nextLine();
        while (password == null) {
            System.out.println("Password cannot be null");
            System.out.print("Try again! password: ");
            password = scan.nextLine();
        }

        return new User(username, password, User.CLERK);
    }
}
