package servidor;

import entidades.BaralhoImp;
import entidades.CartaImp;
import interfaces.BlackJack21;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BlackJack21RMIImpl extends UnicastRemoteObject implements BlackJack21 {
    BaralhoImp baralho = new BaralhoImp();
    private ServicoImpl servicoCallback;

    public BlackJack21RMIImpl(ServicoImpl servicoCallback) throws RemoteException {
        super();
        this.servicoCallback = servicoCallback;
    }

    @Override
    public CartaImp hit() throws RemoteException {
        CartaImp carta = baralho.compra();
        
        if (carta == null) {
            String mensagem = "Baralho vazio, não é possível comprar mais cartas.";
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
            throw new RemoteException(mensagem);
        }
        else {
            String mensagem = "Carta comprada: " + carta.toString();
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
        }

        return carta;
    }

    @Override
    public void stand() throws RemoteException {
        String mensagem = "Jogador decidiu parar de comprar cartas.";
        System.out.println("[SERVIDOR] " + mensagem);
        servicoCallback.notificarTodosClientes(mensagem);
    }
}