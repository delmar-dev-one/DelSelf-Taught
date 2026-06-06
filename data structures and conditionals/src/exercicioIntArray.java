import java.util.Arrays;

public class exercicioIntArray {
    public static void main(String[] args) {
        // Array de pontuações de jogos
        int[] pontuacoes = {150, 320, 85, 940, 420, 210, 675};

        System.out.println("Pontuações originais: " + Arrays.toString(pontuacoes));

        // Ordenar
        Arrays.sort(pontuacoes);
        System.out.println("Pontuações ordenadas: " + Arrays.toString(pontuacoes));

        System.out.println("Maior pontuação: " + pontuacoes[pontuacoes.length - 1]);
        System.out.println("Menor pontuação: " + pontuacoes[0]);
        System.out.println("Total de jogos: " + pontuacoes.length);
    }
}
