package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.*;
import es.udc.sistemasinteligentes.Nodo;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Estrategia4 implements EstrategiaBusqueda {

    public Estrategia4() {
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception{
        ArrayList<Estado> explorados = new ArrayList<>();
        Estado estadoActual = p.getEstadoInicial();
        Nodo nodoFluctuante = new Nodo(estadoActual,null,null);
        explorados.add(estadoActual);

        int i = 1;

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        while (!p.esMeta(estadoActual)){
            System.out.println((i++) + " - " + estadoActual + " no es meta");
            Accion[] accionesDisponibles = p.acciones(estadoActual);
            boolean modificado = false;
            for (Accion acc: accionesDisponibles) {
                Estado sc = p.result(estadoActual, acc);
                System.out.println((i++) + " - RESULT(" + estadoActual + ","+ acc + ")=" + sc);
                if (!explorados.contains(sc)) {
                    estadoActual = sc;
                    System.out.println((i++) + " - " + sc + " NO explorado");
                    explorados.add(estadoActual);
                    modificado = true;
                    System.out.println((i++) + " - Estado actual cambiado a " + estadoActual);
                    nodoFluctuante = crearNodo(sc,nodoFluctuante,acc);
                    break;
                }
                else
                    System.out.println((i++) + " - " + sc + " ya explorado");
            }
            if (!modificado) throw new Exception("No se ha podido encontrar una solución");
        }
        System.out.println((i++) + " - FIN - " + estadoActual);
        return reconstruyeSol(nodoFluctuante);
    }

    public Nodo[]  reconstruyeSol(Nodo nodo){
        ArrayList<Nodo> solNodos = new ArrayList<>();
        while(nodo!=null){
            //System.out.println(nodo.estado+", "+nodo.accion);
            solNodos.add(nodo);
            nodo = nodo.padre;
        }
        Collections.reverse(solNodos);
        return solNodos.toArray(new Nodo[solNodos.size()]);
    }

    public Nodo crearNodo(Estado e, Nodo p, Accion a){
        Nodo n = new Nodo(e,p,a);
        return n;
    }
}
