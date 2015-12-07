import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ReversePolishNotation {

    private ReversePolishNotation() {
        // Nie pozwalam na tworzenie obiektów tej klasy
        // TODO zamień operatory na znaki A, B, C... bo sypie się przy równaniach z kilkoma znakami jako operator
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
     * na podstawie @see <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting-yard algorithm</a>
     */
    public static String[] execute(String wyrażenie) {
        // Upewniam się że operatory są odpowiednio rozdzielone
        wyrażenie = wyrażenie
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ");

        for (Map.Entry<String, Integer> operator : OPERATORS.entrySet())
            wyrażenie = wyrażenie.replaceAll(operator.getKey(), " " + operator.getKey() + " ");

        wyrażenie = wyrażenie.toLowerCase();

        // Tworzę nasze tokeny
        String[] inputTokens = wyrażenie.split("\\s+");

        // Przygotowuję listę wynikową tokenów w notacji oraz stos operatorów
        ArrayList<String> wynik = new ArrayList<>();
        Stack<String> stosOperatorów = new Stack<>();

        for (String token : inputTokens) {
            // Pomijaj puste tokeny
            if (token.isEmpty())
                continue;

            // Jeśli to operator, to dodaj poprzedni do wyniku (jeśli jest bardziej wiążący), a nowy dodaj na stos
            if (isOperator(token)) {
                while (!stosOperatorów.empty() && isOperator(stosOperatorów.peek())) {
                    if (compareOperators(token, stosOperatorów.peek()) <= 0) {
                        wynik.add(stosOperatorów.pop());
                        continue;
                    }
                    break;
                }
                stosOperatorów.push(token);

            // Dodaj "(" do stosu
            } else if (token.equals("(")) {
                stosOperatorów.push(token);

            // Jeśli ")" wykonaj dodaj do wyniku kolejne zapisane operatory z tego nawiasu
            } else if (token.equals(")")) {
                while (!stosOperatorów.empty() && !stosOperatorów.peek().equals("("))
                    wynik.add(stosOperatorów.pop());
                stosOperatorów.pop();

            // Jeśli nie jest to żaden z operatorów dodaj do wyniku
            } else
                wynik.add(token);
        }

        // Dodaj pozostałe operatory do wyniku
        while (!stosOperatorów.empty())
            wynik.add(stosOperatorów.pop());

        String[] tokeny = new String[wynik.size()];
        return wynik.toArray(tokeny);
    }
}