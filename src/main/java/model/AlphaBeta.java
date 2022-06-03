package model;


import java.util.List;

//  players.put(Side.BLACK, new AlphaBetaPlayer(board, Side.BLACK));

class AlphaBeta {
     static final int INITIAL_DEPTH = 5;
     static final int TIMEOUT_MILISECONDS = 6000;

     int currentDepth;
     Move bestMove;
     Move globalBestMove;
     long start;
     boolean timeout;
    ///
    Board board;
    Side side;

    public AlphaBeta(Board board, Side side) {
        this.board = board;
        this.side = side;
    }

    public Move decideMove() {
        timeout = false;
        start = System.currentTimeMillis();

        for (int d = 0; ; d++) {
            if (d > 0) {
                globalBestMove = bestMove;
                System.out.println("Recherche terminée avec profondeur " + currentDepth + ". meilleur mouvement jusqu'à présent: " + globalBestMove);
            }
            currentDepth = INITIAL_DEPTH + d;

            maximizer(currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (timeout) {
                System.out.println();
                return globalBestMove;
            }
        }
    }

    ///
    private int maximizer(int depth, int alpha, int beta) {
        if (System.currentTimeMillis() - start > TIMEOUT_MILISECONDS) {
            timeout = true;
            return alpha;
        }

        if (depth == 0) {
            return board.computeRating(Side.BLACK);
        }

        List<Move> legalMoves = computeAllLegalMoves();

         assert legalMoves != null;
        for (Move move : legalMoves) {

            makeMove(move);
            side = side.opposite();

            int rating = minimizer(depth - 1, alpha, beta);

            side = side.opposite();
            undoMove(move);

            if (rating > alpha) {
                alpha = rating;

                if (depth == currentDepth) {
                    bestMove = move;
                }
            }

            if (alpha >= beta) {
                return alpha;
            }
        }
        return alpha;
    }

    private int minimizer(int depth, int alpha, int beta) {
        if (depth == 0) {
            return board.computeRating(Side.BLACK);
        }

        List<Move> legalMoves = computeAllLegalMoves();

        for (Move move : legalMoves) {

            makeMove(move);
            side = side.opposite();

            int rating = maximizer(depth - 1, alpha, beta);

            side = side.opposite();
            undoMove(move);

            if (rating <= beta) {
                beta = rating;
            }

            if (alpha >= beta) {
                return beta;
            }
        }
        return beta;
    }

    ///
    static class Board {
        public int computeRating(Side side) { //eval de board
            return 0;
        }
    }

    static class Side {
        public static Side BLACK;

        public Side opposite() {
            return null;
        }
    }

    static class Move {
    }
    ///

    List<Move> computeAllLegalMoves() {
        return null;
    }

     void undoMove(Move move) {

    }

     void makeMove(Move move) {

    }


}
