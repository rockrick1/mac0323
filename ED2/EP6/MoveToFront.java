import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {
    // sequencia de chars
    private static class List {
        public Node root;

        public class Node {
            public char c;
            public Node next;

            public Node(char c) {
                this.c = c;
            }
            public void setChar(char c) {
                this.c = c;
            }
        }

        // inicializa uma lista ligada com cada nó contendo um char,
        // inicialmente ordenada de acordo com ASCII extendido
        public List() {
            for (int i = 255; i >= 0; i--) {
                char c = (char)i;
                Node n = new Node(c);
                n.next = root;
                root = n;
            }
        }

        public char charAt(int idx) {
            if (idx < 0 || idx > 255)
                throw new java.lang.IllegalArgumentException();

            int i = 0;
            Node n = root;
            while (true) {
                if (i == idx) return n.c;
                i++;
                n = n.next;
            }
        }

        // procura o char c na lista, coloca ele como nova root
        // e atualiza o next da node anterior a ele
        // retorna o indice que ele estava antes de mover
        public int putInStart(char c) {
            Node prev, n;
            n = root;
            prev = root;
            int i = 0;

            while (n.c != c) {
                prev = n;
                n = n.next;
                i++;
            }
            if (i != 0) {
                prev.next = n.next;
                n.next = root;
                root = n;
            }

            return i;
        }

        // função análoga àcima, mas que retorna o char na i-ésima posição
        // e o coloca na primeira logo antes
        public char putInStart(int idx) {
            Node prev, n;
            n = root;
            prev = root;
            int i = 0;

            while (i != idx) {
                prev = n;
                n = n.next;
                i++;
            }
            if (i != 0) {
                prev.next = n.next;
                n.next = root;
                root = n;
            }

            return n.c;
        }
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // inicializa a sequencia
        List seq = new List();

        while(!BinaryStdIn.isEmpty()) {
            // w = 8 pois 2^8 = 256
            char c = BinaryStdIn.readChar(8);

            // procura o char da entrada na sequencia atual e o escreve
            int i = seq.putInStart(c);
            BinaryStdOut.write(i,8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // inicializa a sequencia
        List seq = new List();

        while(!BinaryStdIn.isEmpty()) {
            // w = 8 pois 2^8 = 256
            int i = BinaryStdIn.readInt(8);

            // procura o char da entrada na sequencia atual e o escreve
            char c = seq.putInStart(i);
            BinaryStdOut.write(c,8);
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
    }
}