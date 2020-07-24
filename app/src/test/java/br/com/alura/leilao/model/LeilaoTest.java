package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        String descricaoDevolvida = CONSOLE.getDescricao();

//        assertEquals("Console", descricaoDevolvida);
        assertThat(descricaoDevolvida, is(equalTo("Console")));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

//        assertEquals(200.0, maiorLanceDevolvido, DELTA);
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Regina"), 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Regina"), 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        final Leilao console = new LeilaoBuilder("Console")
                .lance(ALEX, 200.0)
                .lance(new Usuario("Regina"), 400.0)
                .lance(ALEX, 500.0)
                .build();

        List<Lance> tresMaioresLancesDevolvidos = console.tresMaioresLances();

        assertThat(tresMaioresLancesDevolvidos,
                both(Matchers.<Lance>hasSize(3))
                        .and(contains(
                                new Lance(ALEX, 500.0),
                                new Lance(new Usuario("Regina"), 400.0),
                                new Lance(ALEX, 200.0))));
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLances() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Regina"), 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        final Usuario REGINA = new Usuario("Regina");
        final Leilao console = new LeilaoBuilder("Console")
                .lance(ALEX, 200.0)
                .lance(REGINA, 400.0)
                .lance(ALEX, 500.0)
                .lance(REGINA, 550.0)
                .build();

        List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(550.0, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        console.propoe(new Lance(ALEX, 620.0));

        List<Lance> tresMaioresLancesDevolvidosParaCincoLances = console.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(620.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(550.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(0.0, menorLanceDevolvido, DELTA);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void deve_LancarException_QuandoForMenorQueOMaiorLance() {
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Regina"), 400.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void deve_LancarException_QuandoForOMesmoUsuarioDoUltimoLance() {
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(ALEX, 550.0));
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void deve_LancarException_QuandoUsuarioDerCincoLances() {
        final Usuario REGINA = new Usuario("Regina");
        new LeilaoBuilder("Console")
                .lance(ALEX, 100.0)
                .lance(REGINA, 200.0)
                .lance(ALEX, 300.0)
                .lance(REGINA, 400.0)
                .lance(ALEX, 500.0)
                .lance(REGINA, 600.0)
                .lance(ALEX, 700.0)
                .lance(REGINA, 800.0)
                .lance(ALEX, 900.0)
                .lance(REGINA, 1000.0)
                .lance(ALEX, 1100.0)
                .build();
    }

}