public class exercicio01_Constantes {
    // Constantes
    public static final double TAXA_JUROS_FIXA = 11.00;
    public static final double SALARIO_MINIMO = 1410.0;
    public static final double TAXA_INSS = 0.075;

    public static void main(String[] args) {
        System.out.println("=== CONSTANTES EM JAVA ===");
        System.out.println("Taxa de Juros Fixa: " + TAXA_JUROS_FIXA + "%");

        double salarioBruto = SALARIO_MINIMO;
        double inss = salarioBruto * TAXA_INSS;
        double salarioLiquido = salarioBruto - inss;

        System.out.println("Salário Bruto: R$ " + salarioBruto);
        System.out.println("Desconto INSS: R$ " + inss);
        System.out.println("Salário Líquido: R$ " + salarioLiquido);
    }
}
