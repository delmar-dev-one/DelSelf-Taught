public class exercicio04_IfElse {
    public static void main(String[] args) {
        System.out.println(">>> USO DE IF e ELSE >>>\n");

        double valorDoCarro = 150000;

        if (valorDoCarro > 120001) {
            System.out.println("Não permitir a compra, pois está acima do preco máximo.");
        } else if (valorDoCarro >= 12000 && valorDoCarro <= 120000) {
            System.out.println("Liberado para compra.");
        } else {
            System.out.println("Não tem carros nessa faixa de preço, não liberar compra!");
        }

        // extra de if-else-if
        System.out.println("\n-Testando as Nota");
        int nota = 85;
        if (nota >= 90) {
            System.out.println("Aprovado com A! Parebéns");
        } else if (nota >= 80) {
            System.out.println("Aprovado com B! Parabéns");
        } else if (nota >= 70) {
            System.out.println("Aprovado com C! Parabéns");
        } else {
            System.out.println("Reprovado");
        }
    }
}
