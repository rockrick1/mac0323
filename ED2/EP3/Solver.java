import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable())
            throw new java.lang.IllegalArgumentException();
    }
    // min number of moves to solve initial board
    public int moves()
    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    // test client (see below)
    public static void main(String[] args)
}