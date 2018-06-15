import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.IndexMinPQ;
import java.util.Comparator;
import java.awt.Color;

public class SeamCarver {
    private int Infinite = 1250; // esse é o maior valor que a energia pode assumir
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

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] seam = new int[width];
        double[][] distTo = new double[width][height];
        double[][] energies = new double[width][height];
        // Inicializa uma matriz com as energias de cada pixel.
        // First será o indice x do qual começaremos, ou seja, a posição
        // da borda superior com menor energia.
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                energies[x][y] = energy(x,y);
                distTo[x][y] = Infinite;
            }
        int first = 0;
        for (int y = 0; y < height; y++)
            if (energies[0][y] < energies[0][first])
                first = y;


        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(width);

        distTo[0][first] = 0;
        pq.insert(first, distTo[0][first]);

        for (int x = 1; !pq.isEmpty();) {
            int y = pq.delMin();
            StdOut.println("from:"+y);
            for (int k = Math.max(y-1,0); k <= Math.min(height, y+1); k++) {
                int from = y; int to = k;
                StdOut.println("to:"+to);
                double d = distTo[x-1][from] + energies[x][to];
                StdOut.println("d:"+d);
                StdOut.println("dist from:"+distTo[x-1][from]);
                if (distTo[x][to] > d) {
                    StdOut.println("entrei no if!");
                    seam[x - 1] = to;
                    StdOut.printf("seam %d = %d\n", x-1,to);
                    distTo[x][to] = d;
                    if (pq.contains(to)) {
                        x--;
                        pq.decreaseKey(to, d);
                    }

                    else {
                        x++;
                        pq.insert(to, d);
                    }
                }
            //    StdOut.println("proximo x\n");
            }
        }
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
        for (int i : SC.findHorizontalSeam())
            StdOut.print(i+"-");
    }
}