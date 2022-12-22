import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static Calculos cs = new Calculos();

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        if ((args.length == 0)) {
            mostrarMenuOpcoes(sc);
        } else {
            lerArgumentosLinhaComandos(args);
        }
    }

    public static void lerArgumentosLinhaComandos(String[] argumentos) throws IOException {
        boolean inputValido = true;
        String nomeFicheiroEntrada = "";
        //-m X -p Y -t Z -d K ficheiroResultado.csv
        int paramM = 0;  //método a usar
        double paramP = 0;  //passo h 0<x<1
        int paramT = 0;  // populacao >0
        int paramD = 0;  //num dias; d>0
        double varn = 0;
        String nomeFicheiroSaida = "";

        nomeFicheiroEntrada = argumentos[0];
        if (nomeFicheiroEntrada.indexOf(".csv") == -1) {
            inputValido = false;
        }
        paramM = Integer.parseInt(argumentos[2]);
        if (paramM != 1 && paramM != 2) {
            inputValido = false;
        }
        paramP = Double.parseDouble(argumentos[4]);
        if (paramP < 0 || paramP > 1) {
            inputValido = false;
        }
        paramT = Integer.parseInt(argumentos[6]);
        if (paramT < 0) {
            inputValido = false;
        }
        paramD = Integer.parseInt(argumentos[8]);
        if (paramD < 0) {
            inputValido = false;
        }
        nomeFicheiroSaida = argumentos[9];
        if (nomeFicheiroSaida.indexOf(".csv") == -1) {
            inputValido = false;
        }
        varn = paramT / paramP;

        if (inputValido) {
            int numLinhas = contarLinhasFicheiro(nomeFicheiroEntrada) - 1;
            String[] arrNomesFicheiro = new String[numLinhas];
            preencherNomes(arrNomesFicheiro, nomeFicheiroEntrada);
            //Array de Valores
            double[][] arrValores = new double[numLinhas][4];
            preencherValores(arrValores, nomeFicheiroEntrada);

            double beta = 0;
            double gama = 0;
            double ro = 0;
            double alfa = 0;
            for (int i = 0; i < arrNomesFicheiro.length; i++) {
                beta = arrValores[i][0];
                gama = arrValores[i][1];
                ro = arrValores[i][2];
                alfa = arrValores[i][3];
                double[][] arrayResultado = new double[0][0];
                if (paramM == 1) {
                    arrayResultado = euler(paramD, paramP, paramT, beta, gama, ro, alfa);
                } else if (paramM == 2) {
                    arrayResultado = RK4(alfa, beta, gama, ro, paramD, paramP, paramT);
                }

                String nomeFicheiro = "m" + paramM + "p" + String.format("%.0f", paramP) + "t" + paramT + "d" + paramD;
                escreverParaCsv(arrayResultado, arrNomesFicheiro[i]+nomeFicheiro);
                criarScriptGnu(arrNomesFicheiro[i]+ nomeFicheiro, paramM, paramD, arrNomesFicheiro[i] + nomeFicheiro, paramT, false);
                imprimirImagem(arrNomesFicheiro[i]+ nomeFicheiro, false);
            }

        } else {
            System.out.println("parâmetros inválidos, volte a tentar!");
        }
    }

    private static String verificaFicheiro(Scanner sc) throws FileNotFoundException {
        String nomeFicheiroIn = " ";
        boolean nomeValido = false;
        System.out.print("Escreva o nome do ficheiro de entrada de dados em formato .csv: ");

        while (!nomeValido) {
            nomeFicheiroIn = sc.next();
            File importt = new File(nomeFicheiroIn);

            if (importt.exists()) {
                // o ficheiro existe, podemos abri-lo
                // verificamos agora se não está vazio
                Scanner impor = new Scanner(new File(nomeFicheiroIn));
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
        return nomeFicheiroIn;
    }

    private static double verificaPassoIntegracao(Scanner sc) {
        double h = -1;
        while (verificaIntervaloDoisNumeros(h, 0, 1) == false) {
            System.out.print("Introduza o valor do passo do intervalo ]0,1[ : ");
            h = sc.nextDouble();
        }
        return h;
    }

    private static boolean verificaIntervaloDoisNumeros(double num, int numMenor, int numMaior) {
        if (num <= numMenor || num >= numMaior) {
            return false;
        } else {
            return true;
        }
    }

    private static int verificaNumeroDias(Scanner sc) {
        int N = -1;
        while (verificaMaiorZero(N) == false) {
            System.out.print("Introduza o número de dias a considerar para análise: ");
            N = sc.nextInt();
        }
        return N;
    }

    private static int verificaTamanhoPopulacao(Scanner sc) {
        int N = -1;
        while (verificaMaiorZero(N) == false) {
            System.out.print("Introduza o tamanho da população: ");
            N = sc.nextInt();
        }
        return N;
    }


    private static boolean verificaMaiorZero(int num) {
        if (num <= 0) {
            return false;
        } else {
            return true;
        }
    }

    private static int contarLinhasFicheiro(String nomeFicheiroIn) throws FileNotFoundException {
        int cont = 0;
        Scanner impor = new Scanner(new File(nomeFicheiroIn));
        while (impor.hasNextLine()) {
            impor.nextLine();
            cont++;
        }
        impor.close();
        return cont;
    }

    private static void preencherNomes(String[] arrNomes, String nomeFicheiroIn) throws FileNotFoundException {
        Scanner impor = new Scanner(new File(nomeFicheiroIn));
        impor.nextLine();
        for (int i = 0; i < arrNomes.length; i++) {
            String[] itensDaLinha = impor.nextLine().split(";");
            arrNomes[i] = itensDaLinha[0];
        }
        impor.close();
    }

    private static void preencherValores(double[][] arrValores, String nomeFicheiroIn) throws FileNotFoundException {
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


    public static void mostrarMenuOpcoes(Scanner sc) throws IOException {
        double varn = 0;
        boolean opcaoValida = false;
        int opcao = 0;
        do {
            System.out.println("Bem vindo!");
            System.out.println("##################");
            System.out.println("(1) Método Euler");
            System.out.println("(2) Método RK4");
            System.out.println("##################");
            System.out.print("Por favor, selecione uma opção:");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Método Euler");
                    lerValoresParaIniciarMetodos(sc, opcao, true);
                    opcaoValida = true;
                    break;
                case 2:
                    System.out.println("Método RK4");
                    lerValoresParaIniciarMetodos(sc, opcao, true);
                    opcaoValida = true;
                    break;
                default:
                    break;
            }
        } while (!opcaoValida);
    }

    public static void lerValoresParaIniciarMetodos(Scanner sc, int opcao, boolean interativo) throws IOException {
        //Pede e verifica o nome do ficheiro de entrada de dados e verifica se é válido
        String nomeFicheiroIn = verificaFicheiro(sc);

        //Pede e verifica intervalo de passo de integração
        double h = verificaPassoIntegracao(sc);

        //Pede e verifica tamanho da população
        int N = verificaTamanhoPopulacao(sc);

        //Pede e verifica numero de dias da análise
        int dias = verificaNumeroDias(sc);

        //Contar linhas ficheiro menos a primeira linha (neste caso é o título)
        int numLinhas = contarLinhasFicheiro(nomeFicheiroIn) - 1;

        double numPassos = dias / h;
        //Array dos nomes
        String[] arrNomes = new String[numLinhas];
        preencherNomes(arrNomes, nomeFicheiroIn);

        //Array de Valores
        double[][] arrValores = new double[numLinhas][4];
        preencherValores(arrValores, nomeFicheiroIn);

        System.out.println("Lista de opções a escolher:");

        int escolha = 0;
        for (int i = 0; i < arrNomes.length; i++) {
            escolha = i + 1;
            System.out.println("(" + escolha + ")" + " - " + arrNomes[i]);
        }
        escolha++;
        System.out.println("(" + escolha + ")" + " - " + "Escolher todas as opções");
        System.out.print("Que pessoa pretende analisar? ");
        int opcaoSelecionada = sc.nextInt() - 1;

        double beta = 0;
        double gama = 0;
        double ro = 0;
        double alfa = 0;
        String nomeFicheiro = "m" + opcao + "p" + String.format("%.0f", h) + "t" + N + "d" + dias;
        //se a opção selecionada for igual ao maior número das opções, é porque o user pediu para analisar todos as pessoas
        if (opcaoSelecionada == escolha - 1) {
            for (int i = 0; i < arrNomes.length; i++) {
                beta = arrValores[i][0];
                gama = arrValores[i][1];
                ro = arrValores[i][2];
                alfa = arrValores[i][3];
                double[][] arrayResultado = new double[0][0];
                if (opcao == 1) {
                    arrayResultado = euler(dias, h, N, beta, gama, ro, alfa);
                } else if (opcao == 2) {
                    arrayResultado = RK4(alfa, beta, gama, ro, dias, h, N);
                }

                escreverParaCsv(arrayResultado, arrNomes[i] + nomeFicheiro);
                criarScriptGnu(arrNomes[i]+ nomeFicheiro, opcao, dias, arrNomes[i] + nomeFicheiro, N, interativo);
                imprimirImagem(arrNomes[i]+ nomeFicheiro, interativo);
            }
        }
        else {
            beta = arrValores[opcaoSelecionada][0];
            gama = arrValores[opcaoSelecionada][1];
            ro = arrValores[opcaoSelecionada][2];
            alfa = arrValores[opcaoSelecionada][3];
            double[][] arrayResultado = new double[0][0];
            if (opcao == 1) {
                arrayResultado = euler(dias, h, N, beta, gama, ro, alfa);
            } else if (opcao == 2) {
                arrayResultado = RK4(alfa, beta, gama, ro, dias, h, N);
            }

            escreverParaCsv(arrayResultado, arrNomes[opcaoSelecionada] + nomeFicheiro);
            criarScriptGnu(arrNomes[opcaoSelecionada]+ nomeFicheiro, opcao, dias, arrNomes[opcaoSelecionada] + nomeFicheiro, N, interativo);
            imprimirImagem(arrNomes[opcaoSelecionada]+ nomeFicheiro, interativo);
        }
    }

    public static void escreverParaCsv(double[][] matriz, String nomeFicheiroSaida) throws FileNotFoundException {

        PrintWriter out = new PrintWriter(nomeFicheiroSaida + ".csv");
        out.println("dias" + ";" + "S" + ";" + "I" + ";" + "R" + ";" + "N");
        for (int i = 0; i < matriz.length; i++) {
            out.println(i + ";" + matriz[i][1] + ";" + matriz[i][2] + ";" + matriz[i][3] + ";" + matriz[i][4]);
        }
        out.close();
    }

    public static void imprimirImagem(String fileName, boolean interativo) throws IOException {
        Runtime rt = Runtime.getRuntime();
        //rt.exec("gnuplot -p " + fileName + ".gp");
        if (interativo) {
            rt.exec("gnuplot -p " + fileName + ".gp");
        } else {
            rt.exec("gnuplot " + fileName + ".gp");
        }
    }

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

    public static double[][] RK4(double alfa, double beta, double gamma, double ro, int dias, double h, double varNPop) {
        double passosNumDia = 1 / h;

        double[][] resultadoYn = new double[dias][5];
        double kS = 0;
        double kI = 0;
        double kR = 0;
        int i = 0;
        double k1S = 0;
        double k2S = 0;
        double k3S = 0;
        double k4S = 0;
        double k1I = 0;
        double k2I = 0;
        double k3I = 0;
        double k4I = 0;
        double k1R = 0;
        double k2R = 0;
        double k3R = 0;
        double k4R = 0;

        double x0ParaS = 0;
        double x0ParaI = 0;
        double x0ParaR = 0;

        double ynParaS = 0;
        double ynParaI = 0;
        double ynParaR = 0;

        double y0ParaS = varNPop - 1;
        double y0ParaI = 1;
        double y0ParaR = 0;

        for (i = 0; i < dias; i++) {
            for (int j = 0; j < passosNumDia; j++) {
                //k1 = h * f(x0,y0);
                k1S = h * derivadaSOrdemT(beta, y0ParaS, y0ParaI);
                k1I = h * derivadaIOrdemT(ro, beta, y0ParaS, y0ParaI, gamma, alfa, y0ParaR);
                k1R = h * derivadaROrdemT(gamma, y0ParaI, alfa, y0ParaR, ro, beta, y0ParaS);
                //k2 = h * f(x0 + h/2, y0 + k1/2);
                k2S = h * derivadaSOrdemT(beta, y0ParaS, y0ParaI + (k1S / 2));
                k2I = h * derivadaIOrdemT(ro, beta, y0ParaS, y0ParaI, gamma, alfa, y0ParaR);
                k2R = h * derivadaROrdemT(gamma, y0ParaI, alfa, y0ParaR, ro, beta, y0ParaS);
                //k3 = h * f(x0 + h/2, y0 + k2/2);
                k3S = h * derivadaSOrdemT(beta, y0ParaS, y0ParaI + (k2S / 2));
                k3I = h * derivadaIOrdemT(ro, beta, y0ParaS, y0ParaI, gamma, alfa, y0ParaR);
                k3R = h * derivadaROrdemT(gamma, y0ParaI, alfa, y0ParaR, ro, beta, y0ParaS);
                //k4 = h * f(x0 + h,y0 + k3);
                k4S = h * derivadaSOrdemT(beta, y0ParaS, y0ParaI + k3S);
                k4I = h * derivadaIOrdemT(ro, beta, y0ParaS, y0ParaI, gamma, alfa, y0ParaR);
                k4R = h * derivadaROrdemT(gamma, y0ParaI, alfa, y0ParaR, ro, beta, y0ParaS);

                kS = (k1S + 2 * k2S + 2 * k3S + k4S) / 6;
                kI = (k1I + 2 * k2I + 2 * k3I + k4I) / 6;
                kR = (k1R + 2 * k2R + 2 * k3R + k4R) / 6;
                ynParaS = y0ParaS + kS;
                ynParaI = y0ParaI + kI;
                ynParaR = y0ParaR + kR;
                x0ParaS += h;
                x0ParaI += h;
                x0ParaR += h;
                y0ParaS = ynParaS;
                y0ParaI = ynParaI;
                y0ParaR = ynParaR;

            }
            resultadoYn[i][0] = i;
            resultadoYn[i][1] = ynParaS;
            resultadoYn[i][2] = ynParaI;
            resultadoYn[i][3] = ynParaR;
            resultadoYn[i][4] = ynParaS + ynParaI + ynParaR;
        }

        return resultadoYn;
    }

    //−β.S.I
    public static double derivadaSOrdemT(double beta, double s, double i) {
        return -beta * s * i;
    }

    //ρ.β.S.I − γ.I + α.R
    public static double derivadaIOrdemT(double ro, double beta, double s, double i, double gama, double alfa, double r) {
        return ro * beta * s * i - gama * i + alfa * r;
    }

    //γ.I − α.R + (1 − ρ).β.S.I
    public static double derivadaROrdemT(double gama, double i, double alfa, double r, double ro, double beta, double s) {
        return gama * i - alfa * r + (1 - ro) * beta * s * i;
    }


    public static double[][] euler(int dias, double passo, int populacao, double beta, double gama, double ro, double alfa) {


        //As 3 funções
        double sn = 0;
        double in = 0;
        double rn = 0;

        //Valores iniciais das 3 funções
        double s0 = populacao - 1;
        double i0 = 1;
        double r0 = 0;

        //Precisamos de saber quantos passos tem um dia para podermos exportar devidamente para ficheiro csv
        double passosNumDia = 1 / passo;

        //A matriz de valores a devolver
        double[][] matriz = new double[dias][5];

        //O método de Euler
        for (int i = 0; i < dias; i++) {
            for (int j = 0; j < passosNumDia; j++) {
                sn = s0 + passo * derivadaSOrdemT(beta, s0, i0);
                in = i0 + passo * derivadaIOrdemT(ro, beta, s0, i0, gama, alfa, r0);
                rn = r0 + passo * derivadaROrdemT(gama, i0, alfa, r0, ro, beta, s0);

                s0 = sn;
                i0 = in;
                r0 = rn;


            }
            matriz[i][0] = i;
            matriz[i][1] = sn;
            matriz[i][2] = in;
            matriz[i][3] = rn;
            matriz[i][4] = sn + in + rn;


        }

        return matriz;
    }


}