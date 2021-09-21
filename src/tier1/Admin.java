package tier1;

import common.ITier2;
import model.Account;
import model.User;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static common.ITier2.T2_SERVICE_NAME;

public class Admin {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            runAdmin();
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }


    }

    private static void runAdmin() throws MalformedURLException, NotBoundException, RemoteException {
        ITier2 tier2 = (ITier2) Naming.lookup(T2_SERVICE_NAME);

        System.out.println("Admin client running...");

        while (true) {
            System.out.println("Commands: close | create ");
            String cmd = scan.nextLine();

            if (cmd.equals("close")) {
                break;
            }
            else if (cmd.equals("create")) {
                User user = createNewUser();
                Account account = tier2.createUserAndBankAccount(user);
                System.out.println(
                        account != null
                                ? "Account with the number " + account.getNumber() + " created."
                                : "Account could not be created."
                );
            }
            else
                System.out.println("Error: Unknown command.");
        }
    }

    private static User createNewUser() {
        System.out.print("Please enter the new customer's cpr: ");
        String cpr = scan.nextLine();
        while (cpr == null || !cpr.matches("^[0-9]{10}$")) {
            System.out.println("CPR must be 10 digit long.");
            System.out.print("Please enter the new customer's cpr: ");
            cpr = scan.nextLine();
        }

        String password = "default";

        String type = null;
        boolean incorrectInput;
        do {
            System.out.println("Please enter the new customer's type: (clerk, admin, customer)");
            String inputType = scan.nextLine();
            switch (inputType) {
                case "clerk":
                    type = User.CLERK;
                    incorrectInput = false;
                    break;
                case "admin":
                    type = User.ADMIN;
                    incorrectInput = false;
                    break;
                case "customer":
                    type = User.CUSTOMER;
                    incorrectInput = false;
                    break;
                default:
                    System.out.println("Invalid type!");
                    incorrectInput = true;
            }
        } while (incorrectInput);

        return new User(cpr, password, type);
    }


}
