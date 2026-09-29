import java.rmi.Naming;
public class server {
    public static void main(String[] args) {
        try {
            CalculatorImple obj = new CalculatorImple();
            Naming.rebind("rmi://localhost/calculator", obj);
            System.out.println("Server is ready.");
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
}