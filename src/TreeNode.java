/**
 * Klasa reprezentująca drzewo dla operacji matematycznych
 */
public class TreeNode {

    /**
     * Wartość
     */
    private String value;

    /**
     * Lewa gałąź
     */
    private TreeNode leftNode;

    /**
     * Prawa gałąź
     */
    private TreeNode rightNode;

    /**
     * Rodzic
     */
    private TreeNode parentNode;

    /**
     * Tworzy nowy węzeł z zadaną wartością
     * @param value wartość
     */
    public TreeNode(String value) {
        this.value = value;
    }

    /**
     * Jeśli {@see TreeNode} nie posiada dzieci, uznawany jest za wartość
     */
    public boolean isValue() {
        return leftNode == null && rightNode == null;
    }

    /**
     * Jeśli {@see TreeNode} posiada dzieci które uznawane są za wartości, to jest operacją
     */
    public boolean isSimpleOperation() {
        return !isValue() && leftNode.isValue() && rightNode.isValue();
    }

    /**
     * Zwraca wartość węzła
     * @return wartość węzła
     */
    public String getValue() {
        return value;
    }

    /**
     * Zwraca lewą gałąź
     * @return lewa gałąź
     */
    public TreeNode getLeftNode() {
        return leftNode;
    }

    /**
     * Zmienia lewą gałąź na zadany węzeł
     * @param node węzeł
     * @return węzeł po zmianie
     */
    public TreeNode setLeftNode(TreeNode node) {
        leftNode = node;
        leftNode.setParentNode(this);
        return this;
    }

    /**
     * Zwraca prawą gałąź
     * @return prawa gałąź
     */
    public TreeNode getRightNode() {
        return rightNode;
    }

    /**
     * Zmienia prawą gałąź na zadany węzeł
     * @param node węzeł
     * @return węzeł po zmianie
     */
    public TreeNode setRightNode(TreeNode node) {
        rightNode = node;
        rightNode.setParentNode(this);
        return this;
    }

    /**
     * Zmienia rodzica węzła na zadany węzeł
     * @param node węzeł
     * @return węzeł po zmianie
     */
    private TreeNode setParentNode(TreeNode node) {
        parentNode = node;
        return this;
    }

    /**
     * Zwraca pierwszy węzeł w górę w hierarchi który ma pustą lewą gałąź
     * @return węzeł z pustą lewą gałęzią
     */
    public TreeNode getFirstParentWithEmptyLeftNode() {
        if (null != parentNode) {
            if (null == parentNode.getLeftNode())
                return parentNode;
            else
                return parentNode.getFirstParentWithEmptyLeftNode();
        } else
            return null;
    }
}