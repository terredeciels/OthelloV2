package othello;

import eval.OthIA;
import oth.Constantes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static othello.Othello.Coups.NOMOVE;

public class Othello implements Constantes {

    static int nb;
    static int max = 100;
    static FileWriter writter;
    public List<Coups> lcoups;
    public int[] etats;
    public Coups move;
    public int trait;
    OthPrinter othprint;
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
    {
        S0 = new Othello.Etat() {
            @Override
            Othello.Etat exec() {
                n++;
                return nextetat() == -trait ? S0.exec() : S1.exec();
            }
        };
        S1 = new Othello.Etat() {
            @Override
            Othello.Etat exec() {
                if (memetrait() && n - 1 != 0) {
                    lscore.add(new Othello.Score(n - 1, dir));
                    lcoups.add(new Othello.Coups(caseO, lscore));
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
        othprint = new OthPrinter(this);
    }

    public static void main(String[] args) throws IOException {
        new File(pathname + filename).createNewFile();
        writter = new FileWriter(filename);
        rangeClosed(1, max).forEach(Othello::partie);
    }

     static void partie(int num) {
        nb = num;
        new Othello().jouer();
    }

    public void jouer() {
        findepartie = false;
        passe = false;
        lcoups = new ArrayList<>();
        while (true) {
            if (!findepartie) {
                gen(trait);
                // o.move = new OthIA().getEvalRandom().eval(o.lcoups.stream().distinct().toList());
                move = new OthIA().getEvalMax().eval(lcoups.stream().distinct().toList());
                passe_et_findepartie();
                changeside();
            } else break;
        }
        othprint.resultat(this);
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