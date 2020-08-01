package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;

public class ListaLeilaoTelaTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    @Test
    public void deve_AparecerUmLeilao_QuandoCarregaUmLeilaoNaApi() throws IOException {
        Leilao tvSalva = new LeilaoWebClient().salva(new Leilao("TV"));
        if (tvSalva == null) {
            fail("Leilão não foi salvo");
        }

        activity.launchActivity(new Intent());

        onView(withText("TV")).check(matches(isDisplayed()));
    }

}