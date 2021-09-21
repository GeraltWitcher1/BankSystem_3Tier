/*
 * 12.09.2018 Original version
 */


package tier2;

import common.ITier3;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static common.ITier2.T2_SERVICE_NAME;
import static common.ITier3.T3_SERVICE_NAME;

public class Tier2 {

    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry(1099);
            ITier3 tier3 = (ITier3) registry.lookup("T3");

            LocateRegistry.createRegistry(8099);
            Tier2Controller controller = new Tier2Controller(12, tier3);
            Naming.rebind(T2_SERVICE_NAME, controller);

            System.out.println("Tier2 (branch 12) started...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
