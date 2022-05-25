package othello;

import java.util.List;

public class UtilsClass {
    abstract static class Etat {
        abstract Etat exec();
    }

    public record Coups(int sq0, List<Score> lscore) {
        public static Coups NOMOVE;



        @Override
        public String toString() {
            return "(" + Constantes.SCASES[sq0] + ", " + lscore + ")";
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
