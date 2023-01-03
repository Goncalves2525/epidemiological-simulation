import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        if ((args.length == 0)) {
            mostrarMenuOpcoes(sc);
        } else {
            lerArgumentosLinhaComandos(args, sc);
        }
    }

    public static void lerArgumentosLinhaComandos(String[] argumentos, Scanner sc) throws IOException {
        boolean interativo = false; //método não interativo, nunca será mudado o valor
        boolean inputValido = true;
        String nomeFicheiroEntrada = "";
        //-m X -p Y -t Z -d K ficheiroResultado.csv
        int paramM = 0;  //método a usar
        double paramP = 0;  //passo h 0<x<1
        int paramT = 0;  // populacao >0
        int paramD = 0;  //num dias; d>0
        double varn = 0;

        nomeFicheiroEntrada = Validacoes.verificaFicheiro(sc,argumentos[0],interativo);
        if (nomeFicheiroEntrada.indexOf(".csv") == -1) {
            inputValido = false;
        }
        paramM = Integer.parseInt(argumentos[2]);
        if (paramM != 1 && paramM != 2) {
            inputValido = false;
        }

        paramP = Validacoes.verificaPassoIntegracao(sc,Double.parseDouble(argumentos[4]),interativo);
        if(paramP <= -1){
            inputValido = false;
        }

        paramT = Validacoes.verificaTamanhoPopulacao(sc,Integer.parseInt(argumentos[6]),interativo);
        if (paramT < 0) {
            inputValido = false;
        }

        paramD = Validacoes.verificaNumeroDias(sc,Integer.parseInt(argumentos[8]),interativo);
        if (paramD < 0) {
            inputValido = false;
        }

        if (inputValido) {
            int numLinhas = OperacaoFicheiros.contarLinhasFicheiro(nomeFicheiroEntrada) - 1;
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
                    arrayResultado = Calculos.euler(paramD, paramP, paramT, beta, gama, ro, alfa);
                } else if (paramM == 2) {
                    arrayResultado = Calculos.RK4(alfa, beta, gama, ro, paramD, paramP, paramT);
                }

                String nomeFicheiro = "m" + paramM + "p" + String.valueOf(paramP).replace(".","") + "t" + paramT + "d" + paramD;
                OperacaoFicheiros.escreverParaCsv(arrayResultado, arrNomesFicheiro[i]+nomeFicheiro);
                Gnuplot.criarScriptGnu(arrNomesFicheiro[i]+ nomeFicheiro, paramM, paramD, arrNomesFicheiro[i] + nomeFicheiro, paramT, false);
                Gnuplot.gerarGrafGnuPlot(arrNomesFicheiro[i]+ nomeFicheiro, false);
            }

        } else {
            System.out.println("Parâmetros inválidos, volte a executar o programa com parâmetros válidos!");
        }
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
        String nomeFicheiroIn = Validacoes.verificaFicheiro(sc,"",interativo);

        //Pede e verifica intervalo de passo de integração (o 0 é inútil no caso interativo)
        double h = Validacoes.verificaPassoIntegracao(sc,0, interativo);

        //Pede e verifica tamanho da população (o 0 é inútil no caso interativo)
        int N = Validacoes.verificaTamanhoPopulacao(sc, 0, interativo);

        //Pede e verifica numero de dias da análise (o 0 é inútil no caso interativo)
        int dias = Validacoes.verificaNumeroDias(sc,0,interativo);

        //Contar linhas ficheiro menos a primeira linha (neste caso é o título)
        int numLinhas = OperacaoFicheiros.contarLinhasFicheiro(nomeFicheiroIn) - 1;

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
        String nomeFicheiro = "m" + opcao + "p" + String.valueOf(h).replace(".","") + "t" + N + "d" + dias;
        //se a opção selecionada for igual ao maior número das opções, é porque o user pediu para analisar todos as pessoas
        if (opcaoSelecionada == escolha - 1) {
            for (int i = 0; i < arrNomes.length; i++) {
                beta = arrValores[i][0];
                gama = arrValores[i][1];
                ro = arrValores[i][2];
                alfa = arrValores[i][3];
                double[][] arrayResultado = new double[0][0];
                if (opcao == 1) {
                    arrayResultado = Calculos.euler(dias, h, N, beta, gama, ro, alfa);
                } else if (opcao == 2) {
                    arrayResultado = Calculos.RK4(alfa, beta, gama, ro, dias, h, N);
                }

                OperacaoFicheiros.escreverParaCsv(arrayResultado, arrNomes[i] + nomeFicheiro);
                Gnuplot.criarScriptGnu(arrNomes[i]+ nomeFicheiro, opcao, dias, arrNomes[i] + nomeFicheiro, N, interativo);
                Gnuplot.gerarGrafGnuPlot(arrNomes[i]+ nomeFicheiro, interativo);
            }
        }
        else {
            beta = arrValores[opcaoSelecionada][0];
            gama = arrValores[opcaoSelecionada][1];
            ro = arrValores[opcaoSelecionada][2];
            alfa = arrValores[opcaoSelecionada][3];
            double[][] arrayResultado = new double[0][0];
            if (opcao == 1) {
                arrayResultado = Calculos.euler(dias, h, N, beta, gama, ro, alfa);
            } else if (opcao == 2) {
                arrayResultado = Calculos.RK4(alfa, beta, gama, ro, dias, h, N);
            }

            OperacaoFicheiros.escreverParaCsv(arrayResultado, arrNomes[opcaoSelecionada] + nomeFicheiro);
            Gnuplot.criarScriptGnu(arrNomes[opcaoSelecionada]+ nomeFicheiro, opcao, dias, arrNomes[opcaoSelecionada] + nomeFicheiro, N, interativo);
            Gnuplot.gerarGrafGnuPlot(arrNomes[opcaoSelecionada]+ nomeFicheiro, interativo);
        }
    }










}