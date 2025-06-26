package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BlackJack21 extends Remote {
	void  startGame() throws RemoteException;
	void hit() throws RemoteException;
	void stand() throws RemoteException;
	
}
