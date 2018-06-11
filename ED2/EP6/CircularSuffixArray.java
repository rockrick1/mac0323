import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import java.util.Comparator;

public class CircularSuffixArray {
    private int[] index;
    private int length;
    private int first;
    private String s;
    private String transform;
    private Node[] array;

    // usaremos essa no Quick3node para termos as strings em ordem
    // alfabetica e mantendo a informação dos seus indices originais
    // tambem mantem o ultimo char da string que a node representa
    public class Node {
        public int index;
        public int suffix;
        public char c;

        public Node(int suffix, int index, char c) {
            this.suffix = suffix;
            this.index = index;
            this.c = c;
        }

        public int compareTo(Node other) {
            int cmp;
            int a = this.suffix;
            int b = other.suffix;
            while (a+1 != this.suffix) {
                if (a == length) a = 0;
                if (b == length) b = 0;
                Character sa = s.charAt(a);
                Character sb = s.charAt(b);
                cmp = sa.compareTo(sb);
                if (cmp != 0) return cmp;
                a++; b++;
            }
            return 0;
            // String a = s.substring(this.suffix, length()) + s.substring(0, this.suffix);
            // String b = s.substring(other.suffix, length()) + s.substring(0, other.suffix);
            // return a.compareTo(b);
        }
    }

    // implementação "custom" do quick3string
    // alterações feitas para usar um vetor de Nodes, que contem o
    // indice do prefixo da string
    public void Quick3node(Node[] a) {
        sort(a, 0, length - 1, 0);
    }

    private int charAt(Node n, int d) {
        assert d >= 0 && d <= length;
        if (d == length) return -1;
        int idx = (d+n.suffix) % length;
        return s.charAt(idx);
    }

    private void sort(Node[] a, int lo, int hi, int d) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + 15) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else i++;
        }

        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);
    }

    private void insertion(Node[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    private void exch(Node[] a, int i, int j) {
        Node temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private boolean less(Node v, Node w, int d) {
        if (v.compareTo(w) < 0) return true;
        else return false;
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new java.lang.IllegalArgumentException();

        this.s = s;
        length = s.length();

        array = new Node[length];

        for (int i = 0; i < length; i++) {
            array[i] = new Node(i, i, s.charAt((length - 1 + i)%length));
        }

        // let there be light
        Quick3node(array);

        index = new int[length];
        for (int i = 0; i < length; i++) {
            Node v = array[i];
            index[i] = v.index;
            if (v.index == 0) this.first = i;
            transform += v.c;
        }
    }

    // length of s
    public int length() {
        return length;
    }

    // indice do first para ser usado no BurrowsWheeler
    public int first() {
        return first;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length)
            throw new java.lang.IllegalArgumentException();
        return index[i];
    }

    // fornece a string formada pelos ultimos chars de cada
    // string na ordem obtida após a ordenação feita acima
    public String transform() {
        transform = "";
        for (int i = 0; i < length; i++) {
            Node v = array[i];
            if (v.index == 0) this.first = i;
            transform += v.c;
        }
        return transform;
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray CSA = new CircularSuffixArray(BinaryStdIn.readString());

        for (int i = 0; i < CSA.length(); i++)
            StdOut.println(CSA.index(i));
        StdOut.println(CSA.transform());
    }
}