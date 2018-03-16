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
        if (item == null) { // excessao para argumento null
            throw new java.lang.IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (n != 0)
            oldfirst.prev = first;
        else // tambem inicializa last, se for o unico nó
            last = first;
        n++;
    }


    public Item dequeue() {
        if (n <= 0) { // excessao para fila vazia
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(n);
        Item ret;

        current = first;
        for (int i = 0; i < rand; i++)
            current = current.next;
        ret = current.item;
        if (n > 1) {
            if (current == last) { // define o novo last, caso o antigo seja escolhido
                last = current.prev;
                last.next = null;
            }
            else if (current == first) { // define o novo first, caso o antigo seja escolhido
                first = current.next;
                first.prev = null;
            }
            // atualiza o next e prev dos elementos que cercam o que foi removido
            else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
        }
        current = null;
        n--;
        return ret;
    }


    public Item sample() {
        if (n <= 0) { // excessao para fila vazia
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(n);
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
            // excessão se iterador tentar ir além do final da fila
            if (!this.hasNext()) {
                StdOut.println(current == first);
                throw new java.util.NoSuchElementException();
            }
            StdOut.println("hewoooo??");
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() { // excessão para chamada de remove no iterador
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) { // testing b o y s
        RandomizedQueue<Integer> randq = null;
        randq = new RandomizedQueue<Integer>();
        for (int i=19; i >= 0; i--) {
            randq.enqueue(i);
        }
        StdOut.println("SAMPLES:\n");
        Iterator<Integer> it;
        for (int i = 0; i<19; i++) {
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