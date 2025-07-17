package CampoMinado.com.br.Visao;

import CampoMinado.com.br.Modelo.Tabuleiro;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro =  new Tabuleiro(6,6,6);
        new tabuleiroConsole(tabuleiro);

    }
}
