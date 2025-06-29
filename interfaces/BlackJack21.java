package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BlackJack21 extends Remote {
	void  startGame(String nomeJogador) throws RemoteException;
	void hit(String nomeJogador) throws RemoteException;
	void stand(String nomeJogador) throws RemoteException;
	
}
