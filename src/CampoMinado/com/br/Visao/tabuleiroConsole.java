package CampoMinado.com.br.Visao;

import CampoMinado.com.br.Excecao.ExplosaoException;
import CampoMinado.com.br.Excecao.SairExcecao;
import CampoMinado.com.br.Modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class tabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public tabuleiroConsole(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    //EXECUTA O JOGO ENQUANTO O JOGADOR AINDA QUISER JOGAR
    private void executarJogo() {
        try{
            boolean continuar = true;

            while (continuar){
                cicloDoJogo();

                System.out.println("Outra partida? (S/n)");
                String resposta = entrada.nextLine();
                if ("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                }else {
                    tabuleiro.reiniciarOJogo();
                }
            }
        }catch (SairExcecao excecao){
            System.out.println("OBRIGADO POR JOGAR!");
        }finally {
            entrada.close();
        }
    }

    //LOGICA DO JOGO
    private void cicloDoJogo() {
        try{
            while (!tabuleiro.objetivoAlcancado()){
                System.out.println(tabuleiro);

                String digitado = capturarValorDigitado("DIGITE L/C OU SAIR");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar");

                if ("1".equalsIgnoreCase(digitado)){
                    tabuleiro.AbrirCampo(xy.next(), xy.next());
                }else if ("2".equalsIgnoreCase(digitado)){
                    tabuleiro.MarcarCampo(xy.next(), xy.next());
                }
            }

            System.out.println("Você ganhou!");

        }catch (ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!");
        }
    }

    //AUXILIAR DE CAPTURA DE TEXTO
    private String capturarValorDigitado(String texto){
        System.out.println(texto);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)){
            throw new SairExcecao();
        }

        return digitado;
    }
}
