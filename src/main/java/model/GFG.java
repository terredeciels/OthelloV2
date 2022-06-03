package model;
// Java implementation to print
// N-ary Tree graphically

import java.util.Arrays;
import java.util.Vector;
//                 0
//               / | \
//               /  |  \
//               1   2   3
//               / \    / | \
//               4   5  6  7  8
//               |
//               9

//        0
//        +--- 1
//        |    +--- 4
//        |    +--- 5
//        +--- 2
//        +--- 3
//        |    +--- 6
//        |    +--- 7
//        |        +--- 9
//        |    +--- 8
class GFG {

    // Structure of the node
    static class tnode {
        int n;
        Vector<tnode> root = new Vector<>();

        tnode(int data) {
            this.n = data;
        }
    }

    ;

    // Function to print the
// N-ary tree graphically
    static void printNTree(tnode x,
                           boolean[] flag,
                           int depth, boolean isLast) {
        // Condition when node is None
        if (x == null)
            return;

        // Loop to print the depths of the
        // current node
        for (int i = 1; i < depth; ++i) {

            // Condition when the depth
            // is exploring
            if (flag[i]) {
                System.out.print("| "
                        + " "
                        + " "
                        + " ");
            }

            // Otherwise print
            // the blank spaces
            else {
                System.out.print(" "
                        + " "
                        + " "
                        + " ");
            }
        }

        // Condition when the current
        // node is the root node
        if (depth == 0)
            System.out.println(x.n);

            // Condition when the node is
            // the last node of
            // the exploring depth
        else if (isLast) {
            System.out.print("+--- " + x.n + '\n');

            // No more childrens turn it
            // to the non-exploring depth
            flag[depth] = false;
        } else {
            System.out.print("+--- " + x.n + '\n');
        }

        int it = 0;
        for (tnode i : x.root) {
            ++it;

            // Recursive call for the
            // children nodes
            printNTree(i, flag, depth + 1,
                    it == (x.root.size()) - 1);
        }
        flag[depth] = true;
    }

    // Function to form the Tree and
// print it graphically
    static void formAndPrintTree() {
        int nv = 10;
        tnode r = new tnode(0);
        tnode n1 = new tnode(1);
        tnode n2 = new tnode(2);
        tnode n3 = new tnode(3);
        tnode n4 = new tnode(4);
        tnode n5 = new tnode(5);
        tnode n6 = new tnode(6);
        tnode n7 = new tnode(7);
        tnode n8 = new tnode(8);
        tnode n9 = new tnode(9);

        // Array to keep track
        // of exploring depths

        boolean[] flag = new boolean[nv];
        Arrays.fill(flag, true);

        // Tree Formation
        r.root.add(n1);
        n1.root.add(n4);
        n1.root.add(n5);
        r.root.add(n2);
        r.root.add(n3);
        n3.root.add(n6);
        n3.root.add(n7);
        n7.root.add(n9);
        n3.root.add(n8);

        printNTree(r, flag, 0, false);
    }

    // Driver Code
    public static void main(String[] args) {

        // Function Call
        formAndPrintTree();
    }
}
