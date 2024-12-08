package utils;

import tdas.PriorityQueue;
import tdas.StaticPriorityQueue;

public class StaticPriorityQueueUtil {

    //EJERCICIO 2 B ========================================================


    public static void procesarCola(StaticPriorityQueue cola) {
        if (cola.isEmpty()) {
            System.out.println("La cola está vacía.");
            return;
        }

        int totalElementos = 0;


        StaticPriorityQueue copiaCola = copiarCola(cola);
        while (!copiaCola.isEmpty()) {
            copiaCola.remove();
            totalElementos++;
        }

        copiaCola = copiarCola(cola);
        while (!copiaCola.isEmpty()) {
            int valor = copiaCola.getFirst();
            int prioridad = copiaCola.getPriority();


            double resultado = (double) prioridad / totalElementos;


            System.out.println("Elemento: " + valor + ", Prioridad: " + prioridad + ", p/n → " + resultado);

            copiaCola.remove();
        }
    }

    private static StaticPriorityQueue copiarCola(StaticPriorityQueue original) {
        StaticPriorityQueue copia = new StaticPriorityQueue();

        PriorityQueue temp = new StaticPriorityQueue();
        while (!original.isEmpty()) {
            int valor = original.getFirst();
            int prioridad = original.getPriority();

            copia.add(valor, prioridad);
            temp.add(valor, prioridad);

            original.remove();
        }

        while (!temp.isEmpty()) {
            int valor = temp.getFirst();
            int prioridad = temp.getPriority();

            original.add(valor, prioridad);
            temp.remove();
        }

        return copia;
    }

    //EJERCICIO 2 B ========================================================

}
