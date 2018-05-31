import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class CircularSuffixArray {
    private int[] index;
    private String s;
    private String transform;

    // comparador de nodes para MinPQ
    static class PriorityComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return n1.compareTo(n2);
        }
    }

    // usaremos essa classe numa MinPQ para termos as strings em ordem
    // alfabetica e mantendo a informação dos seus indices originais
    public class Node {
        public int index;
        public int suffix;

        public Node(int suffix, int index) {
            this.suffix = suffix;
            this.index = index;
        }

        public int compareTo(Node other) {
            String a = s.substring(this.suffix, length()) + s.substring(0, this.suffix);
            String b = s.substring(other.suffix, length()) + s.substring(0, other.suffix);
            return a.compareTo(b);
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new java.lang.IllegalArgumentException();

        this.s = s;
        String sub = s;
        int length = length();
        // usaremos essa minPQ para retirarmos os indices em ordem alfabetica
        // das strings
        MinPQ<Node> pq = new MinPQ<Node>(new PriorityComparator());

        for (int i = 0; i < length; i++) {
            pq.insert(new Node(i, i));
        }

        transform = "";
        index = new int[length];
        for (int i = 0; i < length; i++) {
            Node v = pq.delMin();
            index[i] = v.index;
            if (v.suffix != 0)
                transform += s.charAt(v.suffix - 1);
            else
                transform += s.charAt(length - 1);
        }
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new java.lang.IllegalArgumentException();
        return index[i];
    }

    // fornece a string formada pelos ultimos chars de cada
    // string na ordem obtida após a ordenação feita acima
    public String transform() {
        return transform;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray CSA = new CircularSuffixArray(s);

        for (int i = 0; i < CSA.length(); i++)
            StdOut.println(CSA.index(i));
        StdOut.println(CSA.transform());
    }
}