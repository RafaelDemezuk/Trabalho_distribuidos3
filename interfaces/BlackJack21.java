package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import entidades.CartaImp;
//1. A interface remota DEVE estender java.rmi.Remote
public interface BlackJack21 extends Remote {
	// 2. Cada método que pode ser chamado remotamente DEVE declarar RemoteException
	CartaImp Hit() throws RemoteException;

	void Stand() throws RemoteException;
}
