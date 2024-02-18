package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.EstrategiaBusqueda;
import es.udc.sistemasinteligentes.Nodo;
import es.udc.sistemasinteligentes.ProblemaBusqueda;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        ProblemaAspiradora.EstadoAspiradora estadoInicial = new ProblemaAspiradora.EstadoAspiradora(ProblemaAspiradora.EstadoAspiradora.PosicionRobot.IZQ,
                                                                                                    ProblemaAspiradora.EstadoAspiradora.PosicionBasura.AMBAS);
        ProblemaBusqueda aspiradora = new ProblemaAspiradora(estadoInicial);

        EstrategiaBusqueda buscador = new Estrategia4();

        Nodo[] sol = buscador.soluciona(aspiradora);
        ArrayList<Nodo> lista = new ArrayList<>(Arrays.asList(sol));

        for(Nodo n : lista){
            System.out.println(n.toString());
        }

    }
}
