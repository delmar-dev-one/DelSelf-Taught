import java.util.Scanner;

public class exercicio02_ConversãoStringsInt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Conversão básica
        System.out.println(">>> CONVERSÃO BÁSICA >>>");
        System.out.print("Digite um valor: ");
        String input = scanner.nextLine();

        try {
            int numero = Integer.parseInt(input);
            System.out.println("O valor convertido é: " + numero);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Por favor digite apenas valores numéricos!!");
        }

        //Soma de Strings Convertidas
        System.out.println("\n>>> SOMA DE DOIS NÚMEROS >>>");
        System.out.print("Digite o primeiro número: ");
        String num1Str = scanner.nextLine();
        System.out.print("Digite o segundo número: ");
        String num2Str = scanner.nextLine();

        // try é tentar
        try {
            int n1 = Integer.parseInt(num1Str);
            int n2 = Integer.parseInt(num2Str);
            System.out.println("Soma: " + (n1 + n2));
        } catch (NumberFormatException e) {
            System.out.println("Erro: Um dos valores não é um número válido.");
        }

        // validação de Entrada (já está no try-catch acima)

        // Listand de Strings para Integers + Média
        System.out.println("\n>>> MÉDIA DE LISTA DE NÚMEROS >>>");
        System.out.print("Quantos números deseja inserir? ");
        try {
            int quantidade = Integer.parseInt(scanner.nextLine());
            int soma = 0;

            for (int i = 0; i < quantidade; i++) {
                System.out.print("Digite o número " + (i + 1) + ": ");
                String valor = scanner.nextLine();
                int num = Integer.parseInt(valor);
                soma += num;
            }

            double media = (double) soma / quantidade;
            System.out.println("Soma total: " + soma);
            System.out.println("Média: " + media);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números válidos.");
        }

        scanner.close();
    }
}
