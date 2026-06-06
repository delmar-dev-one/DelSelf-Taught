public class exercicio03 {
    public static void main(String[] args) {
        // Bora fazer uma base de calculo financeiro basico...

        int aluguel_prestacao = 3000 * 6;
        int energia_eletrica = 270 * 6;
        int agua = 160 * 6;
        int alimento = 1500 * 6;
        int passeio = 300 * 6;
        int prestacao_carro = 1600 * 6;

        int total_semestre = aluguel_prestacao + energia_eletrica + agua + alimento + passeio + prestacao_carro;

        System.out.println("Total Semestre: " + total_semestre);

        int mediaMensal = total_semestre / 6;
        System.out.println("Media mensal: " + mediaMensal);
    }
}
