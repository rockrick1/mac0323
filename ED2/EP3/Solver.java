import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;
import java.util.Comparator;

public class Solver {
    private MinPQ<Board> vistos;
    private MinPQ<Board> vistos_nao_exam;
    private int moves;

    // critério de comparação para as MinPQ's
    static class PriorityComparator implements Comparator<Board> {
        public int compare(Board b1, Board b2) {
            return b1.priority() - b2.priority();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable())
            throw new java.lang.IllegalArgumentException();

        vistos = new MinPQ<Board>(new PriorityComparator());
        vistos_nao_exam = new MinPQ<Board>(new PriorityComparator());

        // A* search
        // o menor caminho do inicio de vistos ate
        // o tab final será a solução
        vistos.insert(initial);
        vistos_nao_exam.insert(initial);

        Board b;
        Iterator<Board> it;
        boolean contains;

        while(true) {
            b = vistos_nao_exam.delMin();
            if (b.isGoal()) break;

            // checa se o heap contém os tabuleiros
            // vizinhos do atual
            for (Board v : b.neighbors()) {
                contains = false;

                it = vistos.iterator();
                while (it.hasNext()) {
                    Board comp = it.next();
                    if (comp.equals(v)) {
                        contains = true;
                        break;
                    }
                }

                // se nao contém, insere ele
                if (!contains) {
                    vistos.insert(v);
                    vistos_nao_exam.insert(v);
                }
            }
        }
        moves = b.moves;
        StdOut.println("deu bom");
        vistos.delMin().print();
    }
    // min number of moves to solve initial board
    public int moves() {
        return moves;
    }
    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Board b;
        Iterator<Board> it;
        Stack<Board> solution = new Stack<Board>();
    }
    // public static void main(String[] args)
}