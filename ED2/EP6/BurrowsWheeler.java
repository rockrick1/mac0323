import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BurrowsWheeler {

    // lista duplamente ligada de nodes que contém um char e um indice
    public static class CharList {
        private static CharNode root;

        // node contem um char e um indice, previous e next
        public class CharNode {
            public char c;
            public int idx;
            public CharNode prev, next;

            public CharNode(char c, int i) {
                this.c = c;
                this.idx = i;
            }
        }

        // inicia a lista de acordo com o vetor dado
        public CharList(char[] chars, int n) {
            for (int i = n-1; i >= 0; i--) {
                CharNode v = new CharNode(chars[i], i);
                v.next = root;
                if (root != null) root.prev = v;
                root = v;
            }
        }

        // remove a node com char c e retorna seu index original
        public static int removeChar(char c) {
            for (CharNode v = root; v != null; v = v.next) {
                if (v.c == c) {
                    if (v.prev != null)
                        v.prev.next = v.next;
                    else
                        root = v.next;
                    if (v.next != null)
                        v.next.prev = v.prev;
                    int ret = v.idx;
                    v = null;

                    return ret;
                }
            }
            return 0;
        }

        private static void printList() {
            for (CharNode v = root; v != null; v = v.next)
                StdOut.print(v.c);
            StdOut.println();
        }

    }


    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        // String input = BinaryStdIn.readString();
        CircularSuffixArray CSA = new CircularSuffixArray(BinaryStdIn.readString());

        int first = CSA.first();
        int length = CSA.length();

        String transform = CSA.transform();
        BinaryStdOut.write(first);
        BinaryStdOut.write(transform);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();

        String transform = BinaryStdIn.readString();
        int length = transform.length();
        int next[] = new int[length];

        // vetor de chars, é a string transform ordenada
        char[] sorted = transform.toCharArray();
        // monta a CharList antes de ordenar, pois usa a string transformada
        // junto com a ordenada
        CharList CL = new CharList(sorted, length);

        // let there be light, once again
        Arrays.sort(sorted);

        char prev = sorted[0];
        int seguidos = 0;
        for (int i = 0; i < length; i++) {
            char c = sorted[i];
            next[i] = CL.removeChar(c);
        }

        // printa o texto original
        int index = first;
        for (int i = 0; i < length; i++) {
            char c = sorted[index];
            BinaryStdOut.write(c);
            index = next[index];
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
    }
}