package org.example.Servicio;

import org.example.Modelo.Contacto;
import org.example.Modelo.NodoContacto;

/**
 * La clase Agenda representa una agenda de contactos usando un árbol binario de búsqueda.
 */
public class Agenda {
    private NodoContacto raiz;

    /**
     * Constructor de la clase Agenda.
     * Inicializa la raíz del árbol en null.
     */
    public Agenda() {
        this.raiz = null;
    }

    /**
     * Agrega un contacto a la agenda.
     * @param nombre El nombre del contacto.
     * @param telefono El teléfono del contacto.
     */
    public void agregarContacto(String nombre, String telefono) {
        Contacto nuevoContacto = new Contacto(nombre, telefono);
        if (this.raiz == null) {
            this.raiz = new NodoContacto(nuevoContacto);
        } else {
            this.insertar(this.raiz, nuevoContacto);
        }
    }

    /**
     * Método auxiliar para insertar un contacto en el árbol.
     * @param padre El nodo padre en el árbol.
     * @param contacto El contacto a insertar.
     */
    private void insertar(NodoContacto padre, Contacto contacto) {
        if (contacto.getNombre().compareTo(padre.getContacto().getNombre()) < 0) {
            if (padre.getIzdo() == null) {
                padre.setIzdo(new NodoContacto(contacto));
            } else {
                insertar(padre.getIzdo(), contacto);
            }
        } else if (contacto.getNombre().compareTo(padre.getContacto().getNombre()) > 0) {
            if (padre.getDcho() == null) {
                padre.setDcho(new NodoContacto(contacto));
            } else {
                insertar(padre.getDcho(), contacto);
            }
        }
    }

    /**
     * Busca un contacto en la agenda por su nombre.
     * @param nombre El nombre del contacto a buscar.
     * @return El contacto si se encuentra, de lo contrario null.
     */
    public Contacto buscarContacto(String nombre) {
        return buscar(this.raiz, nombre);
    }

    /**
     * Método auxiliar para buscar un contacto en el árbol.
     * @param nodo El nodo actual en el árbol.
     * @param nombre El nombre del contacto a buscar.
     * @return El contacto si se encuentra, de lo contrario null.
     */
    private Contacto buscar(NodoContacto nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        if (nombre.equals(nodo.getContacto().getNombre())) {
            return nodo.getContacto();
        } else if (nombre.compareTo(nodo.getContacto().getNombre()) < 0) {
            return buscar(nodo.getIzdo(), nombre);
        } else {
            return buscar(nodo.getDcho(), nombre);
        }
    }

    /**
     * Elimina un contacto de la agenda por su nombre.
     * @param nombre El nombre del contacto a eliminar.
     */
    public void eliminarContacto(String nombre) {
        this.raiz = eliminar(this.raiz, nombre);
    }

    /**
     * Método auxiliar para eliminar un contacto del árbol.
     * @param nodo El nodo actual en el árbol.
     * @param nombre El nombre del contacto a eliminar.
     * @return El nodo modificado después de la eliminación.
     */
    private NodoContacto eliminar(NodoContacto nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        if (nombre.compareTo(nodo.getContacto().getNombre()) < 0) {
            nodo.setIzdo(eliminar(nodo.getIzdo(), nombre));
        } else if (nombre.compareTo(nodo.getContacto().getNombre()) > 0) {
            nodo.setDcho(eliminar(nodo.getDcho(), nombre));
        } else {
            if (nodo.getIzdo() == null) {
                return nodo.getDcho();
            } else if (nodo.getDcho() == null) {
                return nodo.getIzdo();
            }

            NodoContacto temp = minValorNodo(nodo.getDcho());
            nodo.getContacto().setTelefono(temp.getContacto().getTelefono());
            nodo.getContacto().setNombre(temp.getContacto().getNombre());
            nodo.setDcho(eliminar(nodo.getDcho(), temp.getContacto().getNombre()));
        }
        return nodo;
    }

    /**
     * Encuentra el nodo con el valor mínimo en el subárbol dado.
     * @param nodo El nodo raíz del subárbol.
     * @return El nodo con el valor mínimo.
     */
    private NodoContacto minValorNodo(NodoContacto nodo) {
        NodoContacto actual = nodo;
        while (actual.getIzdo() != null) {
            actual = actual.getIzdo();
        }
        return actual;
    }

    /**
     * Muestra todos los contactos en la agenda en orden alfabético.
     */
    public void mostrarContactos() {
        inOrden(this.raiz);
    }

    /**
     * Método auxiliar para recorrer el árbol en orden.
     * @param nodo El nodo actual en el árbol.
     */
    private void inOrden(NodoContacto nodo) {
        if (nodo != null) {
            inOrden(nodo.getIzdo());
            System.out.println("Nombre: " + nodo.getContacto().getNombre() + ", Teléfono: " + nodo.getContacto().getTelefono());
            inOrden(nodo.getDcho());
        }
    }
}
