package eval;

import othello.Othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static othello.Othello.Coups;
import static othello.Othello.Coups.NOMOVE;

public class OthIA {

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
                List<Othello.ScoreCase> lscorecase = new ArrayList<>();
                lcoups.forEach(coups -> {
                    int sum = coups.lscore().stream().mapToInt(Othello.Score::n).sum();
                    lscorecase.add(new Othello.ScoreCase(coups, sum));
                });
                List<Integer> ln = lscorecase.stream().map(Othello.ScoreCase::sum).toList();
                int max = Collections.max(ln);
                List<Othello.ScoreCase> scoreCases = lscorecase.stream().filter(cc -> cc.sum() == max).toList();
                int index = new Random().nextInt(scoreCases.size());
                return scoreCases.get(index).coups();
            } else return NOMOVE;
        }
    }
}