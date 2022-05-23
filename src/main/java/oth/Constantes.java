package oth;

import java.util.List;

import static java.util.Arrays.asList;

public interface Constantes {
    String filename = "statistiques.txt";
    String pathname = "C:\\Users\\gille\\IdeaProjects\\OthME\\";
    int noir = -1, blanc = 1, vide = 0, out = 2;
    int N = -10, E = 1, Ouest = -1, S = 10, NE = -9, SO = 9, NO = -11, SE = 11;
    List<Integer> DIRS = asList(N, NE, E, SE, S, SO, Ouest, NO);
    boolean undomove = true;

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
    String[] SCASES = {"xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx",
            "xx", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "xx",
            "xx", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "xx",
            "xx", "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "xx",
            "xx", "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "xx",
            "xx", "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "xx",
            "xx", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "xx",
            "xx", "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "xx",
            "xx", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "xx",
            "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx"};
}