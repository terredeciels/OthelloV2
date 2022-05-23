package oth;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;


public class Oth implements Constantes {
    public List<Coups> lcoups;
    public int[] etats;
    public Coups move;
    public int trait;
    List<Score> lscore;
    int n;
    int caseO;
    int _case;
    int dir;
    Etat S0, S1;

    {
        S0 = new Etat() {
            @Override
            Etat exec(Oth o) {
                o.n++;
                return o.nextetat() == -o.trait ? S0.exec(o) : S1.exec(o);
            }
        };
        S1 = new Etat() {
            @Override
            Etat exec(Oth o) {
                if (o.memetrait() && o.n - 1 != 0) {
                    o.lscore.add(new Score(o.n - 1, o.dir));
                    o.lcoups.add(new Coups(o.caseO, o.lscore));
                }
                o.n = 0;
                return null;
            }
        };
    }

    public Oth() {
        etats = ETATS_INIT.clone();
        trait = blanc;//noirs commencent
        lcoups = new ArrayList<>();
    }

    public Oth(Oth o) {
        etats = o.etats;
        trait = -o.trait;
        lcoups = new ArrayList<>();
    }


    public void gen(int t) {
        trait = t;
        range(0, 100).filter(c -> etats[c] == vide).forEach(c -> {
            caseO = c;
            lscore = new ArrayList<>();
            DIRS.forEach(this::statemachine);
        });

    }

    private void statemachine(int d) {
        dir = d;
        Etat etat = S0;
        while (true)
            if ((etat = etat.exec(this)) == S1 || etat == null) break;
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

    private int nextetat() {
        return etats[_case = caseO + n * dir];
    }

    public void changeside() {
        trait = -trait;
        lcoups = new ArrayList<>();
    }

    abstract static class Etat {
        abstract Etat exec(Oth o);
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