import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class PointST<Value> {
    private RedBlackBST<Point2D, Value> BST;
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
        // nao sei se ta certo
        Point2D min = new Point2D(rect.xmin(), rect.ymin());
        Point2D max = new Point2D(rect.xmax(), rect.ymax());

        return BST.keys(min, max);
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
        Point2D cmp, closest = null;

        Iterator<Point2D> it = points().iterator();
        while (it.hasNext()) {
            cmp = it.next();
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
        return null;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> ST = new PointST<Integer>();
    }
}