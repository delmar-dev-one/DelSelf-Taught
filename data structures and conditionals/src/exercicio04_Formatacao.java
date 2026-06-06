import java.text.NumberFormat;

public class exercicio04_Formatacao {
    public static void main(String[] args) {
        System.out.println("=== FORMATAÇÃO DE NÚMEROS ===\n");

        NumberFormat moeda = NumberFormat.getCurrencyInstance();

        System.out.println("Valor: " + moeda.format(150.25));
        System.out.println("Valor: " + moeda.format(1200.00));
        System.out.println("Valor: " + moeda.format(999.99));
        System.out.println("Valor: " + moeda.format(12345.6789));
    }
}
