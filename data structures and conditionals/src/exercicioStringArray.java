import java.util.Arrays;

public class exercicioStringArray {
    public static void main(String[] args) {
        String[] jogos = {
                "Resident Evil 2 (Remake)",
                "Dark Souls III",
                "The Legend of Zelda: BOTW",
                "God of War",
                "Cyberpunk 2077",
                "Hades"
        };

        System.out.println("=== CATÁLOGO DE JOGOS ===");
        System.out.println("Lista completa: " + Arrays.toString(jogos));
        System.out.println("\nJogos recomendados:");

        for (int i = 0; i < jogos.length; i++) {
            System.out.println((i + 1) + ". " + jogos[i]);
        }

        // Modificando um elemento
        jogos[4] = "Cyberpunk 2077 (Atualizado)";
        System.out.println("\nApós atualização: " + jogos[4]);
    }
}
