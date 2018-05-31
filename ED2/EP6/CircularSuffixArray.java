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
        public int i;
        public String s;

        public Node(String s, int i) {
            this.s = s;
            this.i = i;
        }

        public int compareTo(Node other) {
            return this.s.compareTo(other.s);
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
            pq.insert(new Node(sub, i));
            // não precisamos fazer isso na ultima iteração desse for
            if (i != length - 1) {
                char c = sub.charAt(0);
                sub = sub.substring(1,length);
                sub += c;
            }
        }

        transform = "";
        index = new int[length];
        for (int i = 0; i < length; i++) {
            Node v = pq.delMin();
            index[i] = v.i;
            transform += v.s.charAt(length - 1);
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
        String s = "I am a blind cave salamander";
        CircularSuffixArray CSA = new CircularSuffixArray(s);

        for (int i = 0; i < CSA.length(); i++)
            StdOut.println(CSA.index(i));
    }
}