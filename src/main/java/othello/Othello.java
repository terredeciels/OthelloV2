package othello;

import eval.OthIA;
import oth.Constantes;
import oth.Oth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.stream.IntStream.rangeClosed;
import static oth.Oth.Coups.NOMOVE;

public class Othello implements Constantes {

    static int nb;
    static int max = 100;
    static FileWriter writter;
    OthPrinter othprint;
    Oth o;
    boolean passe = true;
    boolean findepartie;
    int sN;
    int sB;


    public Othello() {
        o = new Oth();
        othprint = new OthPrinter(o);
    }

    public static void main(String[] args) throws IOException {
        new File(pathname + filename).createNewFile();
        writter = new FileWriter(filename);
        rangeClosed(1, max).forEach(Othello::partie);
    }

    private static void partie(int num) {
        nb = num;
        new Othello().jouer();
    }

    public void jouer() {
        findepartie = false;
        passe = false;
        o.lcoups = new ArrayList<>();
        while (true) {
            if (!findepartie) {
                o.gen(o.trait);
                // o.move = new OthIA().getEvalRandom().eval(o.lcoups.stream().distinct().toList());
                o.move = new OthIA().getEvalMax().eval(o.lcoups.stream().distinct().toList());
                passe_et_findepartie();
                o.changeside();
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
        if (o.move == NOMOVE) if (passe) findepartie = true;
        else passe = true;
        else {
            if (passe) passe = false;
            o.fmove(!o.undomove);
            // othprint.affichage();
        }
    }

}