import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Hello world");

        if(args == null){
            mostrarMenuOpcoes();
        }
        else{

        }

    }

    public static void mostrarMenuOpcoes(){
        int[] arrayOpcoes = new int[] {1, 2, 3, 4};

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

        System.out.println("Teste");
        System.out.println("teste2");
        System.out.println("Teste");
        System.out.println("teste sobreposição");
        System.out.println("sync");
    }


}