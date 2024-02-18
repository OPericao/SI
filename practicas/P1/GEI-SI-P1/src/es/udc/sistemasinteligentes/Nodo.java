package es.udc.sistemasinteligentes;

import es.udc.sistemasinteligentes.Accion;
import es.udc.sistemasinteligentes.Estado;

public class Nodo{
    public Estado estado;
    public Nodo padre;
    public Accion accion;
    public Nodo(Estado e, Nodo p, Accion a){
        this.estado=e;
        this.padre=p;
        this.accion=a;
    }
}
