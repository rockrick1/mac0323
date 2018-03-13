import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randq = null;
        randq = new RandomizedQueue<String>();

        // Le o arquivo de entrada e constroi a fila/pilha
        String bepis;
        while (!StdIn.isEmpty()) {
            bepis = StdIn.readString();
            randq.enqueue(bepis);
        }

        Iterator<String> it = randq.iterator();

        // printa
        while (it.hasNext()) {
            String valor = it.next();
            StdOut.println(valor);
        }
        StdOut.println();
        // remove k elementos
        for (int i = 0; i < k; i++) {
            StdOut.println(randq.dequeue());

        }
    }
}