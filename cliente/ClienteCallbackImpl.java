package cliente;

import interfaces.ClienteCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteCallbackImpl extends UnicastRemoteObject implements ClienteCallBack {

    private int rodada = 1;

    protected ClienteCallbackImpl() throws RemoteException {
        super();
    }

    @Override
    public void notificar(String mensagem) throws RemoteException {
        new Thread(() -> {
            System.out.println("[CLIENTE CALLBACK] Notificação: " + mensagem);
            System.out.flush();
        }).start();
    }

    public int getRodada() {
        return rodada;
    }

    public void fimdejogo() {
        rodada=0;
    }

    public void iniciodejogo() {
        rodada=1;
    }
}