import java.util.Scanner;

public class exercicio02 {
    public static void main(String[] args) {
        System.out.println("Hello Neo, Wake up!!!");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite seu número favorito aqui: ");
        int num1 = scanner.nextInt();

        System.out.println("Digite outro número: ");
        int num2 = scanner.nextInt();

        System.out.println("Digite outro número: ");
        int num3 = scanner.nextInt();

        int soma = num1 + num2 + num3;
        int subtracao = num1 - num2 - num3;
        int divisao = num1 / num2 / num3;
        int multiplicacao = num1 * num2 * num3;

        System.out.println("Parabéns NEO, você nos informou seus números favoritos!");
        System.out.println("A soma de seus números favoritos: " + soma);
        System.out.println("A subtração de seus números favoritos: " + subtracao);
        System.out.println("A divisão de seus números favoritos: " + divisao);
        System.out.println("A multiplicação de seus números favoritos: " + multiplicacao);

        scanner.close();
    }
}
