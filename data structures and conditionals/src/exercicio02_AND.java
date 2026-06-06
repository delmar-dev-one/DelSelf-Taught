public class exercicio02_AND {
    public static void main(String[] args) {
        boolean x = true;
        boolean y = false;

        System.out.println("=== OPERADOR LÓGICO AND (&&) ===");
        System.out.println("x && y: " + (x && y));

        x = true;
        y = true;
        System.out.println("x && y: " + (x && y));

        // Preço de produto
        System.out.println("\n--- Exemplo Prático (Preço do Produto) ---");
        int precoDoProduto = 25;
        int precoDoProduto2 = 15;
        boolean botaoComprar = precoDoProduto2 >= 10 && precoDoProduto <= 25;
        System.out.println("Pode comprar o produto? " + botaoComprar);

        precoDoProduto = 9;
        botaoComprar = precoDoProduto >= 10 && precoDoProduto <= 25;
        System.out.println("Pode comprar o produto (R$9)? " + botaoComprar);
    }
}
