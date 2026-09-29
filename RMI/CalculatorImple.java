import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
public class CalculatorImple
    extends UnicastRemoteObject implements calculator {
        public CalculatorImple() throws RemoteException {
            super();
        }
    
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    
        public int subtract(int a, int b) throws RemoteException {
            return a - b;
        }
    }