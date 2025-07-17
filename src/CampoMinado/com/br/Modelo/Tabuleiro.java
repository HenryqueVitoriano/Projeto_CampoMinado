package CampoMinado.com.br.Modelo;

import CampoMinado.com.br.Excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    //ATRIBUTOS
    private final int linhas;
    private final int colunas;
    private final int minas;

    private final List<Campo> campos = new ArrayList<>();

    //CONSTRUTOR
    public Tabuleiro(int linhas, int colunas, int minas) {
        this.minas = minas;
        this.colunas = colunas;
        this.linhas = linhas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    //METODOS

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++){
            for (int coluna = 0; coluna < colunas; coluna++){
               campos.add(new Campo(linha, coluna));
            }
        }
    }

    //ASSOCIA TODOS OS VIZINHOS UNS AOS OUTROS
    private void associarOsVizinhos() {
        for (Campo c1 : campos){
            for (Campo c2 : campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    //SORTEA QUANTIDADE DE MINAS DE ACORDO COM O TAMANHO DO TABULEIRO
    private void sortearMinas() {
        long quantidadeDeMinas;

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            quantidadeDeMinas = campos.stream().filter(campos -> campos.isMinado()).count();

        }while (quantidadeDeMinas < minas);
    }

    //OBJETIVO FINAL DO JOGO
    public boolean objetivoAlcancado() {
      return campos.stream().allMatch(campos -> campos.objetivoAlcancado());
    }

    //RESETA TODOS OS CAMPOS E INICIA UM NOVO SORTEIO
    public void reiniciarOJogo(){
        campos.forEach(campos -> campos.reiniciar());
        this.sortearMinas();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   ");

        for (int l = 0; l < linhas; l ++){
            stringBuilder.append(" ");
            stringBuilder.append(l).append("C");
            stringBuilder.append(" ");
        }

        stringBuilder.append("\n");

        int i = 0;

        for (int l = 0; l < linhas; l++){
            stringBuilder.append(l).append("L");
            for (int c = 0; c < colunas; c++){
                stringBuilder.append("  ");
                stringBuilder.append(campos.get(i));
                stringBuilder.append(" ");
                i++;
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public void AbrirCampo(int linha, int coluna){
        try {
            campos.parallelStream().
                    filter(c -> c.getLinha() == linha && c.getColuna() == coluna).
                    findFirst().
                    ifPresent(c -> c.abrirCampo());
        } catch (ExplosaoException e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void MarcarCampo(int linha, int coluna){
        campos.parallelStream().
                filter(c -> c.getLinha() == linha && c.getColuna() == coluna).
                findFirst().
                ifPresent(c -> c.alterarMarcacao());
    }

}
