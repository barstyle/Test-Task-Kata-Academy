import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

// Реализуй класс Main с методом public static String calc(String input).
public class Main {

    private static boolean runCalc = true;

    public static String calc(String input) {

        System.out.println("- ".repeat(80));
        System.out.println("Expression: " + input);

        String[] operators = {"+", "-", "/", "*"};
        String operator = "";
        int countOperators = 0;

        // Проверяем на наличие операторов
        for (String o : operators) {
            if (input.contains(o)) {
                operator = o;
                //Считаем количество операторов
                countOperators++;
                long operatorCount = input.chars().filter(ch -> ch == o.charAt(0)).count();
                if (countOperators < operatorCount) countOperators = (int) operatorCount;
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

        System.out.println("countOperators - " + countOperators);

        int result;

        ArrayList<String> expression = new ArrayList<>(Arrays.asList(input
                .replaceAll(" ", "")
                .split(MessageFormat.format("\\{0}", operator))));

        System.out.println(Arrays.toString(expression.toArray()));

        boolean[] isArabic = new boolean[2];
        boolean[] isRomano = new boolean[2];

        HashMap<String, String> tupleRomano = new HashMap<>();
        for (int i = 1; i < 4001; i++) {
            tupleRomano.put(Roman.arabicToRoman(i), String.valueOf(i));
        }

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

        isArabic[0] = tupleRomano.containsValue(leftArgStr);
        isArabic[1] = tupleRomano.containsValue(rightArgStr);

        isRomano[0] = tupleRomano.containsKey(leftArgStr);
        isRomano[1] = tupleRomano.containsKey(rightArgStr);

        if (isArabic[0] != isArabic[1] && isRomano[0] != isRomano[1]) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "используются одновременно разные системы счисления");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        if (isRomano[0] & isRomano[1]) {
            leftArgInt = Roman.romanToArabic(leftArgStr);
            rightArgInt = Roman.romanToArabic(rightArgStr);
            expression.set(0, tupleRomano.get(expression.get(0)));
            expression.set(1, tupleRomano.get(expression.get(1)));
        } else {
            try {
                leftArgInt = Integer.parseInt(leftArgStr);
                rightArgInt = Integer.parseInt(rightArgStr);
            } catch (NumberFormatException e) {
                return "ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "т.к. строка не является математической операцией";
            }

        }
        if (leftArgInt < 1 | leftArgInt > 10 | rightArgInt < 1 | rightArgInt > 10) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "Калькулятор может принимать на вход числа от 1 до 10 включительно, не более");
            } catch (IOException e) {
                runCalc = false;
                e.printStackTrace();
            }
        }

        result = switch (operator) {
            case "+" -> leftArgInt + rightArgInt;
            case "-" -> leftArgInt - rightArgInt;
            case "*" -> leftArgInt * rightArgInt;
            case "/" -> leftArgInt / rightArgInt;
            default -> throw new IllegalStateException("Что-то пошло не так");
        };

        if (isRomano[0] & isRomano[1]) {
            if (result < 1) {
                try {
                    throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                            "в римской системе нет отрицательных чисел и нуля");
                } catch (IOException e) {
                    return e.getMessage();
                }
            }
            return Roman.arabicToRoman(result);
        }
        return String.valueOf(result);
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine() && runCalc) {
            System.out.println(calc(scanner.nextLine()));
        }
//        System.out.println(calc("1 + 1"));
//        System.out.println(calc("10 + 10"));
//        System.out.println(calc("10 / 5"));
//        System.out.println(calc("5 / 2"));
//        System.out.println(calc("5 + 2 +"));
//        System.out.println(calc(" 5 + 2 "));
//        System.out.println(calc(" 5 / + "));
//        System.out.println(calc(" 5 % 2 "));
//        System.out.println(calc(" 5+2 "));
//        System.out.println(calc("1 + III"));
//        System.out.println(calc("V + III"));
//        System.out.println(calc("IX + III"));
//        System.out.println(calc("11 + 2"));
//        System.out.println(calc("1 - 2"));
//        System.out.println(calc("I - II"));
//        System.out.println(calc("III"));
//        System.out.println(calc("X/V"));
//        System.out.println(calc("V / II"));
//        System.out.println(calc("V * X"));
//        System.out.println(calc("5 * %"));
//        System.out.println(calc("XI * II"));
//        System.out.println(calc("1,2 * 2,5"));
    }
}

