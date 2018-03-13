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

    public Deque() { // inicia uma Deque
        first = null;
        last = null;
        current = null;
        n = 0;
    }

    public boolean isEmpty() { // checa se está vazia
        return n == 0;
    }

    public int size() { // tamanho da Deque
        return n;
    }

    public void addFirst(Item item) { // adiciona item no inicio
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

    public void addLast(Item item) { // adiciona item no final
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

    public Item removeFirst() { // remove um item no inicio
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

    public Item removeLast() { // remove um item no final
        if (n <= 0) { // excessao para fila vazia
            throw new java.util.NoSuchElementException();
        }
        Item ret = last.item;
        if (n > 1) {
            last = last.prev;
            last.next = null;
        }
        else // n == 1
            last = null;
        n--;
        return ret;
    }

    public Iterator<Item> iterator() { // retorna um iterador independente
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        // iterador que percorre a Deque de inicio a fim
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
             // excessão se iterador tentar ir além do final da fila
            if (!hasNext()) {
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
        // preenche com valores de 10 a 14
        for (int i=10; i < 15; i++) {
            deq.addFirst(i);
        }
        // printa valores
        Iterator<Integer> it = deq.iterator();
        while (it.hasNext()) {
            Integer valor = it.next();
            StdOut.println("Item " + valor);
        }

        for (int i = 0; i < 4; i++)
            StdOut.println("removeu " + deq.removeLast());
        // printa again
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
        deqS.addLast("Esse");
        deqS.addLast("monitor");
        deqS.addLast("é");
        deqS.addLast("muito");
        deqS.addLast("gato");
        for (String s: deqS) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
