package eval;

import othello.Oth;
import othello.UtilsClass;

import java.util.ArrayList;
import java.util.List;

public record Node(Oth board, NodeType nodeType) {

    public NodeType getNodeOtherType() {
        if (nodeType == NodeType.MAX)
            return NodeType.MIN;
        else
            return NodeType.MAX;
    }

    public List<Node> getNodeChildren() {
        List<Node> children = new ArrayList<>();
        List<UtilsClass.Coups> legalMoves = board.getLegalMoves();
        for (UtilsClass.Coups move : legalMoves) {
            Oth newBoard = board.getNewChildBoard(move);
            NodeType newNodeType;
            if (board.getCurrentPlayer() == newBoard.getCurrentPlayer())
                newNodeType = nodeType;
            else
                newNodeType = getNodeOtherType();

            Node newNode = new Node(newBoard, newNodeType);
            children.add(newNode);
        }
        return children;
    }

    public boolean isTerminalNode() {
        return board.isTheGameOver();
    }

    public double getScore() {
        return board.getScore();
    }

    public enum NodeType {MAX, MIN}
}
