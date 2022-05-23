package othello;

import oth.Constantes;

import java.io.IOException;

import static java.util.stream.IntStream.range;

public class OthPrinter implements Constantes {


    final Oth o;
    int num;


    public OthPrinter(Oth o) {
        this.o = o;

    }

    @Override
    public String toString() {
        StringBuilder spos = new StringBuilder();
        range(0, 100).forEach(_case -> {
            if (o.etats[_case] == Constantes.vide) spos.append("- ");
            else spos.append(print(o.etats[_case])).append(" ");
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

    void affichage() {
        for (UtilsClass.Coups cps : o.lcoups)
            System.out.println(cps);
        System.out.println("num " + num++);
        System.out.println(o.trait == blanc ? "blanc" : "noir");
        System.out.println(SCASES[o.move.sq0()]);
        System.out.println(new OthPrinter(o));
    }


    void resultat(Oth oth) {
        oth.sN = 0;
        oth.sB = 0;
        range(0, 100).forEach(c -> {
            switch (o.etats[c]) {

                case blanc -> oth.sB++;
                case noir -> oth.sN++;
            }
        });

        String R = oth.sB > oth.sN ? "1" : (oth.sN > oth.sB ? "0" : "0.5");
        System.out.println(R + "," + oth.sB + "," + oth.sN);

        try {
            // R = sB > sN ? "1" : (sN > sB ? "0" : "0.5");
            Oth.writter.write(R + "," + oth.sB + "," + oth.sN);
            Oth.writter.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
