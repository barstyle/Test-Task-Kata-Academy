import java.io.IOException;
import java.util.*;

// Реализуй класс Main с методом public static String calc(String input).
public class Main {

    public static String calc(String input) {

        String[] operators = {"+", "-", "/", "*"};
        int result;

        Boolean isArabic;
        Boolean isRomano;

        HashMap<String, String> tupleRomano = new HashMap<String, String>();
        tupleRomano.put("I", "1");
        tupleRomano.put("II", "2");
        tupleRomano.put("III", "3");
        tupleRomano.put("IV", "4");
        tupleRomano.put("V", "5");
        tupleRomano.put("VI", "6");
        tupleRomano.put("VII", "7");
        tupleRomano.put("VIII", "8");
        tupleRomano.put("IX", "9");
        tupleRomano.put("X", "10");

        ArrayList<String> expression = new ArrayList<>(Arrays.asList(input.strip().split(" ")));

        System.out.println(Arrays.toString(expression.toArray()));

        if (expression.size() != 3) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "т.к. формат математической операции не удовлетворяет заданию " +
                        "- два операнда и один оператор (+, -, /, *)");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        isArabic = tupleRomano.containsValue(expression.get(0)) | tupleRomano.containsValue(expression.get(2));
        isRomano = tupleRomano.containsKey(expression.get(0)) | tupleRomano.containsKey(expression.get(2));

        System.out.println(isArabic);
        System.out.println(isRomano);

        if (isArabic == isRomano) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "т.к. формат математической операции не удовлетворяет заданию " +
                        "- Либо only РИМСКИЕ - Либо only АРАБСКИЕ, камон!?");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        if (isRomano) {
            expression.set(0, tupleRomano.get(expression.get(0)));
            expression.set(2, tupleRomano.get(expression.get(2)));
        }

        System.out.println(expression.toString());

        try {
            result = switch (expression.get(1)) {
                case "+" -> Integer.parseInt(expression.get(0)) + Integer.parseInt(expression.get(2));
                case "-" -> Integer.parseInt(expression.get(0)) - Integer.parseInt(expression.get(2));
                case "*" -> Integer.parseInt(expression.get(0)) * Integer.parseInt(expression.get(2));
                case "/" -> Integer.parseInt(expression.get(0)) / Integer.parseInt(expression.get(2));
                default -> throw new IllegalStateException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "Калькулятор умеет выполнять только операции сложения, вычитания, умножения и деления\n" +
                        "и не поддерживает оператор: "
                        + expression.get(1));
            };
        } catch (NumberFormatException e) {
            return "ОШИБКА ВВОДА-ВЫВОДА!\n" +
                    "ДВА операнда и ОДИН оператор, бро! - " + e.getMessage();
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
        if (isRomano) {
            tupleRomano.put("XI", "11");
            tupleRomano.put("XII", "12");
            tupleRomano.put("XIII", "13");
            tupleRomano.put("XIV", "14");
            tupleRomano.put("XV", "15");
            tupleRomano.put("XVI", "16");
            tupleRomano.put("XVII", "17");
            tupleRomano.put("XVIII", "18");
            tupleRomano.put("XIX", "19");
            tupleRomano.put("XX", "20");

            for (String res: tupleRomano.keySet()) {
                if(tupleRomano.get(res).equals(String.valueOf(result))) {
                    System.out.println("!".repeat(50));
                    return tupleRomano.get(res);
                }
            }
        } return String.valueOf(result);

    }

    public static void main(String[] args) {
        System.out.println(calc("1 + 1"));
        System.out.println(calc("10 + 10"));
        System.out.println(calc("10 / 5"));
        System.out.println(calc("5 / 2"));
        System.out.println(calc("5 + 2 +"));
        System.out.println(calc(" 5 + 2 "));
        System.out.println(calc(" 5 / + "));
        System.out.println(calc(" 5 % 2 "));
        System.out.println(calc(" 5+2 "));
        System.out.println(calc("1 + III"));
        System.out.println(calc("V + III"));

    }
}
