import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MaxPQ;
import java.util.Iterator;

public class PointST<Value> {
    private RedBlackBST<Point2D, Value> BST;

    // static class DistComparator implements Comparator<Point2D> {
    //     public int compare(Point2D p1, Point2D p2) {
    //         return n1.priority() - n2.priority();
    //     }
    // }
    // classe de node a ser usada no max-heap
    public class PQNode implements Comparable<PQNode> {
        Point2D p;
        double dist;

        public PQNode(Point2D p, double dist) {
            this.p = p;
            this.dist = dist;
        }
        public int compareTo(PQNode cmp) {
            if (dist() < cmp.dist()) return -1;
            else if (dist() > cmp.dist()) return 1;
            return 0;
        }
        public double dist() {
            return dist;
        }
        public Point2D p() {
            return p;
        }
    }


    // construct an empty symbol table of points
    public PointST() {
        BST = new RedBlackBST<Point2D, Value>();
    }
    // is the symbol table empty?
    public boolean isEmpty() {
        return BST.isEmpty();
    }
    // number of points
    public int size() {
        return BST.size();
    }
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new java.lang.IllegalArgumentException();
        BST.put(p, val);
    }
    // value associated with point p
    public Value get(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        return BST.get(p);
    }
    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        return BST.contains(p);
    }
    // all points in the symbol table
    public Iterable<Point2D> points() {
        return BST.keys();
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException();

        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D p : points())
            if (rect.contains(p))
                q.enqueue(p);
        return q;
    }

    ///////////////////////////////////////////////////////////////////
    private double dist(Point2D x, Point2D y) {
        return x.distanceSquaredTo(y);
    }
    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        if (isEmpty()) return null;

        double dist, min_dist = -1;
        Point2D closest = null;

        for (Point2D cmp : points()) {
            // não pode ser o mesmo ponto, bobinho
            if (!cmp.equals(p)) {
                dist = dist(p, cmp);
                if (dist < min_dist || min_dist == -1) {
                    min_dist = dist;
                    closest = cmp;
                }
            }
        }
        return closest;
    }

    // This method should return the k points that are closest to the
    // query point (in any order);
    // return all N points in the data structure if N <= k.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        // usaremos um hax heap para irmos tirando o maior dos k+1
        // pontos quando inserirmos um novo
        int aux_k = 0;
        MaxPQ<PQNode> pq = new MaxPQ<PQNode>();
        for (Point2D u : points()) {
            double dist = dist(u, p);
            PQNode node = new PQNode(u, dist);
            pq.insert(node);

            aux_k++;
            if (aux_k > k)
                pq.delMax();
        }

        Queue<Point2D> q = new Queue<Point2D>();
        for (PQNode v : pq)
            q.enqueue(v.p());
        return q;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> ST = new PointST<Integer>();
    }
}