import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Validacoes {


    public static double verificaPassoIntegracao(Scanner sc) {
        double resultado = -1;
        while (verificaIntervaloDoisNumeros(resultado, 0, 1) == false) {
            System.out.print("Introduza o valor do passo do intervalo ]0,1] : ");
            resultado = sc.nextDouble();
        }
        return resultado;
    }

    public static boolean verificaIntervaloDoisNumeros(double num, int numMenor, int numMaior) {
        if (num <= numMenor || num > numMaior) {
            return false;
        } else {
            return true;
        }
    }

    public static int verificaNumeroDias(Scanner sc) {
        int N = -1;
        while (verificaMaiorZero(N) == false) {
            System.out.print("Introduza o número de dias a considerar para análise: ");
            N = sc.nextInt();
        }
        return N;
    }

    public static int verificaTamanhoPopulacao(Scanner sc) {
        int N = -1;
        while (verificaMaiorZero(N) == false) {
            System.out.print("Introduza o tamanho da população: ");
            N = sc.nextInt();
        }
        return N;
    }


    public static boolean verificaMaiorZero(int num) {
        if (num <= 0) {
            return false;
        } else {
            return true;
        }
    }
}
