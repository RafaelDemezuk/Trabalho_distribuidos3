package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.OlaMundoRMI;

public class ClienteRMIThreads implements Runnable {

	@Override
	public void run() {
		try {
            // 1. Localiza o RMI Registry na máquina onde o servidor está rodando
            // "localhost" significa que o cliente está na mesma máquina do servidor.
            // Se o servidor estiver em outra máquina, troque por seu IP ou hostname.
            Registry registry = LocateRegistry.getRegistry("localhost", 1099); // Porta padrão do RMI é 1099
            
            // 2. Nome do serviço que o servidor registrou no RMI Registry
            // Esse nome deve ser exatamente o mesmo usado pelo servidor no bind()
            String nomeDoServico = "ServicoOlaMundo";

            // 3. Faz a busca (lookup) do serviço remoto no registry
            // O resultado é um stub — um objeto proxy que representa o objeto remoto localmente
            OlaMundoRMI servicoRemoto = (OlaMundoRMI) registry.lookup(nomeDoServico);

            // 4. Invoca o método remoto através do stub
            String resposta = servicoRemoto.dizerOla(); // Chamada é feita pela rede ao servidor

            // 5. Exibe a resposta retornada pelo servidor
            System.out.println("[CLIENTE] Resposta recebida do servidor: '" + resposta + "'");

        } catch (Exception e) {
            // Trata qualquer erro de comunicação ou lookup
            System.err.println("[CLIENTE] Exceção no cliente: " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		 for(int i = 0; i < 5; i++) {
			 Thread t = new Thread(new ClienteRMIThreads());
			 t.start();
		 }
	}

}
