import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;
import java.util.Comparator;

public class Solver {
    private MinPQ<Node> vistos;
    private MinPQ<Node> vistos_nao_exam;
    private Node last; // Ultima node da solução/tabuleiro resolvido
    private int moves; // Quantidade de movimentos para resolver


    // critério de comparação para as MinPQ's
    static class PriorityComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return n1.priority() - n2.priority();
        }
    }


    // Estrutura para fazer a busca da sequencia da soluçao do board inicial
    public class Node {
        public Board b;
        public int priority;
        public Node parent;

        public Node(Board init) {
            b = init;
            parent = null;
            priority = init.priority();
        }
        public void set_parent(Node p) {
            parent = p;
        }
        public int priority() {
            return priority;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable())
            throw new java.lang.IllegalArgumentException();

        vistos = new MinPQ<Node>(new PriorityComparator());
        vistos_nao_exam = new MinPQ<Node>(new PriorityComparator());

        // A* search
        // o menor caminho do inicio de vistos ate o tab final será a solução
        Node n = new Node(initial);
        vistos.insert(n);
        vistos_nao_exam.insert(n);

        while(true) {
            n = vistos_nao_exam.delMin();

            if (n.b.isGoal()) break;

            // checa se o heap contém os tabuleiros
            // vizinhos do atual
            for (Board v : n.b.neighbors()) {
                // define o pai do atual
                Node nv = new Node(v);
                nv.set_parent(n);

                // se nao contém, insere ele

                // Se o avo dele nao for null, compara pra ver se
                // é igual ele. Se for, nao insere.
                if (nv.parent != null && nv.parent.parent != null) {
                    Board grandad = nv.parent.parent.b;
                    if (!nv.b.equals(grandad)) {
                        vistos.insert(nv);
                        vistos_nao_exam.insert(nv);
                    }
                }
                // se for null, só insere normal
                else {
                    vistos.insert(nv);
                    vistos_nao_exam.insert(nv);
                }
            }
        }
        last = n;
        moves = n.b.moves;
    }
    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }
    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Node n;
        Stack<Board> solution = new Stack<Board>();

        n = last;
        while (n != null) {
            solution.push(n.b);
            n = n.parent;
        }
        return solution;
    }
    // public static void main(String[] args)
}