package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes() {
        Context context = Mockito.mock(Context.class);
        ListaLeilaoAdapter adapter = Mockito.spy(new ListaLeilaoAdapter(context));
        Mockito.doNothing().when(adapter).atualizaLista();

        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));
        int quantidadeLeiloesDevolvida = adapter.getItemCount();

        Mockito.verify(adapter).atualizaLista();
        assertThat(quantidadeLeiloesDevolvida, is(2));
    }

}