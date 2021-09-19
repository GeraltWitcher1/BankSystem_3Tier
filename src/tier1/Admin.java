package tier1;

import common.ITier2;
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
                System.out.println(
                        tier2.createUserAccount(user)
                                ? "Account created."
                                : "Account could not be created."
                );
            }
            else
                System.out.println("Error: Unknown command.");
        }
    }

    private static User createNewUser() {
        System.out.print("Please enter the new customer's username: ");
        String username = scan.nextLine();
        while (username == null || username.length() <= 5) {
            System.out.println("Username must be longer the 5 characters");
            System.out.print("Please enter the new customer's username: ");
            username = scan.nextLine();
        }

        String password = "default";

        User.Type type = null;
        boolean incorrectInput;
        do {
            System.out.println("Please enter the new customer's type: (clerk, admin, customer)");
            String inputType = scan.nextLine();
            switch (inputType) {
                case "clerk":
                    type = User.Type.CLERK;
                    incorrectInput = false;
                    break;
                case "admin":
                    type = User.Type.ADMIN;
                    incorrectInput = false;
                    break;
                case "customer":
                    type = User.Type.CUSTOMER;
                    incorrectInput = false;
                    break;
                default:
                    System.out.println("Invalid type!");
                    incorrectInput = true;
            }
        } while (incorrectInput);

        return new User(username, password, type);
    }


}
