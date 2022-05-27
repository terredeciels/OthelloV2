package othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static othello.Othello.Coups.NOMOVE;


public class Othello {

    static String filename = "statistiques.txt";
    static String pathname = "C:\\Users\\gille\\IdeaProjects\\OthelloV2\\";
    final int noir = -1, blanc = 1, vide = 0, out = 2;
    int N = -10, E = 1, Ouest = -1, S = 10, NE = -9, SO = 9, NO = -11, SE = 11;
    List<Integer> DIRS = asList(N, NE, E, SE, S, SO, Ouest, NO);
    public static boolean undomove = true;

    int[] ETATS_INIT = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 0, 0, 0, 1, -1, 0, 0, 0, 2,
            2, 0, 0, 0, -1, 1, 0, 0, 0, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    static String[] SCASES = {"xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx",
            "xx", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "xx",
            "xx", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "xx",
            "xx", "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "xx",
            "xx", "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "xx",
            "xx", "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "xx",
            "xx", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "xx",
            "xx", "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "xx",
            "xx", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "xx",
            "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx"};
    static int nb;
    static int max = 100;
    static FileWriter writter;
    public List<Coups> lcoups;
    public int[] etats;
    public Coups move;
    public int trait;

    boolean passe = true;
    boolean findepartie;
    int sN;
    int sB;
    List<Score> lscore;
    int n;
    int caseO;
    int _case;
    int dir;
    Etat S0;
    Etat S1;

   // Othello o;
    int num;
    public static void main(String[] args) throws IOException {
        new File(pathname + filename).createNewFile();
        Othello.writter = new FileWriter(filename);
        rangeClosed(1, Othello.max).forEach(Othello::partie);
    }

    static void partie(int num) {
        Othello.nb = num;
        new Othello().jouer();
    }

    {
        S0 = new Etat() {
            @Override
            Etat exec() {
                n++;
                return nextetat() == -trait ? S0.exec() : S1.exec();
            }
        };
        S1 = new Etat() {
            @Override
            Etat exec() {
                if (memetrait() && n - 1 != 0) {
                    lscore.add(new Score(n - 1, dir));
                    lcoups.add(new Coups(caseO, lscore));
                }
                n = 0;
                return null;
            }
        };
    }

    public Othello(Othello o) {
        etats = o.etats;
        trait = -o.trait;
        lcoups = new ArrayList<>();
    }

    public Othello() {
        etats = ETATS_INIT.clone();
        trait = blanc;//noirs commencent
        lcoups = new ArrayList<>();

    }

    public void jouer() {
        findepartie = false;
        passe = false;
        lcoups = new ArrayList<>();
        while (true) {
            if (!findepartie) {
                gen(trait);
                // o.move = new OthIA().getEvalRandom().eval(o.lcoups.stream().distinct().toList());
                move = getEvalMax().eval(lcoups.stream().distinct().toList());
                passe_et_findepartie();
                changeside();
            } else break;
        }
        resultat(this);
        if (nb == max) {
            try {
                writter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void passe_et_findepartie() {
        if (move == NOMOVE) if (passe) findepartie = true;
        else passe = true;
        else {
            if (passe) passe = false;
            fmove(!undomove);
            // othprint.affichage();
        }
    }

    public void gen(int t) {
        trait = t;
        range(0, 100).filter(c -> etats[c] == vide).forEach(c -> {
            caseO = c;
            lscore = new ArrayList<>();
            DIRS.forEach(this::statemachine);
        });

    }

    void statemachine(int d) {
        dir = d;
        Etat etat = S0;
        while (true)
            if ((etat = etat.exec()) == S1 || etat == null) break;
    }

    public void fmove(boolean undomove) {
        move.lscore()
                .forEach(score -> rangeClosed(0, score.n())
                        .forEach(n -> etats[move.sq0() + n * score.dir()] = undomove ? -trait : trait));
        etats[move.sq0()] = undomove ? vide : trait;
    }

    public boolean memetrait() {
        return etats[_case] == trait;
    }

    int nextetat() {
        return etats[_case = caseO + n * dir];
    }

    public void changeside() {
        trait = -trait;
        lcoups = new ArrayList<>();
    }


    public EvalRandom getEvalRandom() {
        return new EvalRandom();
    }

    public EvalMax getEvalMax() {
        return new EvalMax();
    }

    interface fevaluation {
        Coups eval(List<Coups> lcoups);
    }

    public static class EvalRandom implements fevaluation {

        @Override
        public Coups eval(List<Coups> lcoups) {
            if (lcoups.size() != 0) return lcoups.get(new Random().nextInt(lcoups.size()));
            else return NOMOVE;
        }
    }

    public static class EvalMax implements fevaluation {

        @Override
        public Coups eval(List<Coups> lcoups) {
            if (lcoups.size() != 0) {
                List<ScoreCase> lscorecase = new ArrayList<>();
                lcoups.forEach(coups -> {
                    int sum = coups.lscore().stream().mapToInt(Score::n).sum();
                    lscorecase.add(new ScoreCase(coups, sum));
                });
                List<Integer> ln = lscorecase.stream().map(ScoreCase::sum).toList();
                int max = Collections.max(ln);
                List<ScoreCase> scoreCases = lscorecase.stream().filter(cc -> cc.sum() == max).toList();
                int index = new Random().nextInt(scoreCases.size());
                return scoreCases.get(index).coups();
            } else return NOMOVE;
        }
    }

    @Override
    public String toString() {
        StringBuilder spos = new StringBuilder();
        range(0, 100).forEach(_case -> {
            if (etats[_case] == vide) spos.append("- ");
            else spos.append(print(etats[_case])).append(" ");
            if (_case % 10 == 9) spos.append("\n");
        });
        return spos.toString();
    }

    private String print(int etat) {
        return switch (etat) {
            case vide -> "_";
            case blanc -> "b";
            case noir -> "n";
            case out -> " ";
            default -> "?";
        };
    }

    void resultat(Othello othello) {
        othello.sN = 0;
        othello.sB = 0;
        range(0, 100).forEach(c -> {
            switch (etats[c]) {

                case blanc -> othello.sB++;
                case noir -> othello.sN++;
            }
        });

        String R = othello.sB > othello.sN ? "1" : (othello.sN > othello.sB ? "0" : "0.5");
        System.out.println(R + "," + othello.sB + "," + othello.sN);

        try {
            // R = sB > sN ? "1" : (sN > sB ? "0" : "0.5");
            Othello.writter.write(R + "," + othello.sB + "," + othello.sN);
            Othello.writter.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    abstract static class Etat {
        abstract Etat exec();
    }

    public record Coups(int sq0, List<Score> lscore) {
        public static Coups NOMOVE;


        @Override
        public String toString() {
            return "(" + SCASES[sq0] + ", " + lscore + ")";
        }


    }

    public record Score(int n, int dir) {
        @Override
        public int n() {
            return n;
        }
    }

    public record ScoreCase(Coups coups, int sum) {

    }


}