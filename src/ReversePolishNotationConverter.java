import java.util.ArrayList;
import java.util.Stack;

/**
 * Klasa pozwalająca na konwersję naturalnego porządku operacji w wyrażeniu na porządek Odwrotnej Notacji Polskiej
 */
public class ReversePolishNotationConverter {

    private ReversePolishNotationConverter() {
        // Nie ma potrzeby tworzenia isntancji tej klasy
    }

    /**
     * Zwraca tokeny ułożone w odwrotnej notacji polskiej
     * na podstawie @see <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting-yard algorithm</a>
     */
    public static String[] convert(Expression expression) {
        // Wczytuję nasze tokeny
        String[] inputTokens = expression.getExpressionTokens();

        // Przygotowuję listę wynikową tokenów w notacji oraz stos operatorów
        ArrayList<String> result = new ArrayList<>();
        Stack<String> operatorsStack = new Stack<>();

        for (String token : inputTokens) {
            // Pomijaj puste tokeny
            if (token.isEmpty())
                continue;

            // Jeśli to operator, to dodaj poprzedni do wyniku (jeśli jest bardziej wiążący), a nowy dodaj na stos
            if (ExpressionOperators.isOperator(token)) {
                while (!operatorsStack.empty() && ExpressionOperators.isOperator(operatorsStack.peek())) {
                    if (ExpressionOperators.compareOperators(token, operatorsStack.peek()) <= 0) {
                        result.add(operatorsStack.pop());
                        continue;
                    }
                    break;
                }
                operatorsStack.push(token);

            // Dodaj "(" do stosu
            } else if (token.equals("(")) {
                operatorsStack.push(token);

            // Jeśli ")" wykonaj dodaj do wyniku kolejne zapisane operatory z tego nawiasu
            } else if (token.equals(")")) {
                while (!operatorsStack.empty() && !operatorsStack.peek().equals("("))
                    result.add(operatorsStack.pop());
                operatorsStack.pop();

            // Jeśli nie jest to żaden z operatorów dodaj do wyniku
            } else
                result.add(token);
        }

        // Dodaj pozostałe operatory do wyniku
        while (!operatorsStack.empty())
            result.add(operatorsStack.pop());

        String[] newTokens = new String[result.size()];
        return result.toArray(newTokens);
    }
}