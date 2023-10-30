
package tdas;

public class DoubleLinkNode<E> {
    private E content;
    private DoubleLinkNode<E> previous;
    private DoubleLinkNode<E> next;
    
    public DoubleLinkNode(E content){
        this.content = content;
        previous = null;
        next = null;
    }
    
    public DoubleLinkNode(E content, DoubleLinkNode<E> previous, DoubleLinkNode<E> next){
        this.content = content;
        this.previous = previous;
        this.next = next;
    }

    public E getContent() {
        return content;
    }

    public DoubleLinkNode<E> getPrevious() {
        return previous;
    }

    public DoubleLinkNode<E> getNext() {
        return next;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public void setPrevious(DoubleLinkNode<E> previous) {
        this.previous = previous;
    }

    public void setNext(DoubleLinkNode<E> next) {
        this.next = next;
    }
    
}
