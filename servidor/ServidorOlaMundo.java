package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.OlaMundoRMI;

public class ServidorOlaMundo {
	public static void main(String[] args) {
		try {
			System.out.println("[SERVIDOR] Iniciando o servidor...");

			// 1. Cria a instância do objeto que implementa a interface remota
			OlaMundoRMIImpl objetoServicoReal = new OlaMundoRMIImpl();

			// 2. EXPORTAÇÃO MANUAL do objeto para torná-lo um objeto remoto
			// O segundo parâmetro '0' significa usar uma porta anônima.
			// O método exportObject retorna o STUB do objeto exportado.
			OlaMundoRMI stub = (OlaMundoRMI) UnicastRemoteObject.exportObject(objetoServicoReal, 0);

			// 3. Criar o RMI Registry
			Registry registry = LocateRegistry.createRegistry(1099);

			// 4. PUBLICAR O STUB (retornado por exportObject) no Registry
			String nomeDoServico = "ServicoOlaMundo";
			registry.bind(nomeDoServico, stub); // Publica o STUB!

			System.out.println("[SERVIDOR] Servidor pronto e aguardando conexões...");

		} catch (Exception e) {
			System.err.println("[SERVIDOR] Exceção no servidor: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
