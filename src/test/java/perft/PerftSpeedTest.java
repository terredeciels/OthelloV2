package perft;

import othello.Othello;

import java.util.List;


import static othello.Othello.undomove;


class PerftSpeedTest {
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
    @org.junit.jupiter.api.Test
    void main() {
        String[] expectcount = new String[]{"", "4", "12", "56", "244", "1396", "8200", "55092",
                "390216", "3005288", "24571284", "212258800", "1939886636", "18429641748", "184042084512"};

        Othello o = new Othello();
        double t0 = System.nanoTime();
        for (int depth = 1; depth <= MAX_DEPTH; depth++) {
            PerftResult res = perft(new Othello(o), depth);
            double t1 = System.nanoTime();
            System.out.println("Depth " + depth + " : " + (t1 - t0) / 1000000000 + " sec");
            System.out.println("Count = " + res.moveCount + "  /  " + expectcount[depth]);
            //assertEquals(Long.toString(res.moveCount), expectcount[depth]);
        }
    }
    public static final int MAX_DEPTH = 10;

    public static void main(String[] args) {
        perftTest();
    }

    static void perftTest() {

        Othello o = new Othello();
        double t0 = System.nanoTime();
        for (int depth = 1; depth <= MAX_DEPTH; depth++) {
            PerftResult res = perft(new Othello(o), depth);
            double t1 = System.nanoTime();
            System.out.println("Depth " + depth + " : " + (t1 - t0) / 1000000000 + " sec");
            System.out.println("Count = " + res.moveCount);
        }

    }

    public static PerftResult perft(Othello o, int depth) {

        PerftResult result = new PerftResult();
        if (depth == 0) {
            result.moveCount++;
            return result;
        }

        o.gen(o.trait);
        List<Othello.Coups> moves = o.lcoups.stream().distinct().toList();
        if (moves.size() != 0) {
            for (Othello.Coups move : moves) {
                o.move = move;
                o.fmove(!undomove);
               PerftResult subPerft = perft(new Othello(o), depth - 1);
                o.fmove(undomove);
                result.moveCount += subPerft.moveCount;

            }
        } else {
           PerftResult subPerft = perft(new Othello(o), depth - 1);
            result.moveCount += subPerft.moveCount;
        }
        return result;
    }

    public static class PerftResult {

        public long timeTaken = 0;
        public long moveCount = 0;

    }

}