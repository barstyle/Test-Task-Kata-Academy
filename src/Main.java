import java.util.*;

// Реализуй класс Main с методом public static String calc(String input).
public class Main {

    public static String calc(String input) {

        String[] operators = {"+", "-", "/", "*"};

        Boolean isArabic = true;

        HashMap<String, Integer> tupleRomano = new HashMap<>();
        tupleRomano.put("I", 1);
        tupleRomano.put("II", 2);
        tupleRomano.put("III", 3);
        tupleRomano.put("IV", 4);
        tupleRomano.put("V", 5);
        tupleRomano.put("VI", 6);
        tupleRomano.put("VII", 7);
        tupleRomano.put("VIII", 8);
        tupleRomano.put("IX", 9);
        tupleRomano.put("X", 10);




        List<String> operands = new ArrayList<>();


        for (int i = 0; i < input.split(" ").length; i++) {

            operands.add(input.split(" ")[i]);
        }

        System.out.println(Arrays.toString(operands.toArray()));

        int result;

        switch (operands.get(1)) {
            case "+":
                result = Integer.parseInt(operands.get(0)) + Integer.parseInt(operands.get(2));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operands.get(1));
        }

        return String.valueOf(result);

    }

    public static void main(String[] args) {
        System.out.println(calc("1 + 1"));
        System.out.println(calc("10 + 10"));
        System.out.println(calc("10 % 10"));


    }
}
