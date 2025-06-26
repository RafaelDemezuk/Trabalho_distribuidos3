package cliente;

import interfaces.ClienteCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteCallbackImpl extends UnicastRemoteObject implements ClienteCallBack {

    protected ClienteCallbackImpl() throws RemoteException {
        super();
    }

    public void notificar(String mensagem) throws RemoteException {
        System.out.println("[CLIENTE CALLBACK] Notificação: " + mensagem);
    }
}