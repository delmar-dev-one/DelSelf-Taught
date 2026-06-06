public class exercicio02_Operadores {
    public static void main(String[] args) {
        System.out.println("=== OPERADORES E ORDEM DOS OPERADORES ===\n");

        int ordemA = 9 + 9 * 3;
        int ordemB = (9 + 9) * 3;
        System.out.println("Sem parênteses (9+9*3): " + ordemA);
        System.out.println("Com parênteses (9+9)*3: " + ordemB);

        double divisao = (double) 11 / 9;
        System.out.println("Divisão com casting: " + divisao);

        // Maior número entre três valores
        int a = 5, b = 999, c = 12;
        int maior = (a > b) ? (a > c ? a : c) : (b > c ? b : c);
        System.out.println("Maior número entre " + a + ", " + b + ", " + c + " é: " + maior);
    }
}
