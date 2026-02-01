import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Gnuplot {
    public static void criarScriptGnu(String nome, int metodo, int numDias, String filename, int populacao, boolean interativo) throws FileNotFoundException {
        String outputDir = OperacaoFicheiros.prepararDiretorioOutputs();
        String caminhoCompleto = outputDir + nome;
        PrintWriter out = new PrintWriter(caminhoCompleto + ".gp");
        String nomeMetodo = "";
        if (metodo == 1) {
            nomeMetodo = "Euler";
        } else {
            nomeMetodo = "Runge-Kutta de ordem 4";
        }
        if (!interativo) {
            out.println("set terminal png size 640,480 \n" +
                    "set output '" + caminhoCompleto + ".png'\n" );
        }
        out.println(
                "set title 'Distribuicao da falsa noticia(" + nomeMetodo + ")'\n" +
                        "set xlabel 'Numero dias'\n" +
                        "set ylabel 'Populacao'\n" +
                        "set xrange [1:" + numDias + "]\n" +
                        "set yrange [1:" + populacao + "]\n" +
                        "set grid\n" +
                        "set datafile separator \";\"\n" +
                        "plot for [col=2:4] '" + outputDir + filename + ".csv' using 0:col with lines title columnheader");
        out.close();
    }

    public static void gerarGrafGnuPlot(String fileName, boolean interativo) throws IOException {
        String gnuplotPath = "/opt/homebrew/bin/gnuplot";
        String outputDir = OperacaoFicheiros.prepararDiretorioOutputs();
        String caminhoArquivoGp = outputDir + fileName + ".gp";

        try {
            if (interativo) {
                // Usar sh -c com nohup e & para desacoplar o processo
                String command = "nohup " + gnuplotPath + " -persist " + caminhoArquivoGp + " > /dev/null 2>&1 &";
                ProcessBuilder pb = new ProcessBuilder("sh", "-c", command);
                pb.start();
            } else {
                ProcessBuilder pb = new ProcessBuilder(gnuplotPath, caminhoArquivoGp);
                Process p = pb.start();
                p.waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
