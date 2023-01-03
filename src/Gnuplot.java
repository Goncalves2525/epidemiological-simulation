import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Gnuplot {
    public static void criarScriptGnu(String nome, int metodo, int numDias, String filename, int populacao, boolean interativo) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(nome + ".gp");
        String nomeMetodo = "";
        if (metodo == 1) {
            nomeMetodo = "Euler";
        } else {
            nomeMetodo = "Runge-Kutta de ordem 4";
        }
        if (!interativo) {
            out.println("set terminal png size 640,480 \n" +
                    "set output '" + nome + ".png'\n" );
        }
        out.println(
                "set title 'Distribuicao da falsa noticia(" + nomeMetodo + ")'\n" +
                        "set xlabel 'Numero dias'\n" +
                        "set ylabel 'Populacao'\n" +
                        "set xrange [1:" + numDias + "]\n" +
                        "set yrange [1:" + populacao + "]\n" +
                        "set grid\n" +
                        "set datafile separator \";\"\n" +
                        "plot for [col=2:4] '" + filename + ".csv' using 0:col with lines title columnheader");
        out.close();
    }

    public static void gerarGrafGnuPlot(String fileName, boolean interativo) throws IOException {
        Runtime rt = Runtime.getRuntime();
        //rt.exec("gnuplot -p " + fileName + ".gp");
        if (interativo) {
            rt.exec("gnuplot -p " + fileName + ".gp");
        } else {
            rt.exec("gnuplot " + fileName + ".gp");
        }
    }
}
