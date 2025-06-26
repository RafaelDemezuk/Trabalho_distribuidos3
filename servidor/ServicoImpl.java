package servidor;

import interfaces.ClienteCallBack;
import interfaces.ServicoCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServicoImpl extends UnicastRemoteObject implements ServicoCallBack {
    private List<ClienteCallBack> clientes = new ArrayList<>();

    public ServicoImpl() throws RemoteException {
        super();
    }

    @Override
    public void registrarCallBack(ClienteCallBack cliente) throws RemoteException {
        System.out.println("[SERVIDOR] Registrando cliente callback...");
        clientes.add(cliente);
        cliente.notificar("Você foi registrado com sucesso no servidor!");
        System.out.println("[SERVIDOR] Cliente registrado com sucesso.");
    }
}