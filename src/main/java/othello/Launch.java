package othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.stream.IntStream.rangeClosed;

public class Launch {
    static String filename = "statistiques.txt";
    static String pathname = "C:\\Users\\gille\\IdeaProjects\\OthelloV2\\";
    public static void main(String[] args) throws IOException {
        new File(pathname + filename).createNewFile();
        Othello.writter = new FileWriter(filename);
        rangeClosed(1, Othello.max).forEach(
                num -> {
                    Othello.nb = num;

                    new Othello(null).jouer();
                }

        );
    }

}
