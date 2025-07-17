package CampoMinado.com.br.Modelo;

import CampoMinado.com.br.Excecao.ExplosaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class campoTest {
    private Campo campo;

    @BeforeEach
    void iniciarCampo(){
        campo = new Campo(3,3);
    }

    @Test
    void testeVizinhoRealDistanciaUm(){
        Campo vizinho = new Campo(3,2);

       boolean resultado = campo.adicionarVizinho(vizinho);

        Assertions.assertTrue(resultado);
    }

    @Test
    void testeVizinhoRealDistanciaDois(){
        Campo vizinho = new Campo(4,4);

        boolean resultado = campo.adicionarVizinho(vizinho);

        Assertions.assertTrue(resultado);
    }

    @Test
    void testeVizinhoFalso(){
        Campo vizinho = new Campo(5,2);
        boolean resultado = campo.adicionarVizinho(vizinho);

        Assertions.assertFalse(resultado);
    }

    @Test
    void testeValorPadraoAtributoMarcado(){
        Assertions.assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlterarMarcacao(){
        campo.alterarMarcacao();
        campo.alterarMarcacao();
        Assertions.assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoNaoMarcado(){
        Assertions.assertTrue(campo.abrirCampo());
    }

    @Test
    void testeAbrirNaoMinadoMarcado(){
        campo.alterarMarcacao();
        Assertions.assertFalse(campo.abrirCampo());
    }

    @Test
    void testeAbrirMinadoNaoMarcado(){
        campo.minar();
        Assertions.assertThrows(ExplosaoException.class, () ->{
            campo.abrirCampo();
        });
    }

    @Test
    void testeAbrirMinadoMarcado(){
        campo.minar();
        campo.alterarMarcacao();

        Assertions.assertFalse(campo.abrirCampo());
    }

    @Test
    void testeAbrirComVizinhos(){

        Campo vizinho1 = new Campo(2,2);
        Campo vizinhoDoVizinho1 = new Campo(1,1);

        campo.adicionarVizinho(vizinho1);
        vizinho1.adicionarVizinho(vizinhoDoVizinho1);


        campo.abrirCampo();

        Assertions.assertTrue(vizinho1.isAberto());
        Assertions.assertTrue(vizinhoDoVizinho1.isAberto());

    }

    @Test
    void testeAbrirVizinhoMinado(){
        Campo vizinho1 = new Campo(2,2);
        Campo vizinhoDoVizinho1 = new Campo(1,1);

        vizinhoDoVizinho1.minar();

        campo.adicionarVizinho(vizinho1);
        vizinho1.adicionarVizinho(vizinhoDoVizinho1);


        campo.abrirCampo();

        Assertions.assertTrue(vizinho1.isAberto());
        Assertions.assertFalse(vizinhoDoVizinho1.isAberto());

    }

    @Test
    void testeLinhaColuna(){
        Assertions.assertNotNull(campo.getLinha());
        Assertions.assertNotNull(campo.getColuna());

    }

    @Test
    void testeObjetivoAlcancado(){
        campo.minar();
        campo.alterarMarcacao();
        Assertions.assertTrue(campo.objetivoAlcancado());
    }

    @Test
    void testeMinasVizinhanca(){
        Campo vizinho1 = new Campo(2,2);
        vizinho1.minar();

        campo.adicionarVizinho(vizinho1);

        Assertions.assertEquals(campo.minasNaVizinhanca(), 1);
    }

    @Test
    void testeReiniciar(){
        campo.reiniciar();

        Assertions.assertFalse(campo.isAberto() && campo.isMarcado() && campo.isMinado());
    }

    @Test
    void testeExibir(){
        campo.alterarMarcacao();
        Assertions.assertEquals(campo.toString(), "X");
    }

}
