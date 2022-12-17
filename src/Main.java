import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(args);
        if(args == null){

        }
        else{
        }
        mostrarMenuOpcoes(sc);

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