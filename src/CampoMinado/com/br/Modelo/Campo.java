package CampoMinado.com.br.Modelo;

import CampoMinado.com.br.Excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class Campo {
    //ATRIBUTOS
    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    //CONSTRUTOR CAMPO
    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    //METODOS

    //METODO QUE ADICIONA VIZINHO AO CAMPO REFERENCIADO
    boolean adicionarVizinho(Campo vizinho) {
        //Faz com que o campo não se adicione como vizinho
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;

        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    //METODO QUE ALTERA A MARCACAO DO CAMPO
    void alterarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }
    }

    //METODO QUE ABRE O CAMPO
    boolean abrirCampo() {
        if (!aberto && !marcado) {
            aberto = true;

            if (minado) { // VERIFICA SE O CAMPO ESTA MINADO
                throw new ExplosaoException(); // CASO ESTEJA LANÇA UMA EXCEÇÃO
            }

            if (vizinhancaSegura()) { // VERIFICA SE OS VIZINHOS ADICIONADOS NAO ESTAO MINADOS E OS ABRE
                vizinhos.forEach(vizinho -> vizinho.abrirCampo());
            }

            return true;

        } else {
            return false;
        }
    }

    // VERIFICA A SEGURANÇA DOS CAMPOS VIZINHOS
    boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(vizinho -> vizinho.minado);
    }

    //MINA UM CAMPO
    void minar() {
        minado = true;
    }


    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public String toString() {
        if (marcado) {
            return "X";
        } else if (aberto && minado) {
            return "#";
        }else if (aberto && minasNaVizinhanca() > 0){
            return Long.toString(minasNaVizinhanca());
        }else if (aberto){
            return " ";
        }else {
            return "?";
        }
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }

    long minasNaVizinhanca() {
        return vizinhos.stream().filter(vizinho -> vizinho.minado).count();
    }

    //GETTERS AND SETTERS

    //VERIFICA SE O CAMPO ESTÁ MINADO
    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean isMinado() {
        return minado;
    }

   void setAberto(boolean aberto) {
        this.aberto = aberto;
    }
}
