package cliente;

import java.io.Serializable;

import interfaces.Carta;

public class CartaImp implements Carta, Serializable {
    private String naipe;
    private int valor;

    public CartaImp(String naipe, int valor) {
        this.naipe = naipe;
        this.valor = valor;
    }

    @Override
    public String getNaipe() {
        return naipe;
    }
    
    @Override
    public int getValor() {
        return valor;
    }
}
