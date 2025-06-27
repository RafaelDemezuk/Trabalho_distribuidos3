package cliente;

import interfaces.BlackJack21;
import interfaces.ClienteCallBack;
import interfaces.ServicoCallBack;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente implements Runnable {

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            ServicoCallBack servico = (ServicoCallBack) registry.lookup("ServidorCallBack");
            ClienteCallBack callBack = new ClienteCallbackImpl();
            servico.registrarCallBack(callBack);

            BlackJack21 servicoBlackJack = (BlackJack21) registry.lookup("21");

            Scanner scanner = new Scanner(System.in);
            String opcaoInicial;

            do {
                System.out.println("-".repeat(10) + "Menu Principal" + "-".repeat(10));
                System.out.println("\n[1] JOGAR \n[2] SAIR \n" + "-".repeat(10));
                opcaoInicial = scanner.nextLine().trim().toUpperCase();

                switch (opcaoInicial) {
                    case "1":
                        servicoBlackJack.startGame();
                        String comandoJogo = "";
                        while (!comandoJogo.equals("3") && callBack.getRodada() != 0) {
                            System.out.println("-".repeat(10) + "Menu do Jogo" + "-".repeat(10));
                            System.out.println("\n[1] HIT\n[2] STAND\n[3] VOLTAR AO MENU PRINCIPAL\n" + "-".repeat(10));
                            comandoJogo = scanner.nextLine().trim().toUpperCase();

                            switch (comandoJogo) {
                                case "1":
                                    try {
                                        servicoBlackJack.hit();
                                    } catch (Exception e) {
                                        System.err.println("[CLIENTE] Erro ao comprar carta: " + e.getMessage());
                                    }
                                    break;
                                case "2":
                                    servicoBlackJack.stand();
                                    break;
                                case "3":
                                    System.out.println("[CLIENTE] Voltando ao menu principal...");
                                    break;
                                default:
                                    System.out.println("[CLIENTE] Comando inválido!");
                            }
                        }
                        ;
                        break;

                    case "2":
                        System.out.println("[CLIENTE] Desconectando do servidor...");
                        try {
                            // Desregistrar o callback antes de sair
                            servico.desregistrarCallBack(callBack);
                        } catch (Exception e) {
                            System.err.println("[CLIENTE] Erro ao desregistrar callback: " + e.getMessage());
                        }
                        System.out.println("[CLIENTE] Encerrando aplicação...");
                        break;

                    default:
                        System.out.println("[CLIENTE] Opção inválida!");
                }
            } while (!opcaoInicial.equals("2"));

            scanner.close();

            // Forçar o encerramento da aplicação
            System.exit(0);

        } catch (Exception e) {
            System.err.println("[CLIENTE] Exceção no cliente: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            Thread t = new Thread(new Cliente());
            t.start();
        }
    }
}
