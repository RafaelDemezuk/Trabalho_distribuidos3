package servidor;

import interfaces.BlackJack21;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServicoImpl servico = new ServicoImpl();
            BlackJack21 blackjackService = new BlackJack21RMIImpl(servico);
            
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServidorCallBack", servico);
            registry.rebind("21", blackjackService);
            
            System.out.println("[SERVIDOR] Servidor iniciado e aguardando conexões...");

        } catch (Exception e) {
            System.err.println("[SERVIDOR] Exceção no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}