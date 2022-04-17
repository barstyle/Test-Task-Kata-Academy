import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

// Реализуй класс Main с методом public static String calc(String input).
public class Main {
    //Будет отвечать за остановку работы калькулятора, пока истина - работает
    private static boolean runCalc = true;

    public static String calc(String input) {

        String[] operators = {"+", "-", "/", "*"};
        String operator = "";
        int countOperators = 0;

        // Проверяем на наличие операторов
        for (String o : operators) {
            if (input.contains(o)) {
                operator = o;
                // Считаем количество операторов
                // - Будет считать если операторы разные
                countOperators++;
                // - Будет считать если операторы одинаковые
                long operatorCount = input.chars().filter(ch -> ch == o.charAt(0)).count();
                // Кто больше тот и выиграл
                if (countOperators < operatorCount) countOperators = (int) operatorCount;
            }
        }
        // Бросаем исключение и завершаем работу если поддерживающий оператор не найден
        if (countOperators > 1) {
            try {
                throw new IOException("Оператор должен быть один и только: (+, -, /, *)");
            } catch (IOException e) {
                runCalc = false;
                e.printStackTrace();
            }
        } else if (countOperators < 1) {
            try {
                throw new IOException("Строка для нашего калькулятора не является математической операцией. " +
                        "\nИспользуйте оператор: (+, -, /, *)");
            } catch (IOException e) {
                runCalc = false;
                e.printStackTrace();
            }
        }

        // Тут будем хранить результат калькулятора
        int result;

        // Создаем список операндов
        ArrayList<String> expression = new ArrayList<>(Arrays.asList(input
                .replaceAll(" ", "")
                .split(MessageFormat.format("\\{0}", operator))));

        // Позади массив для проверки каждого операнда
        boolean[] isArabic = new boolean[2];
        boolean[] isRomano = new boolean[2];

        // Создаем словарь Ключ(Римские) - Значение(Арабские)
        HashMap<String, String> tupleRomano = new HashMap<>();
        for (int i = 1; i < 4001; i++) {
            tupleRomano.put(Roman.arabicToRoman(i), String.valueOf(i));
        }

        // Если операндов не два - завершаем работу и ругаемся
        if (expression.size() != 2) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "т.к. формат математической операции не удовлетворяет заданию " +
                        "- два операнда и один оператор (+, -, /, *)");
            } catch (IOException e) {
                runCalc = false;
                e.printStackTrace();
            }
        }

        // Лишние переменные для простоты
        String leftArgStr = expression.get(0);
        String rightArgStr = expression.get(1);

        int leftArgInt = 0;
        int rightArgInt = 0;

        // Наполним наши логические массивы значениями истина или ложь
        isArabic[0] = tupleRomano.containsValue(leftArgStr);
        isArabic[1] = tupleRomano.containsValue(rightArgStr);

        isRomano[0] = tupleRomano.containsKey(leftArgStr);
        isRomano[1] = tupleRomano.containsKey(rightArgStr);

        // Завершаем работу если у нас киш миш курага
        if (isArabic[0] != isArabic[1] && isRomano[0] != isRomano[1]) {
            try {
                throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "используются одновременно разные системы счисления");
            } catch (IOException e) {
                runCalc = false;
                e.printStackTrace();
            }
        }

        // Переводим в числа, что бы было легче нам считать
        if (isRomano[0] & isRomano[1]) {
            leftArgInt = Roman.romanToArabic(leftArgStr);
            rightArgInt = Roman.romanToArabic(rightArgStr);

        } else {
            // Если не получится - завершаем работу
            try {
                leftArgInt = Integer.parseInt(leftArgStr);
                rightArgInt = Integer.parseInt(rightArgStr);
            } catch (NumberFormatException e) {
                System.err.println("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                        "т.к. строка не является математической операцией");
                e.printStackTrace(); // ругаемся
                runCalc = false;
            }
        }

        // Завершаем работу если числа не соответствуют условиям
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

        // Если римские - то выводим результат на римском
        if (isRomano[0] & isRomano[1]) {
            if (result < 1) {
                try {
                    throw new IOException("ОШИБКА ВВОДА-ВЫВОДА!\n" +
                            "в римской системе нет отрицательных чисел и нуля");
                } catch (IOException e) {
                    runCalc = false;
                    e.printStackTrace();
                }
            }
            return Roman.arabicToRoman(result);
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Калькулятор работает - пока все условия ввода-вывода соблюдены
        while (scanner.hasNextLine()) {
            String res = calc(scanner.nextLine());
            if (!runCalc) break;
            System.out.println(res);
        }

    }
}

