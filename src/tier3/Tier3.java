package tier3;

public class Tier3 {
    public static void main(String[] args) {
        try {
            Tier3Controller controller = new Tier3Controller();

            System.out.println("Tier3 started...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
