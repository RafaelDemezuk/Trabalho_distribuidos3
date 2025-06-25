package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.BlackJack21;
import entidades.CartaImp;
import entidades.BaralhoImp;
// 3. A classe de DEVE implementar a interface remota
public class BlackJack21RMIImpl implements BlackJack21 {
    BaralhoImp baralho =  new BaralhoImp();
    // 4. Implementação do método da interface remota
    @Override
    public Carta hit() throws RemoteException {
        System.out.println("[SERVIDOR] Método dizerOla() foi chamado!");
        return "Olá Mundo do RMI direto do Servidor!";
    }
}