package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.BlackJack21;
import entidades.CartaImp;
import entidades.BaralhoImp;

public class BlackJack21RMIImpl implements BlackJack21 {
    BaralhoImp baralho =  new BaralhoImp();


    @Override
    public CartaImp hit() throws RemoteException {
        // 5. Lógica do jogo: compra uma carta do baralho
        try {
            baralho.embaralhar(); // Embaralha o baralho antes de comprar uma carta
            CartaImp carta = baralho.compra(); // Compra uma carta do baralho
            if (carta == null) {
                throw new RemoteException("Baralho vazio, não há cartas para comprar.");
            }
        } catch (Exception e) {
            throw new RemoteException("Erro ao embaralhar o baralho: " + e.getMessage());
        }

    }

    @Override
    public void stand() throws RemoteException {
        // 6. Lógica do jogo: o jogador decide parar de comprar cartas
        // Neste caso, não há lógica adicional a ser implementada, mas o método deve existir.
        System.out.println("Jogador decidiu parar de comprar cartas.");
    }
}