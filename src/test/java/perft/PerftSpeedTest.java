package perft;

import othello.Etat;
import othello.Othello;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static othello.Othello.undomove;
import static othello.Othello.vide;


class PerftSpeedTest {
    public static final int MAX_DEPTH = 10;

    public static void main(String[] args) {
        perftTest();
    }

    static void perftTest() {

        Othello o = new Othello(null);
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


       // o.gen(o.trait);

        range(0, 100).filter(c -> o.etats[c] == vide).forEach(c -> {
            o.caseO = c;
            o.lscore = new ArrayList<>();
            o.DIRS.forEach(d -> {
                o.dir = d;
                Etat etat = o.S0;
                while (true)
                    if ((etat = etat.exec()) == o.S1 || etat == null) break;
            });
        });
        List<Othello.Coups> moves = o.lcoups.stream().distinct().toList();
        if (moves.size() != 0) {
            for (Othello.Coups move : moves) {
                o.move = move;
                //o.fmove(!undomove);
                move.lscore()
                        .forEach(score -> rangeClosed(0, score.n())
                                .forEach(n -> o.etats[move.sq0() + n * score.dir()] = !undomove ? -o.trait : o.trait));
                o.etats[move.sq0()] = !undomove ? vide : o.trait;
                PerftResult subPerft = perft(new Othello(o), depth - 1);
               // o.fmove(undomove);
                move.lscore()
                        .forEach(score -> rangeClosed(0, score.n())
                                .forEach(n -> o.etats[move.sq0() + n * score.dir()] = undomove ? -o.trait : o.trait));
                o.etats[move.sq0()] = undomove ? vide : o.trait;
                result.moveCount += subPerft.moveCount;

            }
        } else {
            PerftResult subPerft = perft(new Othello(o), depth - 1);
            result.moveCount += subPerft.moveCount;
        }
        return result;
    }

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

        Othello o = new Othello(null);
        double t0 = System.nanoTime();
        for (int depth = 1; depth <= MAX_DEPTH; depth++) {
            PerftResult res = perft(new Othello(o), depth);
            double t1 = System.nanoTime();
            System.out.println("Depth " + depth + " : " + (t1 - t0) / 1000000000 + " sec");
            System.out.println("Count = " + res.moveCount + "  /  " + expectcount[depth]);
            //assertEquals(Long.toString(res.moveCount), expectcount[depth]);
        }
    }

    public static class PerftResult {

        public long timeTaken = 0;
        public long moveCount = 0;

    }

}