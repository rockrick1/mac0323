import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] grid; // true se aberto, false se cheio

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.grid[i][j] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row][col] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return !grid[row][col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int opens = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[row][col])
                    opens++;
        return opens;
    }

    // does the system percolate?
    public boolean percolates()

    // unit testing (required)
    public static void main(String[] args) {

    }
}