public class BinaryNode {

    private String value;

    private BinaryNode leftNode;

    private BinaryNode rightNode;

    public BinaryNode(String value) {
        this.value = value;
    }

    /**
     * Jeśli {@see BinaryNode} nie posiada dzieci, uznawany jest za wartość
     */
    public boolean isValue() {
        return leftNode == null && rightNode == null;
    }

    /**
     * Jeśli {@see BinaryNode} posiada dzieci które uznawane są za wartości, to jest operacją
     */
    public boolean isSimpleOperation() {
        return !isValue() && leftNode.isValue() && rightNode.isValue();
    }

    public BinaryNode getLeftNode() {
        return leftNode;
    }

    public BinaryNode getRightNode() {
        return rightNode;
    }

    public BinaryNode setLeftNode(BinaryNode node) {
        leftNode = node;
        return this;
    }

    public BinaryNode setRightNode(BinaryNode node) {
        rightNode = node;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}