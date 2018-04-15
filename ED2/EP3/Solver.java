import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;
import java.util.Comparator;

public class Solver {
    private MinPQ<Board> vistos;
    private MinPQ<Board> vistos_nao_exam;

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
        int ble = 1;

        while(true) {
            contains = false;
            b = vistos_nao_exam.delMin();
            if (b.isGoal()) break;

            // checa se o heap contém os tabuleiros
            // vizinhos do atual
            for (Board v : b.neighbors()) {
                StdOut.println("prox nei");
                it = vistos.iterator();
                while (it.hasNext()) {
                    Board comp = it.next();
                    if (true)
                        comp.print();
                    if (comp.equals(v)) {
                        StdOut.println("oh");
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
            ble++;
        }
        StdOut.println("deu bom");
    }
    // min number of moves to solve initial board
    // public int moves()
    // // sequence of boards in a shortest solution
    // public Iterable<Board> solution()
    // // test client (see below)
    // public static void main(String[] args)
}