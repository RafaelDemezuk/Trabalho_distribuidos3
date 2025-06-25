package entidades;

import interfaces.Carta;
import java.io.Serializable;

public class CartaImp implements Carta, Serializable {
    private String naipe;
    private int valor;
    private String simbolo;
    public CartaImp(String naipe, int valor, String simbolo) {
        this.naipe = naipe;
        this.valor = valor;
        this.simbolo = simbolo;
    }

    @Override
    public String getNaipe() {
        return naipe;
    }
    
    @Override
    public int getValor() {
        return valor;
    }

    @Override
    public String getSimbolo() {
        return simbolo;
    }
}
