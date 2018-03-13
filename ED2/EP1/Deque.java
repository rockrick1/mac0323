import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;
    private Node current;

    private class Node { // Deque Node
        Item item;
        Node next;
        Node prev;
    }

    public Deque() { // construct an empty deque
        first = null;
        last = null;
        current = null;
        n = 0;
    }

    public boolean isEmpty() { // is the deque empty?
        return n == 0;
    }

    public int size() { // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) { // add the item to the front
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

    public void addLast(Item item) { // add the item to the end
        if (item == null) { // excessao para argumento null
            throw new java.lang.IllegalArgumentException();
        }
        Node newlast = new Node();
        newlast.item = item;
        if (n != 0) {
            newlast.prev = last;
            last.next = newlast;
            last = newlast;
        }
        else { // tambem inicializa first, se for o unico nó
            first = newlast;
            last = newlast;
        }
        n++;
    }

    public Item removeFirst() { // remove and return the item from the front
        if (n <= 0) { // excessao para fila vazia
            throw new java.util.NoSuchElementException();
        }
        Item ret = first.item;
        first = first.next;
        if (n > 1)
            first.prev = null;
        n--;
        return ret;
    }

    public Item removeLast() { // remove and return the item from the end
        if (n <= 0) { // excessao para fila vazia
            throw new java.util.NoSuchElementException();
        }
        Item ret = last.item;
        last = last.prev;
        last.next = null;
        n--;
        return ret;
    }

    public Iterator<Item> iterator() { // return an iterator over items in order from front to end
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
             // excessão se iterador tentar ir além do final da fila
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() { // excessão para chamada de remove no iterador
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) { // testing
        Deque<Integer> deq = null;
        deq = new Deque<Integer>();
        // preenche com valores de 10 a 19
        for (int i=10; i < 20; i++) {
            deq.addFirst(i);
        }
        // printa valores
        Iterator<Integer> it = deq.iterator();
        while (it.hasNext()) {
            Integer valor = it.next();
            StdOut.println("Item " + valor);
        }
        // remove os dois ultimos e o primeiro, e printa denovo
        deq.removeLast();
        deq.removeFirst();
        deq.removeLast();
        StdOut.println("Size: " + deq.size());
        it = deq.iterator();
        while (it.hasNext()) {
            Integer valor = it.next();
            StdOut.println("Item " + valor);
        }
        // Deq de Strings
        Deque<String> deqS = new Deque<String>();
        deqS.addLast("Como");
        deqS.addLast("eh");
        deqS.addLast("bom");
        deqS.addLast("estudar");
        deqS.addLast("MAC0323!");
        StdOut.println(deqS.size());
        for (String s: deqS) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
