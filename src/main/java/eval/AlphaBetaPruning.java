package eval;

import othello.Oth;

public class AlphaBetaPruning {

    public String getSolverName() {
        return "Alpha-Beta Pruning";
    }


    public double solve(Oth board) {
        Node root = new Node(board, Node.NodeType.MAX);
        return AlphaBetaPruningAlgorithm(root, -Double.MAX_VALUE, Double.MAX_VALUE);
    }


    private double AlphaBetaPruningAlgorithm(Node node, double p_alpha, double p_beta) {
        double nodeValue = node.nodeType() == Node.NodeType.MAX ? -Double.MAX_VALUE : Double.MAX_VALUE;
        for (Node child : node.getNodeChildren())
            if (p_beta > p_alpha) {
                double childValue;
                if (!child.isTerminalNode())
                    childValue = AlphaBetaPruningAlgorithm(child, p_alpha, p_beta);
                else {
                    childValue = child.getScore();
                    switch (node.nodeType()) {
                        case MIN -> p_beta = childValue;
                        case MAX -> p_alpha = childValue;
                    }
                }
                switch (node.nodeType()) {
                    case MIN -> {
                        nodeValue = Double.min(nodeValue, childValue);
                        if (p_beta > childValue)
                            p_beta = childValue;
                        if (p_beta > nodeValue)
                            p_beta = nodeValue;
                    }
                    case MAX -> {
                        nodeValue = Double.max(nodeValue, childValue);
                        if (p_alpha < childValue)
                            p_alpha = childValue;
                        if (p_alpha < nodeValue)
                            p_alpha = nodeValue;
                    }
                }
            }
        return nodeValue;
    }
}