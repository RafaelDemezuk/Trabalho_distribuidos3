package entidades;

import interfaces.Carta;
import java.io.Serializable;
import java.util.List;

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

    @Override
    public String toString() {
        return "Carta{" +
                "naipe='" + naipe + '\'' +
                ", valor=" + valor +
                ", simbolo='" + simbolo + '\'' +
                '}';
    }

    public static String imprimirCartasLadoALado(List<CartaImp> cartas) {
        if (cartas == null || cartas.isEmpty()) {
            return "";
        }

        StringBuilder resultado = new StringBuilder();
        String[] linhas = new String[7]; // 7 linhas por carta

        // Inicializar arrays de strings para cada linha
        for (int i = 0; i < 7; i++) {
            linhas[i] = "";
        }

        // Construir cada linha para todas as cartas
        for (CartaImp carta : cartas) {
            String nomeNaipe = carta.getNaipe().length() > 6 ? carta.getNaipe().substring(0, 6) : carta.getNaipe();
            String simboloCarta = carta.getSimbolo().length() > 2 ? carta.getSimbolo().substring(0, 2)
                    : carta.getSimbolo();

            linhas[0] += "┌─────────┐ ";
            linhas[1] += String.format("│%-2s       │ ", simboloCarta);
            linhas[2] += "│         │ ";
            linhas[3] += String.format("│   %-6s│ ", nomeNaipe);
            linhas[4] += "│         │ ";
            linhas[5] += String.format("│       %2s│ ", simboloCarta);
            linhas[6] += "└─────────┘ ";
        }

        // Juntar todas as linhas
        for (String linha : linhas) {
            resultado.append(linha).append("\n");
        }

        return resultado.toString();
    }
}
