/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdas;

import baseClasses.Contact;
import java.util.Comparator;

/**
 *
 * @author jossu
 * @param <E>
 */
public interface List<E> extends Iterable<E>, Collection<E> {
    
    boolean addFirst(E element);

    boolean addLast(E element);

    boolean removeFirst();

    boolean removeLast();

    E getFirst();

    E getLast();

    boolean insert(int index, E element);

    boolean contains(E element);

    E get(int index);

    int indexOf(E element);

    boolean isEmpty();

    E remove(int index);

    boolean remove(E element);

    E set(int index, E element);

    int size();
    
    public boolean addAll (List<E> l);
    
    public List<E> findIntersection (List<E> anotherList, Comparator<E> cmp);
    
    public boolean removeDuplicates(Comparator<E> comparator);
    
    public int binarySearch(E element, Comparator<E> comparator);
    
    public boolean removeElement(E element, Comparator<E> comparator);
    
    public int getIndexOf(E element, Comparator<E> comparator);
    
    public Collection<Integer> getAllIndicesOf(E element, Comparator<E> comparator);
    
    public boolean sort(Comparator<E> comparator);
    
    public boolean insertSorted(E element, Comparator<E> comparator);
    
    public List<E> mergeSorted(List<E> otherList, Comparator<E> comparator);
    
    public List<E> findUnion(List<E> otherList, Comparator<E> comparator);
    
    public boolean containsAll(List<E> elements, Comparator<E> comparator);
}
