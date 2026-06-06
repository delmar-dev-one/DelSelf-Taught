public class exercicio03_Math {
    public static void main(String[] args) {
        System.out.println(">>>CLASSE MATH EM JAVA >>>\n");

        double num = 100.99;
        System.out.println("Número original: " + num);
        System.out.println("Math.ceil (para cima): " + Math.ceil(num));
        System.out.println("Math.floor (para baixo): " + Math.floor(num));
        System.out.println("Math.round: " + Math.round(num));

        // Número aleatório
        int random = (int) Math.round(Math.random() * 1000000);
        System.out.println("Número aleatório: " + random);

        // Máximo e Mínimo
        int[] numeros = {6, 3, 9, 7, 1200, 77, 333, 12, 9999, 0, 543, 987};
        int max = numeros[0];
        int min = numeros[0];
        for (int n : numeros) {
            max = Math.max(max, n);
            min = Math.min(min, n);
        }
        System.out.println("Maior(>) valor: " + max);
        System.out.println("Menor(<) valor: " + min);
    }
}
