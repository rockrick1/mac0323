import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> deq = null;
        deq = new RandomizedQueue<String>();
        // Le o arquivo de entrada e constroi a fila/pilha
        String bepis = "a";
        while (!StdIn.isEmpty()) {
            bepis = StdIn.readString();
            deq.enqueue(bepis);
        }
        Iterator<String> it = deq.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println("\ni:" + i);
            String valor = it.next();
            StdOut.println(deq.dequeue());
            Iterator<String> it2 = deq.iterator();
            while (it2.hasNext()) {
                String valor2 = it2.next();
                StdOut.print(valor2 + ":");
            }
        }
    }
}