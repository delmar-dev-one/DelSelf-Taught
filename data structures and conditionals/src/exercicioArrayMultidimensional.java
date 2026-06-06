import java.util.Arrays;

public class exercicioArrayMultidimensional {
    public static void main(String[] args) {
        // Array bidimensional: [plataforma][jogos]
        String[][] catalogo = {
                {"PS5", "God of War Ragnarok", "Spider-Man 2", "Final Fantasy XVI"},
                {"PC", "Cyberpunk 2077", "Baldur's Gate 3", "Hades II"},
                {"Nintendo Switch", "The Legend of Zelda: TOTK", "Animal Crossing", "Mario Kart 8"}
        };

        System.out.println("=== CATÁLOGO DE JOGOS POR PLATAFORMA ===");
        System.out.println(Arrays.deepToString(catalogo));

        System.out.println("\nDetalhado:");
        for (int i = 0; i < catalogo.length; i++) {
            System.out.println("Plataforma: " + catalogo[i][0]);
            for (int j = 1; j < catalogo[i].length; j++) {
                System.out.println("   - " + catalogo[i][j]);
            }
            System.out.println();
        }
    }
}
