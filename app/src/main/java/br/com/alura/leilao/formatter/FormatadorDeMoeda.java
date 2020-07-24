package br.com.alura.leilao.formatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatadorDeMoeda {
    public String transformaEmReal(double valor) {
        NumberFormat numberFormat = DecimalFormat
                .getCurrencyInstance(new Locale("pt", "br"));
        return numberFormat.format(valor);
    }
}
