package entidades;

import java.io.Serializable;

import interfaces.Baralho;

import entidades.CartaImp;


public class BaralhoImp implements Baralho {
    private ArrayList<CartaImp> baralho= new ArrayList();

    public BaralhoImp() {
        for(int i=0;i<52;i++){
            String naipe;
            int valor;
            String simbolo;

            if (i < 13) {
                naipe = "Copas";
                valor = i + 1;
                if(valor == 11) {
                    simbolo = "J"; // Valete
                } else if (valor == 12) {
                    simbolo = "Q"; // Dama
                } else if (valor == 13) {
                    simbolo = "K"; // Rei
                } else if(valor == 1) {
                    simbolo = "A"; // Ás
                }else{
                    simbolo = Integer.toString(valor); // Números de 2 a 10
                }
            } else if (i < 26) {
                naipe = "Ouros";
                valor = i - 12;
                if(valor == 11) {
                    simbolo = "J"; // Valete
                } else if (valor == 12) {
                    simbolo = "Q"; // Dama
                } else if (valor == 13) {
                    simbolo = "K"; // Rei
                } else if(valor == 1) {
                    simbolo = "A"; // Ás
                }else{
                    simbolo = Integer.toString(valor); // Números de 2 a 10
                }
            } else if (i < 39) {
                naipe = "Espadas";
                valor = i - 25;
                if(valor == 11) {
                    simbolo = "J"; // Valete
                } else if (valor == 12) {
                    simbolo = "Q"; // Dama
                } else if (valor == 13) {
                    simbolo = "K"; // Rei
                } else if(valor == 1) {
                    simbolo = "A"; // Ás
                }else{
                    simbolo = Integer.toString(valor); // Números de 2 a 10
                }
            } else {
                naipe = "Paus";
                valor = i - 38;
                if(valor == 11) {
                    simbolo = "J"; // Valete
                } else if (valor == 12) {
                    simbolo = "Q"; // Dama
                } else if (valor == 13) {
                    simbolo = "K"; // Rei
                } else if(valor == 1) {
                    simbolo = "A"; // Ás
                }else{
                    simbolo = Integer.toString(valor); // Números de 2 a 10
                }
            }

            baralho.add(new CartaImp(naipe, valor, simbolo));
        } 
    }

    
}
