import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Board {
    private int[][] board;
    private int n;

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = tiles;
    }

    // string representation of this board
    // função copiada do FAQ de Princeton do EP
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                s.append(String.format("%2d ", tileAt(row, col)));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n)
            throw new java.lang.IllegalArgumentException();
        return board[row][col];
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int ret = 0;
        int current = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != current)
                    ret++;
            }
        }
        return ret;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int goal_row, goal_col; // onde tem que estar
        int cur;
        int dist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cur = tileAt(i, j); // valor do tile atual
                if (cur != 0) {
                    goal_row = (cur - 1) / size();
                    goal_col = (cur - 1) % size();
                    dist += Math.abs(i - goal_row);
                    dist += Math.abs(j - goal_col);
                }
            }
        }
        return dist;
    }

    // is this board the goal board?
    // temporario!!!
    public boolean isGoal() {
        if (hamming() == 0) return true;
        return false;
    }

    public boolean equals(Object y) {
        return this.equals(y);
    }

    // retorna o numero de inversões no tabuleiro
    private int inversions() {
        return 0;
    }

    // public Iterable<Board> neighbors() {
    //     return 0;
    // }
    public boolean isSolvable() {
        if (!(n % 2)) {

        }
    }

    public void print() {
        StdOut.print(toString());
    }

    public static void main(String[] args) {
        StdOut.println(9%9);
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                blocks[row][col] = in.readInt();
        Board b = new Board(blocks);
        b.print();
        StdOut.println(b.manhattan());
    }
}