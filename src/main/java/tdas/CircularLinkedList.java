package tdas;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author euclasio
 * @param <E>
 */
public class CircularLinkedList<E> implements List<E>, Serializable {

    private DoubleLinkNode<E> reference;

    public CircularLinkedList() {
        reference = null;
    }

    public void setReference(DoubleLinkNode<E> e) {
        reference = e;
    }

    public DoubleLinkNode<E> getReference() {
        return reference;
    }

    @Override
    public int size() {
        if(isEmpty()){
            return 0;
        }
        DoubleLinkNode current = reference;
        int size = 0;
        do{
            current = current.getNext();
            size++;
        }
        while(!current.equals(reference));
        return size;
    }

    @Override
    public boolean isEmpty() {
        return reference == null;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> itr = new Iterator<>() {
            private DoubleLinkNode<E> current = reference;
            private boolean first = true;

            @Override
            public boolean hasNext() {
                return first || !current.equals(reference);
            }

            @Override
            public E next() {
                if (first) {
                    first = false;
                } else {
                    current = current.getNext();
                }
                return current.getContent();
            }
        };
        return itr;
    }

    public void display() {
        if (reference == null) {
            System.out.println("List is empty");
            return;
        }

        DoubleLinkNode<E> current = reference;
        do {
            System.out.print(current.getContent() + " ");
            current = current.getNext();
        } while (current != reference);
        System.out.println();
    }

    public Iterator<E> reverse() {
        Iterator<E> itr = new Iterator<>() {
            DoubleLinkNode<E> cursor = reference;

            @Override
            public boolean hasNext() {
                return cursor != null;
            }

            public E next() {
                E content = cursor.getContent();
                cursor = cursor.getPrevious();
                return content;
            }
        };
        return itr;
    }
    @Override
    public String toString() {
        if(reference!=null){
            String string = "";
        DoubleLinkNode<E> cursor = reference;
        do{
            string+=cursor.toString();
            cursor = cursor.getNext();
        }
        while(cursor!=reference);
        return string;
        }
        else{
            return "";
        }
    }

    @Override
    public boolean addFirst(E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addLast(E element) {
        DoubleLinkNode<E> newNode = new DoubleLinkNode<>(element);
        if (reference == null) {
            reference = newNode;
            reference.setNext(reference);
            reference.setPrevious(reference);
        } else {
            DoubleLinkNode<E> lastNode = reference.getPrevious();
            lastNode.setNext(newNode);
            newNode.setPrevious(lastNode);
            newNode.setNext(reference);
            reference.setPrevious(newNode);
        }
        return true;
    }

    @Override
    public boolean removeFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E getFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E getLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean insert(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E get(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int indexOf(E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addAll(List<E> l) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<E> findIntersection(List<E> anotherList, Comparator<E> cmp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeDuplicates(Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int binarySearch(E element, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeElement(E element, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getIndexOf(E element, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Collection<Integer> getAllIndicesOf(E element, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean sort(Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean insertSorted(E element, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<E> mergeSorted(List<E> otherList, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<E> findUnion(List<E> otherList, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean containsAll(List<E> elements, Comparator<E> comparator) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
