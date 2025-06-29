package servidor;

import entidades.CartaImp;
import interfaces.BlackJack21;
import interfaces.ClienteCallBack;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlackJack21RMIImpl extends UnicastRemoteObject implements BlackJack21 {
    private final ServicoImpl servicoCallback;
    private final Map<String, EstadoJogador> jogadores = new HashMap<>();

    public BlackJack21RMIImpl(ServicoImpl servicoCallback) throws RemoteException {
        super();
        this.servicoCallback = servicoCallback;
    }

    private int calcularValorMao(List<CartaImp> mao) {
        int valor = 0;
        int ases = 0;

        for (CartaImp carta : mao) {
            int valorCarta = carta.getValor();
            if (valorCarta == 1) {
                ases++;
                valor += 11;
            } else if (valorCarta > 10) {
                valor += 10;
            } else {
                valor += valorCarta;
            }
        }

        while (valor > 21 && ases > 0) {
            valor -= 10;
            ases--;
        }

        return valor;
    }

    private EstadoJogador obterOuCriarJogador(String nomeJogador) throws RemoteException {
        EstadoJogador estado = jogadores.get(nomeJogador);
        if (estado == null) {

            ClienteCallBack cliente = servicoCallback.obterClientePorNome(nomeJogador);
            if (cliente == null) {
                throw new RemoteException("Cliente não encontrado para o jogador: " + nomeJogador);
            }
            estado = new EstadoJogador(nomeJogador, cliente);
            jogadores.put(nomeJogador, estado);
        }
        return estado;
    }

    @Override
    public void hit(String nomeJogador) throws RemoteException {
        EstadoJogador estado = obterOuCriarJogador(nomeJogador);
        
        if (!estado.isJogoAtivo()) {
            throw new RemoteException("Não há jogo ativo para o jogador: " + nomeJogador);
        }

        CartaImp carta = estado.getBaralho().compra();

        if (carta == null) {
            String mensagem = "Baralho vazio, " + nomeJogador + " não consegue comprar mais cartas.";
            System.out.println("[SERVIDOR] " + mensagem);
            estado.getCliente().notificar(mensagem);
            throw new RemoteException(mensagem);
        } else {
            estado.getMaoJogador().add(carta);

            StringBuilder sb = new StringBuilder();
            sb.append("=== ").append(nomeJogador.toUpperCase()).append(" ===\n");
            sb.append("Sua mão atual: \n");
            sb.append(CartaImp.imprimirCartasLadoALado(estado.getMaoJogador()));
            int valorTotal = calcularValorMao(estado.getMaoJogador());
            sb.append("\nValor total da sua mão: ").append(valorTotal);

            if (valorTotal > 21) {
                sb.append("\nVocê estourou! Valor total: ").append(valorTotal);
                estado.setJogoAtivo(false);
                estado.getCliente().notificar(sb.toString());
                estado.getCliente().fimdejogo();
                throw new RemoteException("Você estourou! Valor total: " + valorTotal);
            } else {
                String mensagem = sb.toString();
                System.out.println("[SERVIDOR] " + mensagem);
                estado.getCliente().notificar(mensagem);
            }
        }
    }

    @Override
    public void stand(String nomeJogador) throws RemoteException {
        EstadoJogador estado = obterOuCriarJogador(nomeJogador);
        
        if (!estado.isJogoAtivo()) {
            throw new RemoteException("Não há jogo ativo para o jogador: " + nomeJogador);
        }

        // Servidor joga até ter pelo menos 17
        while (calcularValorMao(estado.getMaoServidor()) < 17) {
            CartaImp carta = estado.getBaralho().compra();

            if (carta == null) {
                String mensagem = "Baralho vazio, a mesa não consegue comprar mais cartas.";
                System.out.println("[SERVIDOR] " + mensagem);
                estado.getCliente().notificar(mensagem);
                throw new RemoteException(mensagem);
            } else {
                estado.getMaoServidor().add(carta);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO FINAL - ").append(nomeJogador.toUpperCase()).append(" ===\n");
        sb.append("Mão do servidor: \n");
        sb.append(CartaImp.imprimirCartasLadoALado(estado.getMaoServidor()));
        int valorTotalServidor = calcularValorMao(estado.getMaoServidor());
        sb.append("\nValor total da mão do servidor: ").append(valorTotalServidor).append("\n\n");
        
        sb.append("Sua mão final: \n");
        sb.append(CartaImp.imprimirCartasLadoALado(estado.getMaoJogador()));
        int valorTotalJogador = calcularValorMao(estado.getMaoJogador());
        sb.append("\nValor total da sua mão: ").append(valorTotalJogador).append("\n\n");

        String mensagem = sb.toString();
        System.out.println("[SERVIDOR] " + mensagem);
        estado.getCliente().notificar(mensagem);

        // Determinar vencedor
        String resultado;
        if (valorTotalServidor > 21) {
            resultado = "VOCÊ GANHOU! O servidor estourou com " + valorTotalServidor + " pontos!";
        } else if (valorTotalServidor == valorTotalJogador) {
            resultado = "EMPATE! Em caso de empate, a casa vence. Valor: " + valorTotalJogador;
        } else if (valorTotalServidor > valorTotalJogador) {
            resultado = "VOCÊ PERDEU! Servidor: " + valorTotalServidor + " vs Você: " + valorTotalJogador;
        } else {
            resultado = "VOCÊ GANHOU! Você: " + valorTotalJogador + " vs Servidor: " + valorTotalServidor;
        }
        
        System.out.println("[SERVIDOR] " + resultado);
        estado.getCliente().notificar(resultado);
        estado.setJogoAtivo(false);
        estado.getCliente().fimdejogo();
    }

    @Override
    public void startGame(String nomeJogador) throws RemoteException {
        EstadoJogador estado = obterOuCriarJogador(nomeJogador);
        estado.reiniciarJogo();
        
        estado.getCliente().iniciodejogo();
        String mensagem = "=== JOGO INICIADO PARA " + nomeJogador.toUpperCase() + " ===";
        System.out.println("[SERVIDOR] " + mensagem);
        estado.getCliente().notificar(mensagem);

        try {
            // Distribuir duas cartas para o jogador
            for (int i = 0; i < 2; i++) {
                CartaImp carta = estado.getBaralho().compra();
                if (carta == null) {
                    mensagem = "Baralho vazio, não é possível iniciar o jogo.";
                    System.out.println("[SERVIDOR] " + mensagem);
                    estado.getCliente().notificar(mensagem);
                    throw new RemoteException(mensagem);
                }
                estado.getMaoJogador().add(carta);
            }
            
            // Distribuir duas cartas para o servidor
            for (int i = 0; i < 2; i++) {
                CartaImp carta = estado.getBaralho().compra();
                if (carta == null) {
                    mensagem = "Baralho vazio, não é possível iniciar o jogo.";
                    System.out.println("[SERVIDOR] " + mensagem);
                    estado.getCliente().notificar(mensagem);
                    throw new RemoteException(mensagem);
                }
                estado.getMaoServidor().add(carta);
            }

            // Mostrar as cartas do jogador
            StringBuilder sb = new StringBuilder();
            sb.append("=== SUAS CARTAS ===\n");
            sb.append(CartaImp.imprimirCartasLadoALado(estado.getMaoJogador()));
            int valorTotalJogador = calcularValorMao(estado.getMaoJogador());
            sb.append("Valor total da sua mão: ").append(valorTotalJogador).append("\n\n");

            // Mostrar apenas a primeira carta do servidor
            sb.append("=== CARTAS DO SERVIDOR ===\n");
            List<CartaImp> cartaVisivel = new ArrayList<>();
            cartaVisivel.add(estado.getMaoServidor().get(0)); 
            sb.append(CartaImp.imprimirCartasLadoALado(cartaVisivel));

            sb.append("┌─────────┐\n");
            sb.append("│░░░░░░░░░│\n");
            sb.append("│░░░░░░░░░│\n");
            sb.append("│░░░░░░░░░│\n");
            sb.append("│░░░░░░░░░│\n");
            sb.append("│░░░░░░░░░│\n");
            sb.append("└─────────┘\n");

            mensagem = sb.toString();
            System.out.println("[SERVIDOR] " + mensagem);
            estado.getCliente().notificar(mensagem);

            // Verificar BlackJack natural
            if (valorTotalJogador == 21) {
                int valorTotalServidor = calcularValorMao(estado.getMaoServidor());
                if (valorTotalServidor == 21) {
                    mensagem = "EMPATE! Ambos têm BlackJack natural!";
                } else {
                    mensagem = "BLACKJACK NATURAL! Você ganhou com as cartas iniciais!";
                }
                System.out.println("[SERVIDOR] " + mensagem);
                estado.getCliente().notificar(mensagem);
                estado.setJogoAtivo(false);
                estado.getCliente().fimdejogo();
            } else if (calcularValorMao(estado.getMaoServidor()) == 21) {
                mensagem = "O servidor tem BlackJack natural! Você perdeu.";
                System.out.println("[SERVIDOR] " + mensagem);
                estado.getCliente().notificar(mensagem);
                estado.setJogoAtivo(false);
                estado.getCliente().fimdejogo();
            } else {
                mensagem = "Jogo iniciado! Você pode comprar mais cartas (HIT) ou parar (STAND).";
                System.out.println("[SERVIDOR] " + mensagem);
                estado.getCliente().notificar(mensagem);
            }
        } catch (RemoteException e) {
            mensagem = "Erro ao iniciar o jogo: " + e.getMessage();
            System.err.println("[SERVIDOR] " + mensagem);
            estado.getCliente().notificar(mensagem);
            throw e;
        }
    }
}