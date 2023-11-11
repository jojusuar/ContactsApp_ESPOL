
package tdas;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author euclasio
 * @param <E>
 */
public class ArrayList<E> implements List<E>, Serializable {
    private int effectiveSize;
    private E[] elements;
    private int capacity = 10;
    
    public ArrayList(){
        effectiveSize = 0;
        elements = (E[]) new Object[capacity];
    }

        @Override
    public boolean addFirst(E element) {
        if (element == null) {
            return false;
        } else if (isEmpty()) {
            elements[effectiveSize++] = element;
            return true;
        } else if (capacity == effectiveSize) {
            addCapacity();
        }

        for (int i = effectiveSize - 1; i >= 0; i--) {
            elements[i + 1] = elements[i];
        }

        elements[0] = element;
        effectiveSize++;
        return true;
    }

    @Override
    public boolean insert(int index, E element) {
        if (element == null || index >= effectiveSize) {
            return false;
        } else if (effectiveSize == capacity) {
            addCapacity();
        }

        for (int i = effectiveSize; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        effectiveSize++;
        return true;
    }

    @Override
    public boolean addLast(E element) {
        if (element == null) {
            return false;
        } else if (effectiveSize == capacity) {
            addCapacity();
        }

        elements[effectiveSize++] = element;
        return true;
    }

    private void addCapacity() {
        E[] tmp = (E[]) new Object[capacity * 2];
        for (int i = 0; i < capacity; i++) {
            tmp[i] = elements[i];
        }
        elements = tmp;
        capacity = capacity * 2;
    }

    @Override
    public E getFirst() {
        return elements[0];
    }

    @Override
    public E getLast() {
        return elements[effectiveSize - 1];
    }

    @Override
    public boolean removeFirst() {
        if (isEmpty()) {
            return false;
        }
        effectiveSize--;
        for (int i = 0; i < effectiveSize; i++) {
            elements[i] = elements[i + 1];
        }

        elements[effectiveSize] = null;
        return true;
    }

    @Override
    public boolean removeLast() {
        if (isEmpty()) {
            return false;
        }

        elements[--effectiveSize] = null;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public boolean contains(E element) {
        if (element == null || isEmpty()) {
            return false;
        }

        for (int i = 0; i < effectiveSize; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");

        for (int i = 0; i < effectiveSize; i++) {
            if (i != effectiveSize - 1) {
                s.append(elements[i] + ", ");
            } else {
                s.append(elements[i]);
            }
        }

        s.append("]");
        return s.toString();
    }

    public List<E> slicing(int start, int end) {
        List<E> lista = new ArrayList<>();
        if (start >= end || end > effectiveSize) {
            return lista;
        }

        for (int i = start; i < end; i++) {
            lista.addLast(elements[i]);
        }

        return lista;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ArrayList)) {
            return false;
        }

        ArrayList<E> other = (ArrayList<E>) o;

        if (this.effectiveSize != other.effectiveSize) {
            return false;
        }

        for (int i = 0; i < effectiveSize; i++) {
            if (!this.elements[i].equals(other.elements[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public E get(int index) {
        if (effectiveSize == 0 || index < 0 || index >= effectiveSize) {
            return null;
        }
        return elements[index];
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            return -1;
        }

        for (int i = 0; i < effectiveSize; i++) {
            if (elements[i].equals(element)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E remove(int index) {
        if (effectiveSize == 0 || index < 0 || index >= effectiveSize) {
            return null;
        }
        E element = elements[index];
        for (int i = index; i < effectiveSize; i++) {
            elements[i] = elements[i + 1];
        }
        elements[effectiveSize - 1] = null;
        effectiveSize--;
        return element;
    }

    @Override
    public boolean remove(E element) {
        if (element == null) {
            return false;
        }

        for (int i = 0; i < effectiveSize; i++) {
            if (elements[i].equals(element)) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public E set(int index, E element) {
        if (element == null || index < 0 || index >= effectiveSize) {
            return null;
        }

        E i = elements[index];
        elements[index] = element;
        return i;
    }

    @Override
    public int size() {
        return effectiveSize;
    }

    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            int cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor < effectiveSize;
            }

            @Override
            public E next() {
                E element = elements[cursor];
                cursor++;
                return element;
            }
        };
        return it;
    }

    @Override
    public boolean addAll(List<E> l) {
        if (l == null) {
            return false;
        }
        for (int i = 0; i < l.size(); i++) {
            this.addLast(l.get(i));
        }
        return true;
    }

    @Override
    public List<E> findIntersection(List<E> anotherList, Comparator<E> cmp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public boolean containsElement(E element, Comparator<E> comparator) {
        if (element == null || isEmpty()) {
            return false;
        }

        for (int i = 0; i < effectiveSize; i++) {
            if (comparator.compare(elements[i], element)==0) {
                return true;
            }
        }
        return false;
    }

    //TAREA COLECCIONES LINEALES Y COMPARADORES
    
    @Override
    public boolean removeDuplicates(Comparator<E> comparator){
        ArrayList<E> temp = new ArrayList<>(); // creo una lista vacia 
        if(comparator==null){
            return false;
        }
        for(E element: this){
            boolean found = false;
            if(temp.isEmpty()){  //ingreso el primer elemento en la lista temporal para poder iterar sobre ella
                temp.addLast(element);
            }
            for(E element2: temp){
                if(comparator.compare(element, element2)==0){ // si el elemento ya esta en la lista, se activa la bandera
                    found = true;
                }   
            }
            if(!found){
                temp.addLast(element); // si la bandera no se activa, se agrega el elemento a la lista temporal
                }
        }
        this.elements = temp.elements; // sobreescribo el contenido de la lista que invoca al metodo
        this.effectiveSize = temp.effectiveSize;
        return true;
    }
    
    @Override
    public int binarySearch(E element, Comparator<E> comparator) { 
        int start = 0; //declaro los indices de los extremos
        int end = this.size()-1;
        while(start <= end){ 
            int mid = (start+end)/2; //encuentro el punto medio de la seccion
            if(comparator.compare(element, this.get(mid))<0){
                end = mid-1; //se descarta la segunda mitad de la seccion
            }
            if(comparator.compare(element, this.get(mid))>0){
                start = mid+1; //se descarta la primera mitad de la seccion
            }
            if(comparator.compare(element, this.get(mid))==0){
                return mid; //se devuelve el indice del elemento encontrado
            }
        }
        return -1; //el elemento no esta en la lista
    }

    @Override
    public boolean removeElement(E element, Comparator<E> comparator) {
        if(element==null){
            return false;
        }
        if(comparator==null){
            return false;
        }
        boolean found = false;
        for(int i=0; i<this.effectiveSize; i++){ 
            if(comparator.compare(element, this.get(i))==0){
                remove(i);
                i--; // reduzco i para no saltar el nuevo elemento en la posicion del recien eliminado
                found = true;
            }
        }
        return found;
    }

    @Override
    public int getIndexOf(E element, Comparator<E> comparator) { //devuelve el indice de la primera coincidencia
        if(element==null){
            return -1;
        }
        if(comparator==null){
            return -1;
        }
        for(int i=0; i<this.effectiveSize; i++){ 
            if(comparator.compare(element, this.get(i))==0){
                return i;
            }
        }
        return -1;
    }

    @Override
    public Collection<Integer> getAllIndicesOf(E element, Comparator<E> comparator) {
        if(element==null){
            return null;
        }
        if(comparator==null){
            return null;
        }
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i=0; i<this.effectiveSize; i++){ 
            if(comparator.compare(element, this.get(i))==0){
                indexes.addLast(i);
            }
        }
        return indexes;
    }

    @Override
    public boolean sort(Comparator<E> comparator) {
        if(isEmpty()){
            return false;
        }
        if(comparator==null){
            return false;
        }
        for(int i=effectiveSize-1; i>0; i--){ // i maneja la separacion entre elementos ordenados y no ordenados
            for (int j=0; j<i; j++) { // j es menor a i para solo iterar en elementos que aun no han sido ordenados
                if (comparator.compare(get(j), get(j+1))>0) { // si un elemento es mayor a su sucesor, se los intercambia
                    E temp = get(j);
                    set(j, get(j+1));
                    set(j+1, temp);
                }
            }
        }
        return true;
    }

    @Override
    public boolean insertSorted(E element, Comparator<E> comparator) {
        if(element==null){
            return false;
        }
        if(comparator==null){
            return false;
        }
        for(int i=0; i<effectiveSize; i++){ //se itera sobre la lista hasta encontrar el primer elemento mayor al ingresado, en cuya posicion se lo inserta
            if(comparator.compare(element, get(i))<0){
                this.insert(i, element);
                return true;
            }
        }
        addLast(element); //si nunca se encontro un elemento mayor, se agrega al final de la lista
        return true;
    }

    @Override
    public List<E> mergeSorted(List<E> otherList, Comparator<E> comparator) {
        List<E> merged = new ArrayList<>();
        merged.addAll(this);
        merged.addAll(otherList);
        merged.sort(comparator);
        return merged;
    }

    public List<E> findUnion(List<E> otherList, Comparator<E> comparator) {
        List<E> union = new ArrayList<>();
        union.addAll(this);
        for(E element: otherList){
            if(this.contains(element)){
                union.removeElement(element, comparator);
            }
            else{
                union.addLast(element);
            }
        }
        return union;
    }

    @Override
    public boolean containsAll(List<E> elements, Comparator<E> comparator) {
        for(E element: elements){
            if(!this.containsElement(element, comparator)){
                return false;
            }
        }
        return true;
    }
 
}
