package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteCallBack extends Remote {
    void notificar(String mensagem) throws RemoteException;
   
}