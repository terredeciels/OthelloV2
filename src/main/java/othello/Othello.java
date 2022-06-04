package othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static othello.Othello.Coups.NOMOVE;


public class Othello {
    public static void main(String[] args) throws IOException {
        new File(pathname + filename).createNewFile();
        writter = new FileWriter(filename);
        rangeClosed(1, max).forEach(
                num -> {
                    nb = num;

                    new Othello(null).jouer();
                }

        );
    }

    static String filename = "statistiques.txt";
    static String pathname = "C:\\Users\\gille\\IdeaProjects\\OthelloV2\\";
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
    static FileWriter writter;
    final static int noir = -1;
    final static int blanc = 1;
    public final static int vide = 0;
    final static int out = 2;
    final static int max = 1;
    static int nb = 0;
    public List<Coups> lcoups;
    public int[] etats;
    public Coups move;
    int N = -10, E = 1, Ouest = -1, S = 10, NE = -9, SO = 9, NO = -11, SE = 11;
    public List<Integer> DIRS = asList(N, NE, E, SE, S, SO, Ouest, NO);
    boolean passe = true, findepartie;
   public static boolean undomove ;
    int sN;
    int sB;
    int n;
    public int caseO;
    int _case;
    public int dir;
    public int trait;
    public List<Score> lscore;
    public Etat S0, S1;

    public Othello(Othello o) {
        etats = o == null ? ETATS_INIT.clone() : o.etats;
        trait = o == null ? noir : -o.trait;
        lcoups = new ArrayList<>();
        S1 = new Etat() {
            @Override
            public Etat exec() {
                if (etats[_case] == trait && n - 1 != 0) {
                    lscore.add(new Score(n - 1, dir));
                    lcoups.add(new Coups(caseO, lscore));
                }
                n = 0;
                return null;
            }
        };
        S0 = new Etat() {
            @Override
            public Etat exec() {
                n++;
                return etats[_case = caseO + n * dir] == -trait ? S0.exec() : S1.exec();
            }
        };
    }

    public void jouer() {
        findepartie = false;
        passe = false;
        lcoups = new ArrayList<>();
        while (true) {
            if (!findepartie) {

                range(0, 100).filter(c -> etats[c] == vide).forEach(c -> {
                    caseO = c;
                    lscore = new ArrayList<>();
                    DIRS.forEach(d -> {
                        dir = d;
                        Etat etat = S0;
                        while (true)
                            if ((etat = etat.exec()) == S1 || etat == null) break;
                    });
                });

                // move = EvalRandom(lcoups.stream().distinct().toList());
                move = (lcoups.size() != 0) ? lcoups.get(new Random().nextInt(lcoups.size()))
                        : NOMOVE;
                if (move == NOMOVE) if (passe) findepartie = true;
                else passe = true;
                else {
                    if (passe) passe = false;

                    fmove(undomove);
                    affichage(this);
                }

                trait = -trait;
                lcoups = new ArrayList<>();
            } else break;
        }
        sN = 0;
        sB = 0;
        range(0, 100).forEach(c -> {
            switch (etats[c]) {

                case blanc -> sB++;
                case noir -> sN++;
            }
        });

        String R = sB > sN ? "1" : (sN > sB ? "0" : "0.5");
        System.out.println(R + "," + sB + "," + sN);

        try {
            writter.write(R + "," + sB + "," + sN);
            writter.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (nb == max) {
            try {
                writter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void fmove(boolean undomove) {
        move.lscore()
                .forEach(score -> rangeClosed(0, score.n())
                        .forEach(n -> etats[move.sq0() + n * score.dir()] = undomove ? -trait : trait));
        etats[move.sq0()] = undomove ? vide : trait;
    }

    void affichage(Othello o) {
        for (Othello.Coups cps : o.lcoups)
            System.out.println(cps);
        System.out.println("num " + nb++);
        System.out.println(o.trait == blanc ? "blanc" : "noir");
        System.out.println(SCASES[o.move.sq0()]);
        System.out.println(o);
    }

    @Override
    public String toString() {
        StringBuilder spos = new StringBuilder();
        range(0, 100).forEach(_case -> {
            if (etats[_case] == vide) spos.append("- ");
            else {
                String print = switch (etats[_case]) {
                    case vide -> "_";
                    case blanc -> "b";
                    case noir -> "n";
                    case out -> " ";
                    default -> "?";
                };
                spos.append(print).append(" ");
            }
            if (_case % 10 == 9) spos.append("\n");
        });
        return spos.toString();
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

    public abstract static class Etat {
        public abstract Etat exec();


    }
}