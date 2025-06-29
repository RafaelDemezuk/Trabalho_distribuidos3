package cliente;

import interfaces.ClienteCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteCallbackImpl extends UnicastRemoteObject implements ClienteCallBack {

    private int rodada = 1;
    private String nomeJogador;

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
    
    @Override
    public int getRodada()  throws RemoteException {
        return rodada;
    }
    
    @Override
    public void fimdejogo()  throws RemoteException {
        rodada=0;
    }
    
    @Override
    public void iniciodejogo()  throws RemoteException {
        rodada=1;
    }
    
    @Override
    public String getNomeJogador() throws RemoteException {
        return nomeJogador;
    }
    
    @Override
    public void setNomeJogador(String nome) throws RemoteException {
        this.nomeJogador = nome;
    }
}