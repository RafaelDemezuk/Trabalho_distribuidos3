package servidor;

import entidades.BaralhoImp;
import entidades.CartaImp;
import interfaces.BlackJack21;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BlackJack21RMIImpl extends UnicastRemoteObject implements BlackJack21 {
    BaralhoImp baralho = new BaralhoImp();

    public BlackJack21RMIImpl() throws RemoteException {
        super();
    }

    @Override
    public CartaImp hit() throws RemoteException {
        CartaImp carta = baralho.compra();
        
        if (carta == null) {
            throw new RemoteException("Baralho vazio, não é possível comprar mais cartas.");
        }
        else {
            System.out.println("Carta comprada: " + carta.toString());
        }

        return carta;
    }

    @Override
    public void stand() throws RemoteException {
        System.out.println("Jogador decidiu parar de comprar cartas.");
    }
}