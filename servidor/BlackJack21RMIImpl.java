package servidor;

import java.rmi.RemoteException;
import interfaces.BlackJack21;
import entidades.CartaImp;
import entidades.BaralhoImp;

public class BlackJack21RMIImpl implements BlackJack21 {
    BaralhoImp baralho =  new BaralhoImp();


    @Override
    public CartaImp hit() throws RemoteException {
        // 5. Lógica do jogo: compra uma carta do baralho
        CartaImp carta = baralho.compra();
        if (carta == null) {
            throw new RemoteException("Baralho vazio, não é possível comprar mais cartas.");
        }
        return carta;
    }

    @Override
    public void stand() throws RemoteException {
        // 6. Lógica do jogo: o jogador decide parar de comprar cartas
        // Neste caso, não há lógica adicional a ser implementada, mas o método deve existir.
        System.out.println("Jogador decidiu parar de comprar cartas.");
    }
}