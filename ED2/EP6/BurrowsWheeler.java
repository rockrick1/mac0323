import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        // String input = BinaryStdIn.readString();
        CircularSuffixArray CSA = new CircularSuffixArray(BinaryStdIn.readString());

        StdOut.println("oi");
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
        Arrays.sort(sorted);

        char prev = sorted[0];
        int seguidos = 0;
        for (int i = 0; i < length; i++) {
            char c = sorted[i];

            if (c != prev) seguidos = 1;
            else seguidos++;

            int pos; // posição que o char c está na string transform
            int count; // contador para quantas vezes ja achamos o
            // char sendo procurado
            for (pos = 0, count = seguidos; pos < length; pos++) {
                if (transform.charAt(pos) == c) {
                    count--;
                    if (count == 0)
                        break;
                }
            }
            next[i] = pos;
            prev = c;
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