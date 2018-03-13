import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

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

    public RandomizedQueue() { // inicializa a randQ
        first = null;
        last = null;
        current = null;
        n = 0;
    }

    public boolean isEmpty() { // verifica se esta vaiza
        return n == 0;
    }

    public int size() { // retorna o numero de itens
        return n;
    }

    public void enqueue(Item item) { // adiciona um item no inicio da randQ
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


    public Item dequeue() { // remove um item aleatorio da randQ
        if (n <= 0) { // excessao para fila vazia
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(n);
        Item ret;

        // vai ate o item a ser removido
        current = first;
        for (int i = 0; i < rand; i++){
            current = current.next;
        }
        ret = current.item;

        if (n > 1) {
            if (current == last) { // define o novo last, caso o antigo seja escolhido
                this.last = current.prev;
                this.last.next = null;
            }
            else if (current == first) { // define o novo first, caso o antigo seja escolhido
                this.first = current.next;
                this.first.prev = null;
            }
            // atualiza o next e prev dos elementos que cercam o que foi removido
            else {
                current.prev.next = current.next;
                current.next.prev = current.prev;
            }
        }
        n--;
        return ret;
    }


    public Item sample() { // retorna um item aleatorio, sem remove-lo da randQ
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


    public Iterator<Item> iterator() { // instancia um iterador independente
        return new RandomizedIterator();
    }


    private class RandomizedIterator implements Iterator<Item> {
        // percorre a randQ de inicio a fim
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


    public static void main(String[] args) { // testing b o y s
        RandomizedQueue<Integer> randq = null;
        randq = new RandomizedQueue<Integer>();
        // preenche com numeros
        for (int i=9; i >= 0; i--) {
            randq.enqueue(i);
        }

        // vai tirando numeros aleatorios e imprimindo
        Iterator<Integer> it;
        for (int i = 0; i<9; i++) {
            StdOut.print(randq.dequeue()+" removed");
            it = randq.iterator();
            while (it.hasNext()) {
                Integer valor = it.next();
                StdOut.print(":" + valor);
            }
            StdOut.println("\n");
        }
    }
}