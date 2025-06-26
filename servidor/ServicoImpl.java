package servidor;

import interfaces.ClienteCallBack;
import interfaces.ServicoCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
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

    public void notificarTodosClientes(String mensagem) throws RemoteException {
        Iterator<ClienteCallBack> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            ClienteCallBack cliente = iterator.next();
            try {
                cliente.notificar(mensagem);
                System.out.println("[SERVIDOR] Mensagem enviada via callback: " + mensagem);
            } catch (RemoteException e) {
                System.err.println("[SERVIDOR] Erro ao notificar cliente: " + e.getMessage());
                iterator.remove(); // Remove com segurança usando o iterator
            }
        }
    }

    public void notificarFimDeJogo() throws RemoteException {
        for (ClienteCallBack cliente : clientes) {
            try {
                cliente.fimdejogo();
                System.out.println("[SERVIDOR] Notificação de fim de jogo enviada.");
            } catch (RemoteException e) {
                System.err.println("[SERVIDOR] Erro ao notificar fim de jogo: " + e.getMessage());
            }
        }
    }

}