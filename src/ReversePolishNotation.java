import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ReversePolishNotation {

    private ReversePolishNotation() {
        // Nie pozwalam na tworzenie obiektów tej klasy
    }

    // Operatory wraz z ich wagą wiązaniową
    private static final Map<String, Integer> OPERATORS = new HashMap<>();
    static {
        OPERATORS.put("or", 0);
        OPERATORS.put("and", 5);
        OPERATORS.put("=", 10);
        OPERATORS.put("!=",10);
        OPERATORS.put(">", 10);
        OPERATORS.put(">=", 10);
        OPERATORS.put("<", 10);
        OPERATORS.put("<=", 10);
    }

    /**
     * Sprawdza czy token jest operatorem czy wyrażeniem
     */
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    /**
     * Sprawdza który operator ma silniejsze wiązanie
     */
    private static int compareOperators(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2))
            return 0;
        return OPERATORS.get(token1) - OPERATORS.get(token2);
    }

    /**
     * Zwraca tokeny ułożone w odwrotnej notacji polskiej
     * na podstawie Shunting-yard algorithm {@see https://en.wikipedia.org/wiki/Shunting-yard_algorithm}
     */
    public static String[] execute(String expression) {
        // Upewniam się że operatory są odpowiednio rozdzielone
        expression = expression
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ");

        for (Map.Entry<String, Integer> operator : OPERATORS.entrySet())
            expression = expression.replaceAll(operator.getKey(), " " + operator.getKey() + " ");

        expression = expression.toLowerCase();

        // Tworzę nasze tokeny
        String[] inputTokens = expression.split("\\s+");

        // Przygotowuję listę wynikową tokenów w notacji oraz stos operatorów
        ArrayList<String> wynik = new ArrayList<>();
        Stack<String> stosOperatorów = new Stack<>();

        for (String token : inputTokens) {
            // Pomijaj puste tokeny
            if (token.isEmpty())
                continue;

            // Jeśli to operator, wykonaj operacje na stosie
            if (isOperator(token)) {
                while (!stosOperatorów.empty() && isOperator(stosOperatorów.peek())) {
                    if (compareOperators(token, stosOperatorów.peek()) <= 0) {
                        wynik.add(stosOperatorów.pop());
                        continue;
                    }
                    break;
                }
                stosOperatorów.push(token);

            } else if (token.equals("(")) {
                stosOperatorów.push(token);

            } else if (token.equals(")")) {
                while (!stosOperatorów.empty() && !stosOperatorów.peek().equals("("))
                    wynik.add(stosOperatorów.pop());
                stosOperatorów.pop();

            } else
                wynik.add(token);
        }

        while (!stosOperatorów.empty())
            wynik.add(stosOperatorów.pop());

        String[] output = new String[wynik.size()];
        return wynik.toArray(output);
    }
}