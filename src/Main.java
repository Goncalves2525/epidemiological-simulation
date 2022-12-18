import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);

        //Pede o nome do ficheiro de entrada de dados e verifica se é válido
        String nomeFicheiroIn = nomeFicheiroVerificado(sc);

        //Contar linhas ficheiro menos a primeira linha (neste caso é o título)
        int numLinhas = contarLinhasFicheiro(nomeFicheiroIn)-1;

        //Array dos nomes
        String [] arrNomes = new String[numLinhas];
        preencherNomes(arrNomes,nomeFicheiroIn);

        //Array de Valores
        double [][] arrValores = new double[numLinhas][4];
        preencherValores(arrValores,nomeFicheiroIn);


        mostrarMenuOpcoes(sc);

    }

    private static String nomeFicheiroVerificado(Scanner sc) {
        String nomeFicheiroIn = " ";
        boolean nomeValido = false;

        while (!nomeValido) {
            System.out.print("Escreve o nome do ficheiro de entrada de dados em formato .csv: ");
            nomeFicheiroIn = sc.next();
            File impor = new File(nomeFicheiroIn);

            if (impor.exists()) {
                // o ficheiro existe, podemos abri-lo
                nomeValido = true;
            } else {
                // o ficheiro não existe, pedimos de novo o nome do ficheiro
                System.out.println("O ficheiro não existe. Por favor, insira de novo o nome do ficheiro: ");
            }
        }
        return nomeFicheiroIn;
    }

    private static int contarLinhasFicheiro(String nomeFicheiroIn) throws FileNotFoundException {
        int cont = 0;
        Scanner impor = new Scanner (new File(nomeFicheiroIn));
        while (impor.hasNextLine()) {
            impor.nextLine();
            cont++;
        }
        impor.close();
        return cont;
    }

    private static void preencherNomes(String [] arrNomes,String nomeFicheiroIn) throws FileNotFoundException {
        Scanner impor = new Scanner (new File(nomeFicheiroIn));
        impor.nextLine();
        for (int i = 0; i < arrNomes.length; i++) {
            String[] itensDaLinha = impor.nextLine().split(";");
            arrNomes[i]=itensDaLinha[0];
        }
        impor.close();
    }

    private static void preencherValores(double [][] arrValores, String nomeFicheiroIn) throws FileNotFoundException {
        Scanner impor = new Scanner (new File(nomeFicheiroIn));
        impor.nextLine();
        for (int i = 0; i < arrValores.length; i++) {
            String variavelApoio = impor.nextLine();
            variavelApoio = variavelApoio.replace(',', '.');
            String[] itensDaLinha = variavelApoio.split(";");
            for (int j = 1; j < itensDaLinha.length; j++) {
                arrValores[i][j-1]=Double.parseDouble(itensDaLinha[j]);
            }
        }
        impor.close();
    }


    public static void mostrarMenuOpcoes(Scanner sc){

        int[] arrayOpcoes = new int[] {1, 2, 3, 4};
        boolean opcaoValida = false;
        int opcao = 0;
        do{
            System.out.println("Bem vindo!");
            System.out.println("##################");
            System.out.println("(1) aaaa");
            System.out.println("(2) bbbb");
            System.out.println("(3) cccc");
            System.out.println("(4) dddd");
            System.out.println("##################");
            System.out.print("Por favor, selecione uma opção:");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("opçao1");
                    opcaoValida = true;
                    break;
                case 2:
                    System.out.println("opçao2");
                    opcaoValida = true;
                    break;
                case 3:
                    System.out.println("opçao3");
                    opcaoValida = true;
                    break;
                case 4:
                    System.out.println("opçao4");
                    opcaoValida = true;
                    break;
                default:
                    break;
            }
        }while (!opcaoValida);
    }


    public static double RK4(double x0, double y0, double n, double h){
        double yn = 0;
        double k = 0;
        int i = 0;
        double k1,k2,k3,k4;


        for (i = 0; i < n; i ++){
//            k1 = h * f(x0,y0);
//            k2 = h * f(x0 + h/2, y0 + k1/2);
//            k3 = h * f(x0 + h/2, y0 + k2/2);
//            k4 = h * f(x0 + h,y0 + k3);
//            k = (k1 + 2 * k2 + 2 * k3 + k4)/6;
//            yn = y0 +k;
//            x0 = x0 + h;
//            y0 = yn;
        }

        return yn;
    }

    //−β.S.I
    public static double derivadaSOrdemT(double beta, double s, double i){
        return -beta * s * i;
    }

    //ρ.β.S.I − γ.I + α.R
    public static double derivadaIOrdemT(double ro, double beta, double s, double i, double gama, double alfa, double r ){
        return ro * beta * s * i - gama * i + alfa * r;
    }

    //γ.I − α.R + (1 − ρ).β.S.I
    public static double derivadaROrdemT(double gama, double i, double alfa, double r, double ro, double beta, double s){
        return gama * i - alfa * r + (1 - ro) * beta * s * i;
    }


    public static double[][] euler(int dias, int n, double h, int N, double beta, double gama, double ro, double alfa){
        //as 3 funções a retornar após o método
        double[] funcoes = new double[3];

        //As 3 funções
        double sn = 0;
        double in = 0;
        double rn = 0;

        //Valores iniciais das 3 funções
        double s0 = N - 1;
        double i0 = 1;
        double r0 = 0;

        //Precisamos de saber quantos passos tem um dia para podermos exportar devidamente para ficheiro csv
        double passosNumDia = 1 / h;

        double[][] matriz = new double[dias][3];

        for (int i = 0; i < dias; i++) {
            for (int j = 0; j < passosNumDia; j++) {
                sn = s0 + 0.1 * derivadaSOrdemT(beta, s0, i0);
                in = i0 + 0.1 * derivadaIOrdemT(ro, beta, s0, i0, gama, alfa, r0);
                rn = r0 + 0.1 * derivadaROrdemT(gama, i0, alfa, r0, ro, beta, s0);

                s0 = sn;
                i0 = in;
                r0 = rn;
            }
            matriz[i][0] = sn;
            matriz[i][1] = in;
            matriz[i][2] = rn;


        }

        return matriz;
    }
}