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
        if (isEmpty()) {
            return 0;
        }
        DoubleLinkNode<E> current = reference;
        int size = 0;
        System.out.println(reference);
        do {
            current = current.getNext();
            size++;
        } while (!current.equals(reference));
        return size;
    }

    @Override
    public boolean isEmpty() {
        return reference == null;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) >= 0;
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
        if (reference != null) {
            String string = "";
            DoubleLinkNode<E> cursor = reference;
            do {
                string += cursor.toString();
                cursor = cursor.getNext();
            } while (cursor != reference);
            return string;
        } else {
            return "";
        }
    }

    @Override
    public boolean addFirst(E element) {
        DoubleLinkNode<E> newNode = new DoubleLinkNode<>(element);
        if (reference == null) {
            reference = newNode;
            reference.setNext(reference);
            reference.setPrevious(reference);
        }else{
            newNode.setNext(reference);
            newNode.setPrevious(reference.getPrevious());
            reference.setPrevious(newNode);
            newNode.getPrevious().setNext(newNode);
            reference = newNode;
        }
        return true;
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
        return getNode(index).getContent();
    }

    public DoubleLinkNode<E> getNode(int index) {
        DoubleLinkNode<E> current = reference;
        int counter = 0;
        while (counter < index) {
            current = current.getNext();
            counter++;
            if (current == reference) {
                return null;
            }
        }
        return current;
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        if (element == null) {
            for (DoubleLinkNode<E> x = reference; x != null; x = x.getNext()) {
                if (x.getContent() == null)
                    return index;
                index++;
            }
        } else {
            for (DoubleLinkNode<E> x = reference; x != null; x = x.getNext()) {
                if (element.equals(x.getContent()))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(E element) {
        if (reference == null) {
            return false;
        }
        DoubleLinkNode<E> current = reference;

        do {
            if (current.getContent().equals(element)) {
                if (current.getNext() == current) {
                    reference = null;
                } else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    if (current == reference) {
                        reference = current.getNext();
                    }
                }
                return true;
            }
            current = current.getNext();
        } while (current != reference);
        return false;
    }

    public boolean remove(E element, Comparator<E> comp) {
        if (reference == null) {
            return false;
        }
        DoubleLinkNode<E> current = reference;

        do {
            if (comp.compare(current.getContent(), element)==0) {
                if (current.getNext() == current) {
                    reference = null;
                } else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    if (current == reference) {
                        reference = current.getNext();
                    }
                }
                return true;
            }
            current = current.getNext();
        } while (current != reference);
        return false;
    }

    @Override
    public E set(int index, E element) {
        DoubleLinkNode<E> node = getNode(index);
        E gone = node.getContent();
        node.setContent(element);
        return gone;
    }

    @Override
    public boolean addAll(List<E> l) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
