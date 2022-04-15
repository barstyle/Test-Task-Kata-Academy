import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

// Реализуй класс Main с методом public static String calc(String input).
public class Main {

    public static String calc(String input) {

        System.out.println("- ".repeat(80));
        System.out.println("Expression: " + input);

        String[] operators = {"+", "-", "/", "*"};
        String operator = "";
        int countOperators = 0;

        for (String o : operators) {
            if (input.contains(o)) {
                operator = o;
                long operatorCount = input.chars().filter(ch -> ch == o.charAt(0)).count();
                if (countOperators < operatorCount) countOperators = (int) operatorCount;
                System.out.println("countOperators - " + countOperators);
            }
        }

        if (countOperators > 1) {
            try {
                throw new IOException("Оператор должен быть один и только: (+, -, /, *)");
            } catch (IOException e) {
                return e.getLocalizedMessage();
            }
        } else if (countOperators < 1) {
            try {
                throw new IOException("Строка для нашего калькулятора не является математической операцией. " +
                        "\nИспользуйте оператор: (+, -, /, *)");
            } catch (IOException e) {
                return e.getLocalizedMessage();
            }
        }

        int result;

        ArrayList<String> expression = new ArrayList<>(Arrays.asList(input
                .replaceAll(" ", "")
                .split(MessageFormat.format("\\{0}", operator))));

        System.out.println(Arrays.toString(expression.toArray()));

        boolean isArabic;
        boolean isRomano;

        HashMap<String, String> tupleRomano = new HashMap<>();
        for (int i = 1; i < 4001; i ++) {
            tupleRomano.put(Roman.arabicToRoman(i), String.valueOf(i));
        }
//        tupleRomano.put("I", "1");
//        tupleRomano.put("II", "2");
//        tupleRomano.put("III", "3");
//        tupleRomano.put("IV", "4");
//        tupleRomano.put("V", "5");
//        tupleRomano.put("VI", "6");
//        tupleRomano.put("VII", "7");
//        tupleRomano.put("VIII", "8");
//        tupleRomano.put("IX", "9");
//        tupleRomano.put("X", "10");


        if (expression.size() != 2) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "т.к. формат математической операции не удовлетворяет заданию " +
                        "- два операнда и один оператор (+, -, /, *)");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        String leftArgStr = expression.get(0);
        String rightArgStr = expression.get(1);

        int leftArgInt;
        int rightArgInt;

        isArabic = tupleRomano.containsValue(leftArgStr) | tupleRomano.containsValue(rightArgStr);
        isRomano = tupleRomano.containsKey(leftArgStr) | tupleRomano.containsKey(rightArgStr);

//        System.out.println(isArabic);
//        System.out.println(isRomano);

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
            expression.set(1, tupleRomano.get(expression.get(1)));
        }

        try {
            result = switch (operator) {
                case "+" -> Integer.parseInt(expression.get(0)) + Integer.parseInt(expression.get(1));
                case "-" -> Integer.parseInt(expression.get(0)) - Integer.parseInt(expression.get(1));
                case "*" -> Integer.parseInt(expression.get(0)) * Integer.parseInt(expression.get(1));
                case "/" -> Integer.parseInt(expression.get(0)) / Integer.parseInt(expression.get(1));
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

        if (Integer.parseInt(expression.get(0)) < 1 | Integer.parseInt(expression.get(0)) > 10
                | Integer.parseInt(expression.get(1)) < 1 | Integer.parseInt(expression.get(1)) > 10) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        if (isRomano & result > 1) {
//            tupleRomano.put("XI", "11");
//            tupleRomano.put("XII", "12");
//            tupleRomano.put("XIII", "13");
//            tupleRomano.put("XIV", "14");
//            tupleRomano.put("XV", "15");
//            tupleRomano.put("XVI", "16");
//            tupleRomano.put("XVII", "17");
//            tupleRomano.put("XVIII", "18");
//            tupleRomano.put("XIX", "19");
//            tupleRomano.put("XX", "20");

            for (String res : tupleRomano.keySet()) {
                if (tupleRomano.get(res).equals(String.valueOf(result))) {
                    return res;
                }
            }
        }
        if (isArabic) return String.valueOf(result);
        return "В Риме нет чисел меньше Единицы - только ВПЕРЕД! - только ВВЕРХ! - К ЗВЕЗДАМ!!!";
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
        System.out.println(calc("IX + III"));
        System.out.println(calc("11 + 2"));
        System.out.println(calc("1 - 2"));
        System.out.println(calc("I - II"));
        System.out.println(calc("III"));
        System.out.println(calc("X/V"));
        System.out.println(calc("V / II"));
        System.out.println(calc("V * X"));
    }
}

