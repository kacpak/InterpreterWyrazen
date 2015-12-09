import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExpressionOperators {

    private ExpressionOperators() {
        // Nie ma potrzeby tworzyć instancji tej klasy
    }

    /**
     * Mapa określa pierwszeństwo operatorów względem siebie, im wyższa wartość tym operator silniej wiąże
     */
    private static final Map<String, Integer> OPERATORS_PRECEDENCE = new LinkedHashMap<>();
    static {
        OPERATORS_PRECEDENCE.put("or", 0);
        OPERATORS_PRECEDENCE.put("and", 5);
        OPERATORS_PRECEDENCE.put("!=", 10);
        OPERATORS_PRECEDENCE.put(">=", 10);
        OPERATORS_PRECEDENCE.put("<=", 10);
        OPERATORS_PRECEDENCE.put("=", 10);
        OPERATORS_PRECEDENCE.put(">", 10);
        OPERATORS_PRECEDENCE.put("<", 10);
    }

    /**
     * Udostępnia niemodyfikowalną listę pierwszeństwa operatorów
     */
    public static Map<String, Integer> getPrecedence() {
        return Collections.unmodifiableMap(OPERATORS_PRECEDENCE);
    }

    /**
     * Sprawdza czy token jest operatorem
     */
    public static boolean isOperator(String token) {
        return OPERATORS_PRECEDENCE.containsKey(token);
    }

    /**
     * Sprawdza który operator ma silniejsze wiązanie
     */
    public static int compareOperators(String token1, String token2) {
        if (!ExpressionOperators.isOperator(token1) || !ExpressionOperators.isOperator(token2))
            return 0;

        return OPERATORS_PRECEDENCE.get(token1) - OPERATORS_PRECEDENCE.get(token2);
    }
}
