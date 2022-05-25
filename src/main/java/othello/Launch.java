package othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.stream.IntStream.rangeClosed;

public class Launch {


    public static void main(String[] args) throws IOException {
        new File(Constantes.pathname + Constantes.filename).createNewFile();
        Oth.writter = new FileWriter(Constantes.filename);
        rangeClosed(1, Oth.max).forEach(Launch::partie);
    }

    static void partie(int num) {
        Oth.nb = num;
        new Oth().jouer();
    }
}
