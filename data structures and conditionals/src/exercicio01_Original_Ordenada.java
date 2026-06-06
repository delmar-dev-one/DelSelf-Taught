import java.util.Arrays;

public class exercicio01_Original_Ordenada {
    public static void main(String[] args) {

        // Array de números
        int[] my_array1 = {2, 123, 333, 33, 3, 777, 369, 6, 555, 999, 99999, 45, 1899, 1};

        // Array de Strings
        String[] my_array2 = {"Java", "PHP", "Python", "C", "C++", "C#", "Arduino C++"};

        // Array 1 - Números
        System.out.println("Array 1 - Original: " + Arrays.toString(my_array1));
        Arrays.sort(my_array1);
        System.out.println("Array 1 - Ordem: " + Arrays.toString(my_array1));

        System.out.println(); // Linha em branco para separar

        // Array 2 - Strings (ordem alfabética)
        System.out.println("Array 2 - Original: " + Arrays.toString(my_array2));
        Arrays.sort(my_array2);
        System.out.println("Array 2 - Ordem: " + Arrays.toString(my_array2));
    }
}
