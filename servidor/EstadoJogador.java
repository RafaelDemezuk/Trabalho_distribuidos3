package servidor;

import entidades.BaralhoImp;
import entidades.CartaImp;
import interfaces.ClienteCallBack;
import java.util.ArrayList;
import java.util.List;

public class EstadoJogador {
    private String nomeJogador;
    private ClienteCallBack cliente;
    private BaralhoImp baralho;
    private List<CartaImp> maoJogador;
    private List<CartaImp> maoServidor;
    private boolean jogoAtivo;
    
    public EstadoJogador(String nomeJogador, ClienteCallBack cliente) {
        this.nomeJogador = nomeJogador;
        this.cliente = cliente;
        this.baralho = new BaralhoImp();
        this.maoJogador = new ArrayList<>();
        this.maoServidor = new ArrayList<>();
        this.jogoAtivo = false;
    }
    
    public void reiniciarJogo() {
        this.baralho = new BaralhoImp();
        this.maoJogador.clear();
        this.maoServidor.clear();
        this.jogoAtivo = true;
    }
    
    public String getNomeJogador() {
        return nomeJogador;
    }
    
    public ClienteCallBack getCliente() {
        return cliente;
    }
    
    public BaralhoImp getBaralho() {
        return baralho;
    }
    
    public List<CartaImp> getMaoJogador() {
        return maoJogador;
    }
    
    public List<CartaImp> getMaoServidor() {
        return maoServidor;
    }
    
    public boolean isJogoAtivo() {
        return jogoAtivo;
    }
    
    public void setJogoAtivo(boolean jogoAtivo) {
        this.jogoAtivo = jogoAtivo;
    }
}
