import java.io.IOException;
import java.util.*;

// Реализуй класс Main с методом public static String calc(String input).
public class Main {

    public static String calc(String input) {

        String[] operators = {"+", "-", "/", "*"};

        Boolean isArabic;
        Boolean isRomano;

        HashMap<String, Integer> tupleRomano = new HashMap<String, Integer>();
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

        ArrayList<String> expression = new ArrayList<>(Arrays.asList(input.strip().split(" ")));

        System.out.println(Arrays.toString(expression.toArray()));

        if (expression.size() != 3) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\nт.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        int result = switch (expression.get(1)) {
            case "+" -> Integer.parseInt(expression.get(0)) + Integer.parseInt(expression.get(2));
            case "-" -> Integer.parseInt(expression.get(0)) - Integer.parseInt(expression.get(2));
            case "*" -> Integer.parseInt(expression.get(0)) * Integer.parseInt(expression.get(2));
            case "/" -> Integer.parseInt(expression.get(0)) / Integer.parseInt(expression.get(2));
            default -> throw new IllegalStateException("Unexpected value: " + expression.get(1));
        };

        return String.valueOf(result);

    }

    public static void main(String[] args) {
        System.out.println(calc("1 + 1"));
        System.out.println(calc("10 + 10"));
        System.out.println(calc("10 / 5"));
        System.out.println(calc("5 / 2"));
        System.out.println(calc("5 + 2 +"));
        System.out.println(calc(" 5 + 2 "));
        System.out.println(calc(" 5 / 0 "));

    }
}
