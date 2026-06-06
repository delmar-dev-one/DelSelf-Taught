public class exercicio05_Condicionais {
    public static void main(String[] args) {
        System.out.println(">>> OPERADORES DE COMPARAÇÃO, LÓGICOS E IF-ELSE >>>\n");

        int idade = 18;
        boolean temCarteira = true;
        boolean temDinheiro = true;

        if (idade >= 18 && temCarteira && temDinheiro) {
            System.out.println("Pode dirigir e comprar o carro!");
        } else if (idade >= 18 && temCarteira) {
            System.out.println("Pode dirigir, mas precisa de dinheiro.");
        } else {
            System.out.println("Não pode dirigir você é de menor.");
        }

        // Exemplo de financiamento
        boolean temEmprego = true;
        boolean temEntrada = false;
        boolean podeFinanciar = (temEmprego || temEntrada);
        System.out.println("Pode financiar? " + podeFinanciar);
    }
}
