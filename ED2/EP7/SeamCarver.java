import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.MinPQ;
import java.awt.Color;

public class SeamCarver {
    private int width, height;
    private Picture pic;

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
    // public int[] findHorizontalSeam()
    //
    // // sequence of indices for vertical seam
    // public int[] findVerticalSeam()

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
        Color cor = pic.get(2,0);
        StdOut.println(cor.getGreen());
    }
}