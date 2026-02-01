import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class OperacaoFicheiros {

    private static final String OUTPUT_DIR = "outputs";

    public static String prepararDiretorioOutputs() {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return OUTPUT_DIR + File.separator;
    }

    public static void escreverParaCsv(double[][] matriz, String nomeFicheiroSaida) throws FileNotFoundException {
        String caminhoCompleto = prepararDiretorioOutputs() + nomeFicheiroSaida;
        PrintWriter out = new PrintWriter(caminhoCompleto + ".csv");
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
    public static String verificaFicheiro(Scanner sc, String nomeFicheiroEntrada, boolean interativo) throws FileNotFoundException {
        String nomeFicheiroIn = " ";
        boolean nomeValido = false;
        if(interativo) {
            System.out.print("Escreva o nome do ficheiro de entrada de dados em formato .csv: ");

            while (!nomeValido) {
                nomeFicheiroIn = sc.next();
                File importt = new File(nomeFicheiroIn);

                if (importt.exists()) {
                    // o ficheiro existe, podemos abri-lo
                    // verificamos agora se não está vazio
                    Scanner impor = new Scanner(importt);
                    if (impor.hasNextLine()) {
                        nomeValido = true;
                    } else {
                        System.out.print("O ficheiro existe mas está vazio. Por favor, insira de novo o nome de um ficheiro válido: ");
                    }
                    impor.close();
                } else {
                    // o ficheiro não existe, pedimos de novo o nome do ficheiro
                    System.out.print("O ficheiro não existe. Por favor, insira de novo o nome do ficheiro: ");
                }
            }
        }
        else{
            nomeFicheiroIn = nomeFicheiroEntrada;
        }
        return nomeFicheiroIn;
    }

    public static void preencherNomes(String[] arrNomes, String nomeFicheiroIn) throws FileNotFoundException {
        Scanner impor = new Scanner(new File(nomeFicheiroIn));
        impor.nextLine();
        for (int i = 0; i < arrNomes.length; i++) {
            String[] itensDaLinha = impor.nextLine().split(";");
            arrNomes[i] = itensDaLinha[0];
        }
        impor.close();
    }

    public static void preencherValores(double[][] arrValores, String nomeFicheiroIn) throws FileNotFoundException {
        Scanner impor = new Scanner(new File(nomeFicheiroIn));
        impor.nextLine();
        for (int i = 0; i < arrValores.length; i++) {
            String variavelApoio = impor.nextLine();
            variavelApoio = variavelApoio.replace(',', '.');
            String[] itensDaLinha = variavelApoio.split(";");
            for (int j = 1; j < itensDaLinha.length; j++) {
                arrValores[i][j - 1] = Double.parseDouble(itensDaLinha[j]);
            }
        }
        impor.close();
    }
}
