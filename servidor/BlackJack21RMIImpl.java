package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.BlackJack21;

// 3. A classe de DEVE implementar a interface remota
public class BlackJack21RMIImpl implements BlackJack21 {
    Baralho baralho;
    // 4. Implementação do método da interface remota
    @Override
    public Carta hit() throws RemoteException {
        System.out.println("[SERVIDOR] Método dizerOla() foi chamado!");
        return "Olá Mundo do RMI direto do Servidor!";
    }
}