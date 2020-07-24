package br.com.alura.leilao.formatter;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FormatadorDeMoedaTest {

    @Test
    public void deve_FormatarParaMoedaReal_QuandoReceberValorDouble(){
        FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();

        String moedaEmReal = formatadorDeMoeda.transformaEmReal(200.0);

        assertThat(moedaEmReal, is(equalTo("R$ 200,00")));
    }

}