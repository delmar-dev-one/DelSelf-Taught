
// Bibliotecas import java.util.Scanner;
import java.util.Scanner;
import java.util.Random;

public class JogoDaForcaAvancado {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean jogarNovamente = true;


        System.out.println("================================================");
        System.out.println("Bem-vindo ao Jogo da Forca Avançado! Desenvolvido por: Lidelmar");
        System.out.println("================================================");

        while (jogarNovamente) {

            // Chama o método para jogar uma partida
            jogarUmaPartida(scanner, random);

            System.out.print("\nDeseja jogar novamente? (s/n): ");
            String resposta = scanner.nextLine().toUpperCase();
            jogarNovamente = resposta.equals("s");

        }

        // Mensagem de despedida
        System.out.println("\nObrigado por jogar! Até a próxima!");
        scanner.close();

    }

    // Método para jogar uma partida do jogo da forca
    public static void jogarUmaPartida(Scanner scanner, Random random) {
        // ========================= Categoria e Palavras ==================
        String[][] categorias = {
            {"ESPACO", "LUA", "SOL", "ESTRELA", "MARTE", "ROCKET", "GALAXIA"},
            {"ANIMAIS", "GATO", "CACHORRO", "ELEFANTE", "TIGRE", "PINGUIM"},
            {"COMIDA", "PIZZA", "HAMBURGER", "SUSHI", "TACO", "LASANHA"},
            {"TECNOLOGIA", "COMPUTADOR", "CELULAR", "ROBO", "INTERNET", "SOFTWARE"}
            
    };

        // Array para armazenar os nomes das categorias
        String[] nomesCategorias = {"ESPACO", "ANIMAIS", "COMIDA", "TECNOLOGIA"};

        System.out.println("\nEscolha sua Categoria:");

        // Loop para exibir as categorias disponíveis
        for (int i = 0; i < nomesCategorias.length; i++) {
            // Exibe as categorias disponíveis para o jogador escolher
            System.out.println((i + 1) + ". " + nomesCategorias[i]);
        }

        System.out.println("\nDigite o número da categoria: ");
        int escolhaCategoria = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        if (escolhaCategoria < 1 || escolhaCategoria > categorias.length) {
            // Se a escolha for inválida, exibe uma mensagem e define a categoria padrão como ESPACO
            System.out.println("Escolha inválida. Jogando com a categoria padrão: ESPACO.");
            escolhaCategoria = 1; // Define a categoria padrão como ESPACO
        }

        String[] palavras = categorias[escolhaCategoria - 1]; // Obtém as palavras da categoria escolhida
        String palavraSecreta = palavras[random.nextInt(palavras.length)].toUpperCase(); // Seleçao aleatória da palavra secreta

        //========================= Variáveis do Jogo ==================
        char letrasDescobertas[] = new char[palavraSecreta.length()]; // Array para armazenar as letras descobertas
        for (int i = 0; i < letrasDescobertas.length; i++) {
            letrasDescobertas[i] = '_'; // Inicializa o array com underscores para representar as letras não descobertas
        }

        int tentativasRestantes = 6; // Número de tentativas restantes
        String letrasUsadas = ""; // String para armazenar as letras já usadas

        System.out.println("\n Tem escolhido: " + nomesCategorias[escolhaCategoria - 1]);
        System.out.println("Adivinhe a palavra secreta:\n");

        // ================= LOOP PRINCIPAL DO JOGO =================
        while (tentativasRestantes > 0 && !palavraCompleta(letrasDescobertas)) {

            desenharForca(tentativasRestantes);

            System.out.println("Palavra: " + formatarPalavra(letrasDescobertas));
            System.out.println("Tentativas restantes: " + tentativasRestantes);
            System.out.println("Letras usadas: "
                    + (letrasUsadas.isEmpty() ? "Nenhuma" : letrasUsadas));

            System.out.print("\nDigite uma letra: ");
            String input = scanner.nextLine().toUpperCase().trim();

            // Validação
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println(" Por favor, digite apenas UMA letra válida!");
                continue;
            }

            char letra = input.charAt(0);

            // Verifica letra repetida
            if (letrasUsadas.contains(String.valueOf(letra))) {
                System.out.println(" Você já tentou a letra '" + letra + "'!");
                continue;
            }

            letrasUsadas += letra + " ";

            boolean acertou = false;

            // Atualiza letras descobertas
            for (int i = 0; i < palavraSecreta.length(); i++) {

                if (palavraSecreta.charAt(i) == letra) {
                    letrasDescobertas[i] = letra;
                    acertou = true;
                }
            }

            if (acertou) {
                System.out.println("Acertou! A letra '" + letra + "' está na palavra.");
            } else {
                System.out.println(" Errou! A letra '" + letra + "' não está na palavra.");
                tentativasRestantes--;
            }

            System.out.println("-------------------------------------");
        }

        // ================= RESULTADO FINAL =================
        System.out.println("\n====================================");
        desenharForca(tentativasRestantes);

        if (palavraCompleta(letrasDescobertas)) {
            System.out.println("PARABÉNS! Você acertou a palavra: " + palavraSecreta);
            System.out.println("Você é um verdadeiro mestre da forca! ");
        } else {
            System.out.println("Game Over! A palavra era: " + palavraSecreta);
        }

        System.out.println("====================================");

        scanner.close();
    }

    // ================= MÉTODOS AUXILIARES =================

    public static void desenharForca(int tentativas) {

        int erros = 6 - tentativas;

        System.out.println(" +---+");
        System.out.println(" |   |");

        switch (erros) {

            case 0:
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                break;

            case 1:
                System.out.println(" O   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                break;

            case 2:
                System.out.println(" O   |");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("     |");
                break;

            case 3:
                System.out.println(" O   |");
                System.out.println("/|   |");
                System.out.println("     |");
                System.out.println("     |");
                break;

            case 4:
                System.out.println(" O   |");
                System.out.println("/|\\  |");
                System.out.println("     |");
                System.out.println("     |");
                break;

            case 5:
                System.out.println(" O   |");
                System.out.println("/|\\  |");
                System.out.println("/    |");
                System.out.println("     |");
                break;

            case 6:
                System.out.println(" O   |");
                System.out.println("/|\\  |");
                System.out.println("/ \\  |");
                System.out.println("     |");
                break;
        }

        System.out.println("======\n");
    }

    public static String formatarPalavra(char[] array) {

        StringBuilder sb = new StringBuilder();

        for (char c : array) {
            sb.append(c).append(" ");
        }

        return sb.toString().trim();
    }

    public static boolean palavraCompleta(char[] array) {

        for (char c : array) {

            if (c == '_') {
                return false;
            }
        }

        return true; 
    }

}
