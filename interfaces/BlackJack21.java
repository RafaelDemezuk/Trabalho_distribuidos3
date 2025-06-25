package interfaces;
<<<<<<< HEAD

import entidades.CartaImp;
=======
import java.rmi.Remote;
import java.rmi.RemoteException;
>>>>>>> c346f6754e5b78f02601e0363cd2cf3ce29afe44
import java.rmi.Remote;
import java.rmi.RemoteException;
//1. A interface remota DEVE estender java.rmi.Remote
public interface BlackJack21 extends Remote {
	// 2. Cada método que pode ser chamado remotamente DEVE declarar RemoteException
	CartaImp Hit() throws RemoteException;

	void Stand() throws RemoteException;
}
