import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Bag;
import java.util.Comparator;
import java.awt.Color;

public class SeamCarver {
    private int width, height;
    private Picture pic;

    // public class Pixel extends Comparable<Pixel> {
    //     private int x, y;
    //     public double energy;
    //
    //     public Pixel (int x, int y, double energy) {
    //         this.x = x; this.y = y;
    //         this.energy = energy;
    //     }
    //
    //     public double compare(Pixel this, Pixel other) {
    //         return other.energy - this.energy;
    //     }
    // }

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.pic = picture;
        this.width = pic.width();
        this.height = pic.height();
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // funções dos gradientes a serem usadas no calculo da energia
    public double gradX(int x, int y) {
        double R, G, B;
        Color corLeft, corRight;

        if (x == 0) corLeft = pic.get(width - 1, y);
        else        corLeft = pic.get(x - 1, y);
        corRight = pic.get((x + 1) % width, y);

        R = corRight.getRed() - corLeft.getRed();
        G = corRight.getGreen() - corLeft.getGreen();
        B = corRight.getBlue() - corLeft.getBlue();

        return (R*R) + (G*G) + (B*B);
    }
    public double gradY(int x, int y) {
        double R, G, B;
        Color corUp, corDown;

        if (y == 0) corUp = pic.get(x, height - 1);
        else        corUp = pic.get(x, y - 1);
        corDown = pic.get(x, (y + 1) % height);

        R = corDown.getRed() - corUp.getRed();
        G = corDown.getGreen() - corUp.getGreen();
        B = corDown.getBlue() - corUp.getBlue();

        return (R*R) + (G*G) + (B*B);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        return Math.sqrt(gradX(x,y) + gradY(x,y));
    }

    private int getIdx(int x, int y) {
        return ((y*width) + x);
    }

    private int getX(int idx) {
        return idx % width;
    }

    private int getY(int idx) {
        return idx / width;
    }

    private Iterable<Integer> adj(int idx) {
        Bag<Integer> bag = new Bag<Integer>();
        int x = getX(idx);
        int y = getY(idx);
        if (y + 1 < height) {
            if (x - 1 >= 0)
                bag.add(getIdx(x-1,y+1));
            bag.add(getIdx(x,y+1));
            if (x + 1 < width)
                bag.add(getIdx(x+1,y+1));
        }
        return bag;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[height];
        int[] edgeTo = new int[height*width];
        double[] distTo = new double[width*height];
        double[]  energies = new double[width*height];

        // Inicializa uma matriz com as energias de cada pixel.
        // First será o indice x do qual começaremos, ou seja, a posição
        // da borda superior com menor energia.
        StdOut.println(width +" "+ height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                energies[getIdx(x,y)] = energy(x,y);
                distTo[getIdx(x,y)] = -1;
            }
        }
        // for (int i = 0; i < width*height; i++) {
        //     StdOut.printf("%.0f\t",energies[i]);
        //     if (i%(width - 1) == 0 && i != 0)
        //         StdOut.print("\n");
        //
        // }


        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(width*height);

        int first = 0;
        StdOut.println(width*height);
        // insere a primeira linha inteira
        for (int x = 0; x < width; x++) {
            pq.insert(x, energies[getIdx(x,0)]);
            edgeTo[getIdx(x,0)] = -1;
        }

        while (!pq.isEmpty()) {
            int fromIdx = pq.delMin();
            // StdOut.println("tirei um");
            int fromX = getX(fromIdx);
            int fromY = getY(fromIdx);
            for (int toIdx : adj(fromIdx)) {
                int toX = getX(toIdx);
                int toY = getY(toIdx);
                // StdOut.printf("to x: %d\n",toX);
                double d = distTo[fromIdx] + energies[toIdx];
                if (distTo[toIdx] > d || distTo[toIdx] == -1) {
                    // StdOut.printf("from: %d to: %d\n",fromIdx, toIdx);
                    if (fromY == 0) first = fromX;
                    edgeTo[toIdx] = fromIdx;
                    distTo[toIdx] = d;

                    if (pq.contains(toIdx)) {
                        pq.decreaseKey(toIdx, d);
                    }

                    else {
                        pq.insert(toIdx, d);
                    }
                }
            }
        }


        // for (int x = 0; x < width; x++)
        //     distTo[x][0] = energies[x][0];
        // for (int y = 0; y < height - 1; y++) {
        //     for (int x = 0; x < width; x++) {
        //         for (int k = x-1; k <= x+1; k++) {
        //             int xTo = getX(k);
        //             double from = distTo[x][y];
        //             double to = energies[xTo][y + 1];
        //             double d = from + to;
        //             StdOut.printf("y:%d, d: %.1f\n",y,d);
        //             if (distTo[xTo][y + 1] > d || distTo[xTo][y + 1] == -1)
        //                 distTo[xTo][y + 1] = d;
        //         }
        //         // for (int j = 0; j < height; j++) {
        //         //     for (int i = 0; i < width; i++)
        //         //     StdOut.printf("%.0f\t",distTo[i][j]);
        //         //     StdOut.println();
        //         // }
        //         // StdOut.println();
        //     }
        // }
        // for (int y = 0; y < height; y++) {
        //     for (int x = 0; x < width; x++)
        //     StdOut.printf("%.0f\t",energies[x][y]);
        //     StdOut.println();
        // }
        // StdOut.println();
        // for (int y = 0; y < height; y++) {
        //     for (int x = 0; x < width; x++)
        //         StdOut.printf("%.0f\t",distTo[x][y]);
        //     StdOut.println();
        // }
        // int first = 0;
        // for (int x = 0; x < width; x++)
        //     if (distTo[x][0] < distTo[first][0])
        //         first = x;
        // StdOut.println(first);
        // seam[0] = first;
        // int x = first
        // for (y = 1; y < height; y++) {
        //     for (int k = x-1; k <= x+1; k++) {
        //         int xTo = getX(k);
        //
        //
        // }

        StdOut.print("edgeTo:\n");
        for (int i : edgeTo)
            StdOut.print(i+" ");
        StdOut.println();
        // StdOut.println(first);
        int best = width*height - 1;
        double min = -1;

        Queue<Integer> path = new Queue<Integer>();
        for (int i = width*height - 1; i > width*height - width; i--) {
            StdOut.printf("%.1f\n", energies[i]);
            int x = getX(i), y = getY(i);

            Stack<Integer> stack = new Stack<Integer>();
            double sum = 0;
            StdOut.println("\n"+getX(i));
            for (int pos = i; pos != -1; pos = edgeTo[pos]) {
                stack.push(pos);
                sum += energies[pos];
                StdOut.print(getX(pos) + " ");
            }
            StdOut.println();

            if (sum < min || min == -1) {
                StdOut.println("vo fazer denovo");
                path = new Queue<Integer>();
                for (int p : stack)
                    path.enqueue(p);
                min = sum;
                best = i;
                StdOut.println("melhor foi "+getX(best));
            }
        }
        for (int i : path)
            StdOut.print(getX(i)+"-");
        return seam;
    }

    // sequence of indices for vertical seam
    // public int[] findVerticalSeam() {
    //     int[] seam = new int[height];
    // }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height == 1 || seam.length != width)
            throw new java.lang.IllegalArgumentException();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || width == 1 || seam.length != height)
            throw new java.lang.IllegalArgumentException();
    }


    //  unit testing (required)
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver SC = new SeamCarver(pic);
        SC.findHorizontalSeam();
        StdOut.println();
    }
}