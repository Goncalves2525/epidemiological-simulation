import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OperacaoFicheiros {

    public static void escreverParaCsv(double[][] matriz, String nomeFicheiroSaida) throws FileNotFoundException {

        PrintWriter out = new PrintWriter(nomeFicheiroSaida + ".csv");
        out.println("dias" + ";" + "S" + ";" + "I" + ";" + "R" + ";" + "N");
        for (int i = 0; i < matriz.length; i++) {
            out.println(i + ";" + matriz[i][1] + ";" + matriz[i][2] + ";" + matriz[i][3] + ";" + matriz[i][4]);
        }
        out.close();
    }

    public static int contarLinhasFicheiro(String nomeFicheiroIn) throws FileNotFoundException {
        int cont = 0;
        Scanner impor = new Scanner(new File(nomeFicheiroIn));
        while (impor.hasNextLine()) {
            impor.nextLine();
            cont++;
        }
        impor.close();
        return cont;
    }
}
