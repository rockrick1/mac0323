import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        // String input = BinaryStdIn.readString();
        CircularSuffixArray CSA = new CircularSuffixArray(BinaryStdIn.readString());

        // acha o first
        int first = 0;
        int length = CSA.length();
        for (int i = 0; i < length; i++)
            if (CSA.index(i) == 0) {
                first = i;
                break;
            }

        String transform = CSA.transform();
        BinaryStdOut.write(first);
        BinaryStdOut.write(transform);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();

        String transform = BinaryStdIn.readString();
        
        char[] sorted = transform.toCharArray();
        Arrays.sort(sorted);

    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();

    }
}