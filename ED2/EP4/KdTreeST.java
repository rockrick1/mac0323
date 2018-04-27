import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import java.util.Iterator;

public class KdTreeST<Value> {
    private int n;
    private Node root;

    private class Node {
        private Point2D p;
        private Value value;
        private RectHV rect;
        private boolean orient; // true if vertical
        public Node lb; // arvore da esquerda
        public Node rt; // arvore da direita

        public Node(Point2D key, Value val, boolean ori) {
            p = key;
            value = val;
            orient = ori;
            lb = null;
            rt = null;
            rect = new RectHV(-2, -2, 2, 2);
        }
        public double compareTo(Point2D cmp) {
            if (this.orient) // se for vertical
                return cmp.x() - p.x();
            else // se for horizontal
                return cmp.y() - p.y();
        }
        public Point2D p() {
            return p;
        }
        public boolean orient() {
            return orient;
        }
        public void set_orient(boolean o) {
            orient = o;
        }
        public RectHV rect() {
            return rect;
        }
        // funções para alterar apenas um parametro do retangulo
        // a ser usada na inserção na arvore
        public void rect_xmin(double xmin) {
            rect = new RectHV(xmin, rect.ymin(), rect.xmax(), rect.ymax());
        }
        public void rect_ymin(double ymin) {
            rect = new RectHV(rect.xmin(), ymin, rect.xmax(), rect.ymax());
        }
        public void rect_xmax(double xmax) {
            rect = new RectHV(rect.xmin(), rect.ymin(), xmax, rect.ymax());
        }
        public void rect_ymax(double ymax) {
            rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), ymax);
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
        if (p == null || val == null)
            throw new java.lang.IllegalArgumentException();

        // raiz é vertical
        n++;
        Node z = new Node(p, val, true);
        if (root == null) {
            root = z;
            return;
        }

        Node parent = null;
        Node x = root;
        double cmp;
        while (x != null) {
            cmp = x.compareTo(p);
            parent = x;
            if (cmp > 0) {
                // define as bordas do retangulo da node
                // enquanto ela "viaja" pela arvore:
                // se o pai é vertical e foi para direita, muda o xmin
                x = x.rt;
                if (parent.orient)
                    z.rect_xmin(parent.p().x());
                // se for horizontal, muda o ymin
                else
                    z.rect_ymin(parent.p().y());
            }
            else if (cmp < 0) {
                // se o pai é vertical e foi para esquerda, muda o xmax
                x = x.lb;
                if (parent.orient)
                    z.rect_xmax(parent.p().x());
                // se for horizontal, muda o ymax
                else
                    z.rect_ymax(parent.p().y());
            }
            else {
                x.value = val;
                n--;
                return;
            }
        }
        // insere a nova node como filho de parent
        cmp = parent.compareTo(p);
        z.set_orient(!parent.orient());
        if (cmp < 0) parent.lb = z;
        else parent.rt = z;
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
        else if (cmp > 0) {
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
    private void insertNode(Queue<Point2D> q, Node root, int level) {
        if (root == null)
            return;
        if (level == 1)
            q.enqueue(root.p);
        else if (level > 1) {
            insertNode(q, root.lb, level - 1);
            insertNode(q, root.rt, level - 1);
        }
    }
    // all points in the symbol table in level order
    public Iterable<Point2D> points() {
        int h = height(root);
        Queue<Point2D> q = new Queue<Point2D>();
        for (int i = 1; i <= h; i++)
            insertNode(q, root, i);
        return q;
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException();

        Queue<Point2D> q = new Queue<Point2D>();
        if (root == null) return q;

        range(q, root, rect);
        return q;
    }
    private void range(Queue<Point2D> q, Node x, RectHV rect) {
        // double min, max;
        // boolean contains;
        // // pega os limites da linha do ponto
        // if (x.orient()) {
        //     // se for vertical, pega os y's
        //     min = x.rect().ymin();
        //     max = x.rect().ymax();
        // }
        // else {
        //     // se for horizontal, pega os x's
        //     min = x.rect().xmin();
        //     max = x.rect().xmax();
        // }
        if (x != null && x.rect().intersects(rect)) {
            if (rect.contains(x.p()))
                q.enqueue(x.p());
            range(q, x.rt, rect);
            range(q, x.lb, rect);
        }
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
        String filename = args[0];
        In in = new In(filename);
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();

        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
        }

        for (Point2D p : kdtree.points()) {
            StdOut.println(p.toString());
        }
    }
}