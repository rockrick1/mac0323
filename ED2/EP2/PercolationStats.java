import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    // vetor com o numero de casas abertas no final de cada trial
    private int[] thresholds;
    private int t; // a ser usados nos intervalos de confiança

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        Percolation perc;
        int i, j, opens;
        thresholds = new int[trials];
        t = trials;

        // Executa T testes
        for (int k = 0; k < trials; k++) {
            perc = new Percolation(n);
            opens = 0;
            while (!perc.percolates()) {
                // Enquanto não sortear uma posição fechada, sorteia denovo
                while (perc.isOpen(i = StdRandom.uniform(n), j= StdRandom.uniform(n)))
                    continue;
                perc.open(i, j);
                opens++;
            }
            thresholds[k] = opens;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96*stddev() / sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96*stddev() / sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch time = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, t);

        StdOut.println("mean:\t\t" + percStats.mean());
        StdOut.println("stddev:\t\t" + percStats.stddev());
        StdOut.println("low:\t\t" + percStats.confidenceLow());
        StdOut.println("high:\t\t" + percStats.confidenceHigh());
        StdOut.println("elapsed:\t" + time.elapsedTime());
    }
}