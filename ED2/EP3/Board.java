import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

public class Board {
    private int[][] board;
    private int n;
    public int moves; // movimentos feitos para chegar nesse estado

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new java.lang.IllegalArgumentException();
        n = tiles.length;
        moves = 0;
        // inicializa o board
        board = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = tiles[i][j];
    }

    public void set(int i, int j, int val) {
        board[i][j] = val;
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
                if (tileAt(i,j) != current && tileAt(i,j) != 0)
                    ret++;
                current++;
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

    public int priority() {
        return hamming() + moves;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int current = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tileAt(i, j) != current && tileAt(i, j) != 0)
                    return false;
                current++;
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        // excessoes basicas
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board other = (Board) y;

        // checa o n primeiro
        if (other.n != this.n) return false;

        // checa casa por casa
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (this.tileAt(i,j) != other.tileAt(i,j))
                    return false;
        return true;
    }

    // retorna o numero de inversoes no tabuleiro
    private int inversions() {
        int k = 0; // numero de inversoes
        int cur, comp;
        // Board b = new Board(board);

        for (int i = 0; i < n*n; i++) {
            cur = tileAt(i/n,i%n);
            for (int p = i; p < n*n; p++) {
                comp = tileAt(p/n,p%n);
                if (cur > comp && cur != 0 && comp != 0) {
                    k++;
                }
            }
        }
        // while(i < n*n) {
        //     if (i+1 != tileAt(i%n, i/n)) {
        //         // guarda o numero que esta na casa errada
        //         temp = b.board[i%n][i/n];
        //         // coloca o numero certo
        //         b.board[i%n][i/n] = i + 1;
        //         // vai para a casa que devia estar o numero gaurdado
        //         if (temp != 0)
        //             i = temp - 1;
        //         else
        //             i = n*n - 1;
        //         k = 1 - k;
        //     }
        //     else
        //         i++;
        // }
        StdOut.println(k + " inversoes");
        return k;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        Board b;
        int z_row = -1, z_col = -1;

        // encontra a posição em que a peça vazia se encontra
        for (int i = 0; i < n && z_row == -1; i++)
            for (int j = 0; j < n && z_row == -1; j++)
                if (tileAt(i, j) == 0) {
                    z_row = i;
                    z_col = j;
                }

        if (z_row - 1 >= 0) {
            b = new Board(this.board);
            // move a peça e atualiza os moves
            b.set(z_row, z_col, b.board[z_row - 1][z_col]);
            b.set(z_row - 1, z_col, 0);
            b.moves = this.moves + 1;
            // coloca o tabuleiro na fila
            q.enqueue(b);
        }
        if (z_row + 1 < n) {
            b = new Board(this.board);
            b.set(z_row, z_col, b.board[z_row + 1][z_col]);
            b.set(z_row + 1, z_col, 0);
            b.moves = this.moves + 1;
            q.enqueue(b);
        }
        if (z_col - 1 >= 0) {
            b = new Board(this.board);
            b.set(z_row, z_col, b.board[z_row][z_col - 1]);
            b.set(z_row, z_col - 1, 0);
            b.moves = this.moves + 1;
            q.enqueue(b);
        }
        if (z_col + 1 < n) {
            b = new Board(this.board);
            b.set(z_row, z_col, b.board[z_row][z_col + 1]);
            b.set(z_row, z_col + 1, 0);
            b.moves = this.moves + 1;
            q.enqueue(b);
        }
        return q;
    }

    public boolean isSolvable() {
        if ((n % 2) != 0)
            return inversions() % 2 == 0; // se for par, é possivel resolver
        else {
            int zero_row = -1;
            // encontra a linha em que a peça vazia se encontra
            for (int i = 0; i < n && zero_row == -1; i++)
                for (int j = 0; j < n && zero_row == -1; j++)
                    if (tileAt(i, j) == 0)
                        zero_row = i;

            return (inversions() + zero_row) % 2 == 1;
        }
    }

    public void print() {
        StdOut.print(toString());
    }

    public static void main(String[] args) {
        // StdOut.println(9%9);
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                blocks[row][col] = in.readInt();
        Board b = new Board(blocks);
        b.print();
        Solver s = new Solver(b);
        // StdOut.println("\nneighbors:");
        //
        // Iterable<Board> q;
        // q = b.neighbors();
        // Iterator<Board> it = q.iterator();
        // while (it.hasNext()) {
        //     Board valor = it.next();
        //     StdOut.println("moves "+valor.moves);
        //     valor.print();
        // }
        // StdOut.println(b2.isSolvable());
    }
}