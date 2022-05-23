package perft;

import othello.Oth;
import othello.UtilsClass;

import java.util.List;

import static oth.Constantes.undomove;


//DEPTH  #LEAF NODES   #FULL-DEPTH  #HIGHER
//        ==========================================
//        1            4
//        2           12
//        3           56
//        4          244
//        5         1396
//        6         8200
//        7        55092
//        8       390216
//        9      3005288
//        10     24571284
//        11    212258800  =    212258572  +    228
//        12   1939886636  =   1939886052  +    584
//        13  18429641748  =  18429634780  +   6968
//        14 184042084512  = 184042061172  +  23340
//-----------------------------------------------------


public class PerftSpeed {
    static final int MAX_DEPTH = 10;

    public static void main(String[] args) {
        perftTest();
    }

    static void perftTest() {

        Oth o = new Oth();
        double t0 = System.nanoTime();
        for (int depth = 1; depth <= MAX_DEPTH; depth++) {
            PerftResult res = perft(new Oth(o), depth);
            double t1 = System.nanoTime();
            System.out.println("Depth " + depth + " : " + (t1 - t0) / 1000000000 + " sec");
            System.out.println("Count = " + res.moveCount);
        }

    }

    static PerftResult perft(Oth o, int depth) {

        PerftResult result = new PerftResult();
        if (depth == 0) {
            result.moveCount++;
            return result;
        }

        o.gen(o.trait);
        List<UtilsClass.Coups> moves = o.lcoups.stream().distinct().toList();
        if (moves.size() != 0) {
            for (UtilsClass.Coups move : moves) {
                o.move = move;
                o.fmove(!undomove);
                PerftResult subPerft = perft(new Oth(o), depth - 1);
                o.fmove(undomove);
                result.moveCount += subPerft.moveCount;

            }
        } else {
            PerftResult subPerft = perft(new Oth(o), depth - 1);
            result.moveCount += subPerft.moveCount;
        }
        return result;
    }

    static class PerftResult {

        public long timeTaken = 0;
        long moveCount = 0;

    }
}