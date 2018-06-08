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
        int a = (v.suffix + d) % length;
        int b = (w.suffix + d) % length;
        for (; a+1 != v.suffix; a++, b++) {
            if (a == length) a = 0;
            if (b == length) b = 0;
            if (s.charAt(a) < s.charAt(b)) return true;
            else if (s.charAt(a) > s.charAt(b)) return false;
        }
        return false;
    }

    // usaremos essa no Quick3node para termos as strings em ordem
    // alfabetica e mantendo a informação dos seus indices originais
    public class Node {
        public int index;
        public int suffix;

        public Node(int suffix, int index) {
            this.suffix = suffix;
            this.index = index;
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

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new java.lang.IllegalArgumentException();

        this.s = s;
        length = s.length();
        StdOut.println("l: "+length);

        Node[] array = new Node[length];

        for (int i = 0; i < length; i++) {
            array[i] = new Node(i, i);
        }

        // let there be light
        Quick3node(array);

        transform = "";
        index = new int[length];
        for (int i = 0; i < length; i++) {
            Node v = array[i];
            index[i] = v.index;
            if (v.index == 0) first = v.index;
            if (v.suffix != 0)
                transform += s.charAt(v.suffix - 1);
            else
                transform += s.charAt(length - 1);
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