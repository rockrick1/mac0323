import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] grid; // true se aberto, false se cheio
    public WeightedQuickUnionUF wquf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size) {
        if (size <= 0) {
            StdOut.println("ooo");
            throw new java.lang.IllegalArgumentException();
        }
        // faremos a wquf com duas posições a mais, que representam as bordas
        // superior e inferior, para checar a percolação
        n = size;
        wquf = new WeightedQuickUnionUF(n*n + 2);
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                grid[i][j] = false;
    }

    // converte as coordenadas da matriz para o indice na WQUF
    public int convertCoord(int row, int col) {
        return col + (row*n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int i, int j) {
        if (i >= n || j >= n)
            throw new java.lang.IllegalArgumentException();

        grid[i][j] = true;

        if (i == 0) // adiciona ao grupo do site virtual superior
            wquf.union(convertCoord(i,j), n*n);
        if (i == n-1) // adiciona no grupo do site virtual inferior
            wquf.union(convertCoord(i,j), n*n + 1);

        if (i + 1 < n)
            if (grid[i+1][j] == true)
                wquf.union(convertCoord(i,j), convertCoord(i+1,j));
        if (j + 1 < n)
            if (grid[i][j+1] == true)
                wquf.union(convertCoord(i,j), convertCoord(i,j+1));
        if (i - 1 >= 0)
            if (grid[i-1][j] == true)
                wquf.union(convertCoord(i,j), convertCoord(i-1,j));
        if (j - 1 >= 0)
            if (grid[i][j-1] == true)
                wquf.union(convertCoord(i,j), convertCoord(i,j-1));

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
        return grid[row][col];
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

    // does the system percolate?
    public boolean percolates() {
        // compara a posição virtual superior com a inferior para ver se
        // percola
        if (wquf.connected(n*n, (n*n) + 1))
            return true;
        return false;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        StdOut.println(perc.isOpen(10,10));
    }
}