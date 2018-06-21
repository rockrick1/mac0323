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
    private int wh;
    private Picture pic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.pic = picture;
        this.width = pic.width();
        this.height = pic.height();
        this.wh = width * height;
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
        if (x < 0 || y < 0 || x >= width || y >= height)
            throw new java.lang.IllegalArgumentException();
        return Math.sqrt(gradX(x,y) + gradY(x,y));
    }

    // funções auxiliares para pegar o indice da matriz na forma de vetor
    // e de volta também
    private int getIdx(int x, int y) {
        return ((y*width) + x);
    }
    private int getX(int idx) {
        return idx % width;
    }
    private int getY(int idx) {
        return idx / width;
    }

    // posições da matriz 'adjacentes' à posição representada por idx
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

    // transpoe a matriz e altera os valores devidamente
    private Picture transpose(Picture picture) {
        Picture ret = new Picture(height, width);

        for (int col = 0; col < height; col++) {
            for (int row = 0; row < width; row++) {
                int rgb = picture.getRGB(row,col);
                ret.setRGB(col, row, rgb);
            }
        }
        // inverte w e h
        int temp = height;
        height = width;
        width = temp;
        return ret;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // apenas transpoe a imagem e faz o vertical
        pic = transpose(pic);

        int[] seam = findVerticalSeam();

        // transpoe de volta depois, claro
        pic = transpose(pic);
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        // usaremos vetores ao inves de matrizes, pois assim com os indices
        // temos a informação tanto do x quanto do y da posição, com as
        // funções auxiliares acima (getIdx, getX e getY)
        int[] edgeTo = new int[wh];
        double[] distTo = new double[wh];
        double[]  energies = new double[wh];

        // Inicializa uma matriz com as energias de cada pixel.
        // First será o indice x do qual começaremos, ou seja, a posição
        // da borda superior com menor energia.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                energies[getIdx(x,y)] = energy(x,y);
                distTo[getIdx(x,y)] = -1;
            }
        }

        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(wh);

        // insere a primeira linha inteira; o pai da primeira linha é
        // um unico nó implicito, representado por -1
        // Queremos achar o melhor caminho desse nó (-1) até a ultima linha
        for (int x = 0; x < width; x++) {
            pq.insert(x, energies[x]);
            edgeTo[x] = -1;
            distTo[x] = energies[x];
        }

        // algoritmo de Dijkstra
        while (!pq.isEmpty()) {
            int fromIdx = pq.delMin();
            for (int toIdx : adj(fromIdx)) {
                double d = distTo[fromIdx] + energies[toIdx];
                if (distTo[toIdx] > d || distTo[toIdx] == -1) {
                    edgeTo[toIdx] = fromIdx;
                    distTo[toIdx] = d;

                    if (pq.contains(toIdx))
                        pq.decreaseKey(toIdx, d);
                    else
                        pq.insert(toIdx, d);
                }
            }
        }

        int best = wh - 1;
        double min = -1;

        // nessa fila teremos o caminho final
        Queue<Integer> path = new Queue<Integer>();
        // percorre todos os pixels da ultima linha, procurando um caminho até
        // os com distTo = -1
        for (int i = wh - 1; i > wh - width - 1; i--) {
            // pra cada iteração, grava o caminho e a soma das energias
            Stack<Integer> stack = new Stack<Integer>();
            double sum = 0;
            for (int pos = i; pos != -1; pos = edgeTo[pos]) {
                stack.push(pos);
                sum += energies[pos];
            }

            // compara com o melhor até agora pra ver se substitui
            if (sum < min || min == -1) {
                // substitui, entao cria um novo path e insere tudo
                path = new Queue<Integer>();
                for (int p : stack)
                    path.enqueue(p);
                min = sum;
                best = i;
            }
        }
        // monta o vetor seam com os valores convertidos de path
        int size = path.size();
        for (int i = 0; i < size; i++)
            seam[i] = getX(path.dequeue());

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || height == 1 || seam.length != width)
            throw new java.lang.IllegalArgumentException();

        // transpoe a imagem e faz o vertical
        pic = transpose(pic);

        removeVerticalSeam(seam);

        pic = transpose(pic);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || width == 1 || seam.length != height)
            throw new java.lang.IllegalArgumentException();

        // cria uma nova imagem, com width - 1 de largura
        // e insere todos os pixels menos os inclusos em seam[]
        Picture removed = new Picture(width - 1, height);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width - 1; col++) {
                if (col >= seam[row]) {
                    int rgb = pic.getRGB(col+1, row);
                    removed.setRGB(col, row, rgb);
                }
                else {
                    int rgb = pic.getRGB(col, row);
                    removed.setRGB(col, row, rgb);
                }
            }
        }
        width--;
        this.pic = removed;
    }


    //  unit testing (required)
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver SC = new SeamCarver(pic);
        SC.findHorizontalSeam();
        StdOut.println("im a blind cave salamander");
    }
}