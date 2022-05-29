package othello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.stream.IntStream.rangeClosed;
import static othello.Othello.*;

public class Launch {
    static String filename = "statistiques.txt";
    static String pathname = "C:\\Users\\gille\\IdeaProjects\\OthelloV2\\";
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

}
