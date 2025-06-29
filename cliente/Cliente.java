package cliente;

import interfaces.BlackJack21;
import interfaces.ClienteCallBack;
import interfaces.ServicoCallBack;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente implements Runnable {
    
    private String nomeJogador;

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            ServicoCallBack servico = (ServicoCallBack) registry.lookup("ServidorCallBack");
            BlackJack21 servicoBlackJack = (BlackJack21) registry.lookup("21");
            
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("=== BEM-VINDO AO BLACKJACK 21 ===");
            System.out.print("Digite seu nome: ");
            nomeJogador = scanner.nextLine().trim();
            
            while (nomeJogador.isEmpty()) {
                System.out.print("Nome não pode estar vazio. Digite seu nome: ");
                nomeJogador = scanner.nextLine().trim();
            }

            ClienteCallBack callBack = new ClienteCallbackImpl();
            callBack.setNomeJogador(nomeJogador);
            servico.registrarCallBack(callBack);

            String opcaoInicial;

            do {
                System.out.println("\n" + "=".repeat(30));
                System.out.println("JOGADOR: " + nomeJogador.toUpperCase());
                System.out.println("=".repeat(30));
                System.out.println("[1] JOGAR BLACKJACK");
                System.out.println("[2] SAIR");
                System.out.println("=".repeat(30));
                System.out.print("Escolha uma opção: ");
                opcaoInicial = scanner.nextLine().trim();

                switch (opcaoInicial) {
                    case "1":
                        servicoBlackJack.startGame(nomeJogador);
                        String comandoJogo = "";
                        while (!comandoJogo.equals("3") && callBack.getRodada() != 0) {
                            System.out.println("\n" + "-".repeat(25));
                            System.out.println("MENU DO JOGO - " + nomeJogador.toUpperCase());
                            System.out.println("-".repeat(25));
                            System.out.println("[1] HIT (Comprar carta)");
                            System.out.println("[2] STAND (Parar)");
                            System.out.println("[3] VOLTAR AO MENU");
                            System.out.println("-".repeat(25));
                            System.out.print("Escolha uma opção: ");
                            comandoJogo = scanner.nextLine().trim();

                            switch (comandoJogo) {
                                case "1":
                                    try {
                                        servicoBlackJack.hit(nomeJogador);
                                    } catch (Exception e) {
                                        System.err.println("[ERRO] " + e.getMessage());
                                    }
                                    break;
                                case "2":
                                    try {
                                        servicoBlackJack.stand(nomeJogador);
                                    } catch (Exception e) {
                                        System.err.println("[ERRO] " + e.getMessage());
                                    }
                                    break;
                                case "3":
                                    System.out.println("Voltando ao menu principal...");
                                    break;
                                default:
                                    System.out.println("Opção inválida! Tente novamente.");
                            }
                        }
                        break;

                    case "2":
                        System.out.println("Desconectando do servidor...");
                        try {
                            servico.desregistrarCallBack(callBack);
                        } catch (Exception e) {
                            System.err.println("Erro ao desregistrar: " + e.getMessage());
                        }
                        System.out.println("Tchau, " + nomeJogador + "!");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } while (!opcaoInicial.equals("2"));

            scanner.close();
            System.exit(0);

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Thread cliente = new Thread(new Cliente());
        cliente.start();
    }
}
