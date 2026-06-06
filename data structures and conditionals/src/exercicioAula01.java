import java.util.Scanner;

public class exercicioAula01 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // escrevendo em chamadas em ingles
        System.out.println("Welcome to Orion ITAO School(do Marcel), come to us to the future.");
        System.out.println("Today we started to work in Java, one of the most popular codes in the world.");
        System.out.println();

        System.out.println(">> CALCULADORA SIMPLES DE SOMA >>>");

        int num1 = lerNumeroValido(scanner, "Digite aqui o primeiro número: ");
        int num2 = lerNumeroValido(scanner, "Digite aqui o segundo número: ");

        // Cálculos
        int soma = num1 + num2;
        int subtracao = num1 - num2;
        int multiplicacao = num1 * num2;

        // Resultados
        System.out.println("\n>>> RESULTADOS <<<");
        System.out.println("A soma dos dois números é: " + soma);
        System.out.println("Subtração: " + subtracao);
        System.out.println("Multiplicação: " + multiplicacao);

        if (num2 != 0) {
            System.out.println("Divisão: " + (num1 / num2));
        } else {
            System.out.println("Divisão: Não é possível dividir por zero.");
        }

        System.out.println("\nParabéns! Você concluiu o exercício da Aula 01.");

        scanner.close();
    }

    // Método auxiliar para evitar erro de InputMismatchException pelo menos é para fazer
    private static int lerNumeroValido(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Erro: Por favor, digite apenas um número inteiro!");
                scanner.next(); // Limpa o valor inválido
            }
        }
    }
}
