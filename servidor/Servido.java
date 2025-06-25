package servidor;

import interfaces.BlackJack21;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servido {
	public static void main(String[] args) {
		try {
			System.out.println("[SERVIDOR] Iniciando o servidor...");

			BlackJack21RMIImpl objetoServico = new BlackJack21RMIImpl();

			BlackJack21 stub = (BlackJack21) UnicastRemoteObject.exportObject(objetoServico, 0);

			Registry registry = LocateRegistry.createRegistry(1099);

			registry.bind("21", stub); 

			System.out.println("[SERVIDOR] Servidor pronto e aguardando conexões...");

		} catch (Exception e) {
			System.err.println("[SERVIDOR] Exceção no servidor: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
