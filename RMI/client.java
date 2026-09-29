import java.rmi.Naming;

public class client {
    public static void main(String[] args) {
        try {
            calculator obj = (calculator) Naming.lookup(
                "rmi://localhost/calculator");
            System.out.println("Addition: " + obj.add(5, 3));
            System.out.println("Subtraction: " + obj.subtract(5, 3));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
