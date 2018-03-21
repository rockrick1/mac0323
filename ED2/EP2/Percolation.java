import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] grid; // true se aberto, false se cheio

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size) {
        n = size;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= n || col >= n)
            throw new java.lang.IllegalArgumentException();
        grid[row][col] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= n || col >= n)
            throw new java.lang.IllegalArgumentException();
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= n || col >= n)
            throw new java.lang.IllegalArgumentException();
        return !grid[row][col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int opens = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j])
                    opens++;
        return opens;
    }

    // converte as coordenadas da matriz para o indice na WQUF
    public int convertCoord(int row, int col) {
        return col + (row*n);
    }

    // does the system percolate?
    public boolean percolates() {
        WeightedQuickUnionUF wquf = new WeightedQuickUnionUF(n*n);

        // percorre o grid e faz as uniões das casas de mesmo valor com suas 4
        // vizinhas
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i + 1 < n)
                    if (grid[i][j] == grid[i+1][j])
                        wquf.union(convertCoord(i,j), convertCoord(i+1,j));
                if (j + 1 < n)
                    if (grid[i][j] == grid[i][j+1])
                        wquf.union(convertCoord(i,j), convertCoord(i,j+1));
                if (i - 1 > 0)
                    if (grid[i][j] == grid[i-1][j])
                        wquf.union(convertCoord(i,j), convertCoord(i-1,j));
                if (j - 1 > 0)
                    if (grid[i][j] == grid[i][j-1])
                        wquf.union(convertCoord(i,j), convertCoord(i,j-1));
            }
        }

        // compara a borde de cima com a de baixa 1 a 1 checando se estão no
        // mesmo componente da WQUF
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (grid[0][i] == true && grid[0][i] == grid[n-1][j])
                    // n*n - n + j condiz com os indices da ultima fileira
                    // ([n-1][j]) do grid na WQUF
                    if (wquf.connected(i, (n*n) - n + j))
                        return true;
        return false;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        StdOut.println(perc.isOpen(10,10));
    }
}