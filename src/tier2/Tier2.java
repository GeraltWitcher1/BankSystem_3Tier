package tier2;

public class Tier2 {

    public static void main(String[] args) {
        try {
            Tier2Controller controller = new Tier2Controller();

            System.out.println("Tier2 started...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
