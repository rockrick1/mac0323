import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class KdTreeST<Value> {
    private int n;
    private Node root;

    private class Node {
        private Point2D p;
        private Value value;
        private RectHV rect;
        private boolean orient; // true if vertical
        private Node lb; // arvore da esquerda
        private Node rt; // arvore da direita

        public Node(Point2D key, Value val, boolean ori) {
            p = key;
            value = val;
            orient = ori;
        }
        public double compareTo(Point2D cmp) {
            if (this.orient) // se for vertical
                return p.x() - cmp.x();
            else // se for horizontal
                return p.y() - cmp.y();
        }
        public void orient(boolean o) {
            orient = o;
        }
    }


    // construct an empty symbol table of points
    public KdTreeST() {
        n = 0;
        root = null;
    }
    // is the symbol table empty?
    public boolean isEmpty() {
        return n == 0;
    }
    // number of points
    public int size() {
        return n;
    }


    // associate the value val with point p
    public void put(Point2D p, Value val) {
        root = put(root, p, val, false);
    }
    private Node put(Node x, Point2D p, Value val, boolean parent_ori) {
        if (p == null || val == null)
            throw new java.lang.IllegalArgumentException();
        if (x == null)
            return new Node(p, val, !parent_ori);

        double cmp = x.compareTo(p);
        if (cmp < 0)
            x.lb = put(x.lb, p, val, x.orient);
        else if (cmp > 0)
            x.rt = put(x.rt, p, val, x.orient);
        else x.value = val;
        return x;
    }


    // value associated with point p
    public Value get(Point2D p) {
        Node x = get(root, p);
        if (x == null) return null;
        return x.value;
    }
    private Node get(Node x, Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        if (x == null) return null;

        double cmp = x.compareTo(p);
        if (cmp < 0) {
            if (x.lb == null) return null;
            else return get(x.lb, p);
        }
        if (cmp > 0) {
            if (x.rt == null) return null;
            else return get(x.rt, p);
        }
        else return x;
    }


    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        return !(get(p) == null);
    }


    // calcula a altura da arvore, recusivamente
    private int height(Node root) {
        if (root == null)
           return 0;
        else {
            int lh = height(root.lb);
            int rh = height(root.rt);

            /* use the larger one */
            if (lh > rh)
                return(lh + 1);
            else return(rh + 1);
        }
    }
    // insere um ponto na fila recursivamente
    private void insertNode(Stack<Point2D> s, Node root, int level) {
        if (root == null)
            return;
        if (level == 1)
            s.push(root.p);
        else if (level > 1) {
            insertNode(s, root.lb, level - 1);
            insertNode(s, root.rt, level - 1);
        }
    }
    // all points in the symbol table in level order
    public Iterable<Point2D> points() {
        int h = height(root);
        Stack<Point2D> s = new Stack<Point2D>();
        for (int i = 1; i <= h; i++)
            insertNode(s, root, i);
        return s;
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException();
        // nao sei se ta certo
        Point2D min = new Point2D(rect.xmin(), rect.ymin());
        Point2D max = new Point2D(rect.xmax(), rect.ymax());

        return null;
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
        return null;
    }

    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<Integer> ST = new KdTreeST<Integer>();
    }
}