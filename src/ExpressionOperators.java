import java.util.Collections;
import java.util.HashMap;
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
     * Mapa określająca znaki zastępcze dla operatorów
     */
    private static final Map<String, String> SUBSTITUTES_OPERATORS = new HashMap<>();
    static {
        SUBSTITUTES_OPERATORS.put("+", "or");
        SUBSTITUTES_OPERATORS.put("*", "and");
        SUBSTITUTES_OPERATORS.put("!", "!=");
        SUBSTITUTES_OPERATORS.put("#", ">=");
        SUBSTITUTES_OPERATORS.put("@", "<=");

        SUBSTITUTES_OPERATORS.put("=", "=");
        SUBSTITUTES_OPERATORS.put(">", ">");
        SUBSTITUTES_OPERATORS.put("<", "<");
    }

    /**
     * Udostępnia niemodyfikowalną listę pierwszeństwa operatorów
     */
    public static Map<String, Integer> getPrecedence() {
        return Collections.unmodifiableMap(OPERATORS_PRECEDENCE);
    }

    /**
     * Udostępnia niemodyfikowalną listę zastępczych operatorów (SUBSTYTUT => OPERATOR)
     */
    public static Map<String, String> getSubstitutesOperatorsMap() {
        return Collections.unmodifiableMap(SUBSTITUTES_OPERATORS);
    }

    /**
     * Udostępnia niemodyfikowalną listę zastępczych operatorów (OPERATOR => SUBSTYTUT)
     */
    public static Map<String, String> getOperatorsSubstitutesMap() {
        Map<String, String> reverse = new HashMap<>();

        for (Map.Entry<String, String> entry : SUBSTITUTES_OPERATORS.entrySet())
            reverse.put(entry.getValue(), entry.getKey());

        return Collections.unmodifiableMap(reverse);
    }

    /**
     * Sprawdza czy token jest operatorem
     */
    public static boolean isOperator(String token) {
        return SUBSTITUTES_OPERATORS.containsValue(token) || SUBSTITUTES_OPERATORS.containsKey(token);
    }

    /**
     * Sprawdza który operator ma silniejsze wiązanie
     */
    public static int compareOperators(String token1, String token2) {
        if (!ExpressionOperators.isOperator(token1) || !ExpressionOperators.isOperator(token2))
            return 0;

        if (token1.length() == 1)
            token1 = SUBSTITUTES_OPERATORS.get(token1);
        if (token2.length() == 1)
            token2 = SUBSTITUTES_OPERATORS.get(token2);

        return OPERATORS_PRECEDENCE.get(token1) - OPERATORS_PRECEDENCE.get(token2);
    }

    /**
     * Podmienia operatory na ich jednoznakowy odpowiedniki
     */
    public static String[] substituteOperators(String... operators) {
        Map<String, String> operatorsSubstitutesMap = getOperatorsSubstitutesMap();
        String[] newTokens = new String[operators.length];

        for (int i = 0; i < operators.length; i++) {
            newTokens[i] = operatorsSubstitutesMap.containsKey(operators[i])
                    ? operatorsSubstitutesMap.get(operators[i])
                    : operators[i];
        }

        return newTokens;
    }

    /**
     * Podmienia operator na jego jednoznakowy odpowiednik
     */
    public static String substituteOperator(String operator) {
        return substituteOperators(operator)[0];
    }

    /**
     * Podmienia jednoznakowe odpowiedniki operatorów na właściwe operatory
     */
    public static String[] restoreOperators(String... operators) {
        String[] newTokens = new String[operators.length];

        for (int i = 0; i < operators.length; i++) {
            newTokens[i] = SUBSTITUTES_OPERATORS.containsKey(operators[i])
                    ? SUBSTITUTES_OPERATORS.get(operators[i])
                    : operators[i];
        }

        return newTokens;
    }

    /**
     * Podmienia jednoznakowy odpowiednik operatora na właściwy operator
     */
    public static String restoreOperator(String operator) {
        return restoreOperators(operator)[0];
    }
}
