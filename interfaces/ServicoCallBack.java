package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoCallBack extends Remote {
    void registrarCallBack(ClienteCallBack cliente) throws RemoteException;
    void desregistrarCallBack(ClienteCallBack cliente) throws RemoteException;
}
