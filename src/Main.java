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
            k1 = h * f(x0,y0);
            k2 = h * f(x0 + h/2, y0 + k1/2);
            k3 = h * f(x0 + h/2, y0 + k2/2);
            k4 = h * f(x0 + h,y0 + k3);
            k = (k1 + 2 * k2 + 2 * k3 + k4)/6;
            yn = y0 +k;
            x0 = x0 + h;
            y0 = yn;
        }

        return yn;
    }

    //−β.S.I
    public static double derivadaSOrdemT(double beta, double s, double i){
        double result = 0;

        return result;
    }

    //ρ.β.S.I − γ.I + α.R
    public static double derivadaIOrdemT(double p ){
        double result = 0;

        return result;
    }

    //γ.I − α.R + (1 − ρ).β.S.I
    public static double derivadaROrdemT(){
        double result = 0;

        return result;
    }

    public static double f(double x, double y){
        double result = 0;

        return result;
    }
}