import java.util.Map;
import java.util.regex.Pattern;

public class Expression {

    /**
     * Oryginalne wyrażenie
     */
    private final String expressionString;

    /**
     * Regex z dozwolonymi znakami w wrażeniu
     */
    private final static String allowedCharactersPattern = "^(or|and|=|>|<|!|[a-h0-9]|\\s|\\(|\\))+$";

    /**
     * Tworzy nowe wyrażenie
     * @param expression wyrażenie
     * @throws UnsupportedOperationException gdy wprowadzono nielegalne znaki
     */
    public Expression(String expression) throws UnsupportedOperationException {
        expressionString = expression.toLowerCase();

        if (!isCorrectExpression(expressionString))
            throw new UnsupportedOperationException();
    }

    /**
     * Sprawdza czy wprowadzono tylko dozwolone znaki, nie sprawdza czy równanie ma sens
     */
    public static boolean isCorrectExpression(String expression) {
        return Pattern.matches(allowedCharactersPattern, expression);
    }

    /**
     * Wprowadzone wyrażenie
     */
    public String getExpression() {
        return expressionString;
    }

    /**
     * Tokeny wyrażenia w naturalnym porządku
     */
    public String[] getExpressionTokens() {
        // Upewniam się że wszystkie operatory/znaki specjalne są odpowiednio rozdzielone w wyrażeniu
        String expression = expressionString
                .replaceAll("\\(", " ( ")
                .replaceAll("\\)", " ) ");

        for (Map.Entry<String, Integer> operator : ExpressionOperators.getPrecedence().entrySet())
            expression = expression.replaceAll(operator.getKey(), " " + operator.getKey() + " ");

        // Przywracam operatory które mogły ulec zniszczeniu przy powyższym dodawaniu spacji
        expression = expression
                .replaceAll(">\\s*=", ">=")
                .replaceAll("<\\s*=", "<=")
                .replaceAll("!\\s*=", "!=");

        // Zwracam tokeny dzieląc je po białych znakach
        return expression.toLowerCase().split("\\s+");
    }

    /**
     * Tokeny wyrażenia w odwrotnej notacji polskiej
     */
    public String[] getReversePolishNotationTokens() {
        return ReversePolishNotationConverter.convert(this);
    }

    /**
     * Drzewo binarne reprezentujące wyrażenie
     */
    public BinaryNode getExpressionTree() {
        return BinaryExpressionTreeGenerator.makeTree(getReversePolishNotationTokens());
    }
}
