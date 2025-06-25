package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.BlackJack21;
import entidades.CartaImp;
import entidades.BaralhoImp;

public class BlackJack21RMIImpl implements BlackJack21 {
    BaralhoImp baralho =  new BaralhoImp();


    @Override
    public Carta hit() throws RemoteException {
        System.out.println("[SERVIDOR] Método dizerOla() foi chamado!");
        return "Olá Mundo do RMI direto do Servidor!";
    }
}