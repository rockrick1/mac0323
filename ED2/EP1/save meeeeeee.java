import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;
    private int rand;
    private Node current;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public RandomizedQueue() {
        first = null;
        last = null;
        current = null;
        n = 0;
    }
    public boolean isEmpty() { // is the queue empty?
        return n == 0;
    }
    public int size() { // return the number of items on the queue
        return n;
    }
    public void enqueue(Item item) { // adds item on the start
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (n != 0)
            oldfirst.prev = first;
        else // also initializes last, if this is the only node
            last = first;
        n++;
    }
    public Item dequeue() {
        int rand = StdRandom.uniform(n);
        StdOut.println("rand "+rand);
        Item ret;
        if (rand < (n-1)/2) { // se escolheu um na primeira metade, começa por first
            current = first;
            for (int i = 0; i < rand; i++)
                current = current.next;
            ret = current.item;
            if (current != first)
                current.prev.next = current.next;
            else // define o novo first, caso o antigo seja escolhido
                first = current.next;
            current = null;
        }
        else { // se escolheu um na segunda metade, começa por last
            current = last;
            for (int i = n-1; i > rand; i--)
                current = current.prev;
            ret = current.item;
            if (current == last) // atualiza o last caso tenha escolhido o ultimo
                last = current.prev;
            current.prev.next = current.next;
            current = null;
        }
        StdOut.println("ret:"+n);
        n--;
        return ret;
    }
    public Item sample() {
        int rand = StdRandom.uniform(n);
        // StdOut.println("rand:"+rand);
        Item ret;
        if (rand <= (n-1)/2) { // se escolheu um na primeira metade, começa por first
            current = first;
            for (int i = 0; i < rand; i++)
                current = current.next;
            ret = current.item;
        }
        else { // se escolheu um na segunda metade, começa por last
            current = last;
            for (int i = n-1; i > rand; i--) {
                current = current.prev;
            }
            ret = current.item;
        }
        // StdOut.println("ret:"+ret);
        return ret;
    }
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) { // testing
        RandomizedQueue<Integer> randq = null;
        randq = new RandomizedQueue<Integer>();
        for (int i=19; i >= 0; i--) {
            randq.enqueue(i);
        }
        StdOut.println("SAMPLES:\n");
        Iterator<Integer> it;
        for (int i = 0; i<10; i++) {
            StdOut.print(randq.dequeue()+" removed\n");
            it = randq.iterator();
            while (it.hasNext()) {
                Integer valor = it.next();
                StdOut.print(":" + valor);
            }
            StdOut.println("\n");
        }
    }
}
}