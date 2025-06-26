package interfaces;

import entidades.CartaImp;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BlackJack21 extends Remote {
	
	CartaImp hit() throws RemoteException;
	void stand() throws RemoteException;
}
