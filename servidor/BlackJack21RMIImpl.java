package servidor;

import entidades.BaralhoImp;
import entidades.CartaImp;
import interfaces.BlackJack21;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class BlackJack21RMIImpl extends UnicastRemoteObject implements BlackJack21 {
    private ServicoImpl servicoCallback;
    private BaralhoImp baralho = new BaralhoImp();
    private List<CartaImp> maoCliente = new ArrayList<>();
    private List<CartaImp> maoServidor = new ArrayList<>();

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

    @Override
    public void hit() throws RemoteException {
        CartaImp carta = baralho.compra();

        if (carta == null) {
            String mensagem = "Baralho vazio, cliente não consegue comprar mais cartas.";
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
            throw new RemoteException(mensagem);
        } else {
            maoCliente.add(carta);

            StringBuilder sb = new StringBuilder();
            sb.append("Carta comprada: ").append(carta.toString()).append("\n");
            sb.append("Mão atual do jogador: ");

            for (int i = 0; i < maoCliente.size(); i++) {
                sb.append(maoCliente.get(i).toString());
                if (i < maoCliente.size() - 1) {
                    sb.append("\n");
                }
            }

            int valorTotal = calcularValorMao(maoCliente);
            sb.append("\nValor total da mão: ").append(valorTotal);
            if( valorTotal > 21) {
                sb.append("\nVocê estourou! Valor total: ").append(valorTotal);
                servicoCallback.notificarTodosClientes(sb.toString());
                servicoCallback.notificarFimDeJogo();
                throw new RemoteException("Você estourou! Valor total: " + valorTotal);
            }else{
                String mensagem = sb.toString();
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
            }
            
        }
    }

    @Override
    public void stand() throws RemoteException {
        while (calcularValorMao(maoServidor) < 17) {
            CartaImp carta = baralho.compra();

            if (carta == null) {
                String mensagem = "Baralho vazio, a mesa não consegue comprar mais cartas.";
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
                throw new RemoteException(mensagem);
            } else {
                maoServidor.add(carta);
            }
        } ;

        StringBuilder sb = new StringBuilder();
        sb.append("Mão do servidor: ");

        for (int i = 0; i < maoServidor.size(); i++) {
            sb.append(maoServidor.get(i).toString());
            if (i < maoServidor.size() - 1) {
                sb.append("\n");
            }
        }

        int valorTotalServidor = calcularValorMao(maoServidor);
        sb.append("\nValor total da mão do servidor: ").append(valorTotalServidor);

        String mensagem = sb.toString();
        System.out.println("[SERVIDOR] " + mensagem);
        servicoCallback.notificarTodosClientes(mensagem);

        int valorTotalCliente = calcularValorMao(maoCliente);
        if (valorTotalServidor > 21) {
            mensagem = "O servidor estourou! Você ganhou com valor total: " + valorTotalCliente;
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
        } else if (valorTotalServidor == valorTotalCliente) {
            mensagem = "Empate! no caso de empate vitoria do servidor Valor total: " + valorTotalCliente;
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
        } else if (valorTotalServidor > valorTotalCliente) {
            mensagem = "O servidor ganhou com valor total: " + valorTotalServidor;
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
        } else {
            mensagem = "Você ganhou com valor total: " + valorTotalCliente;
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
        }
        servicoCallback.notificarFimDeJogo();
    }

    @Override
    public void startGame() throws RemoteException {
        maoCliente.clear();
        maoServidor.clear();
        baralho = new BaralhoImp();
        servicoCallback.notificarInicioDeJogo();
        String mensagem = "O jogo de BlackJack 21 começou!";
        System.out.println("[SERVIDOR] " + mensagem);
        servicoCallback.notificarTodosClientes(mensagem);

        try {
            CartaImp carta = baralho.compra();
            if (carta == null) {
                mensagem = "Baralho vazio, não é possível iniciar o jogo.";
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
                throw new RemoteException(mensagem);
            } else {
                maoCliente.add(carta);
            }
            carta = baralho.compra();
            if (carta == null) {
                mensagem = "Baralho vazio, não é possível iniciar o jogo.";
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
                throw new RemoteException(mensagem);
            } else {
                maoServidor.add(carta);
            }
            carta = baralho.compra();
            if (carta == null) {
                mensagem = "Baralho vazio, não é possível iniciar o jogo.";
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
                throw new RemoteException(mensagem);
            } else {
                maoCliente.add(carta);
            }
            carta = baralho.compra();
            if (carta == null) {
                mensagem = "Baralho vazio, não é possível iniciar o jogo.";
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
                throw new RemoteException(mensagem);
            } else {
                maoServidor.add(carta);
            }
            mensagem = "Cartas iniciais distribuídas\nMão do cliente: \n" + maoCliente.get(0)+"\n"+maoCliente.get(1)+ "\n Mão do servidor: \n" + maoServidor.get(0) + " \ne uma carta virada para baixo.";
            System.out.println("[SERVIDOR] " + mensagem);
            servicoCallback.notificarTodosClientes(mensagem);
            if(calcularValorMao(maoCliente) == 21) {
                servicoCallback.notificarFimDeJogo();
                
                mensagem = "BlackJack! Você ganhou com as cartas iniciais!";
                System.out.println("[SERVIDOR] " + mensagem);
                servicoCallback.notificarTodosClientes(mensagem);
                

            }
            else if(calcularValorMao(maoServidor) == 21) {
                servicoCallback.notificarFimDeJogo();
                servicoCallback.notificarFimDeJogo();
            }
                
        } catch (RemoteException e) {
            System.err.println("[SERVIDOR] Erro ao iniciar o jogo: " + e.getMessage());
        }
    }
}