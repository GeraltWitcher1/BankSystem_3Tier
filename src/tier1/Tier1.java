/*
 * 12.09.2018 Original version
 */


package tier1;


import common.ITier2;

import static common.ITier2.T2_SERVICE_NAME;

import java.rmi.Naming;


public class Tier1 {
    // Just a simple test driver. Should be client

    public static void main(String[] args) {
        try {
            ITier2 tier2 = (ITier2) Naming.lookup(T2_SERVICE_NAME);

            double amount = 85.17;

            while (tier2.withdraw(114509, amount))
                System.out.println("Withdrawn: " + amount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
