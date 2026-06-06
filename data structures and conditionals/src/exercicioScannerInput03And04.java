import java.util.Scanner;
import java.util.InputMismatchException;

public class exercicioScannerInput03And04 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configurar para aceitar vírgula como decimal (melhor para alunos brasileiros)
        scanner.useLocale(java.util.Locale.forLanguageTag("pt-BR"));

        System.out.println(">>> CADASTRO DE JOGADOR <<<");

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite sua idade: ");
        int idade = scanner.nextInt();

        System.out.print("Digite sua altura (ex: 1.75 | 1,75): ");
        double altura = 0;
        try {
            altura = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite a altura com ponto ou vírgula (ex: 1.75)");
            scanner.nextLine(); // limpa o buffer
            // Tenta ler novamente
            altura = scanner.nextDouble();
        }

        System.out.print("Você tem experiência em games? (true/false): ");
        boolean experiencia = scanner.nextBoolean();

        System.out.println("\n> DADOS DO JOGADOR <");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Altura: " + altura + "m");
        System.out.println("Experiência em games: " + experiencia);

        scanner.close();
    }
}