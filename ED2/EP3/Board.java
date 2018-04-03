public class Board {
    private int[][] board;
    private int n;

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.legth;
        // board = (int[][]) new int[n][n];
        board = tiles; // would be nice
    }

    // string representation of this board
    public String toString() {
        String s;
        s = String.valueOf(n) + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s += " " + String.valueOf(board[i][j]);
            }
            s += "\n";
        }
        return s;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
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
    public int manhattan()                 // sum of Manhattan distances between tiles and goal

    // is this board the goal board?
    // temporario!!!
    public boolean isGoal() {
        if (hammin() == 0) return true;
        return false;
    }

    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public boolean isSolvable()            // is this board solvable?

    public static void main(String[] args)
}