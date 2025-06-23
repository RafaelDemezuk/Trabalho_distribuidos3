package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.BlackJack21Imp;
import java.util.Scanner;

public class ClienteRMIThreads implements Runnable {

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            String nomeDoServico = "JOGAR";

            BlackJack21Imp servicoRemoto = (BlackJack21Imp) registry.lookup(nomeDoServico);

            System.out.println("[CLIENTE] Conectado ao serviço remoto: " + nomeDoServico);

            Scanner scanner = new Scanner(System.in);

            String opcaoInicial;

            do {
                System.out.println("-".repeat(10) + "Menu Principal" + "-".repeat(10));
                System.out.println("\n[1] JOGAR\n[2] SAIR\n" + "-".repeat(10));
                opcaoInicial = scanner.nextLine().trim().toUpperCase();

                switch (opcaoInicial) {
                    case "1":
                        String comandoJogo;
                        do {
                            System.out.println("-".repeat(10) + "Menu do Jogo" + "-".repeat(10));
                            System.out.println("\n[1] HIT\n[2] STAND\n[3] VOLTAR AO MENU PRINCIPAL\n" + "-".repeat(10));
                            comandoJogo = scanner.nextLine().trim().toUpperCase();

                            switch (comandoJogo) {
                                case "1":
                                    servicoRemoto.Hit();
                                    break;
                                case "2":
                                    servicoRemoto.Stand();
                                    break;
                                case "3":
                                    System.out.println("[CLIENTE] Voltando ao menu principal...");
                                    break;
                                default:
                                    System.out.println("[CLIENTE] Comando inválido!");
                            }
                        } while (!comandoJogo.equals("3") && !comandoJogo.equals("VOLTAR"));
                        break;

                    case "2":
                        System.out.println("[CLIENTE] Saindo...");
                        break;

                    default:
                        System.out.println("[CLIENTE] Opção inválida!");
                }
            } while (!opcaoInicial.equals("2") && !opcaoInicial.equals("SAIR"));

            scanner.close();

            // 5. Exibe a resposta retornada pelo servidor
            System.out.println("[CLIENTE] Resposta recebida do servidor: '" + resposta + "'");

        } catch (Exception e) {
            // Trata qualquer erro de comunicação ou lookup
            System.err.println("[CLIENTE] Exceção no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new ClienteRMIThreads());
            t.start();
        }
    }

}
