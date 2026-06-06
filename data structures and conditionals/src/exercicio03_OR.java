public class exercicio03_OR {
    public static void main(String[] args) {
        System.out.println(">>> OPERADOR LÓGICO OR ( || ) >>>");

        boolean temEmprego = true;
        boolean temEmpresa = true;
        boolean temCartaDeCredito = true;
        boolean temValorEntrada = true;
        boolean temNomeLimpo = true;

        boolean podeFinanciar = (temEmprego || temEmpresa || temCartaDeCredito)
                && temValorEntrada && temNomeLimpo;

        System.out.println("Pode financiar/compra? " + podeFinanciar);

        // Teste com entrada insuficiente
        temValorEntrada = false;
        podeFinanciar = (temEmprego || temEmpresa || temCartaDeCredito)
                && temValorEntrada && temNomeLimpo;
        System.out.println("Pode financiar (sem entrada)? " + podeFinanciar);
    }
}
