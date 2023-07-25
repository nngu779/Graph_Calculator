package nz.ac.auckland.se281.datastructures;

/**
 * Implementation of a node in a linked list.
 *
 * @param <T> The type of each element in the list.
 */
public class Node<T> {
  private T data;
  private Node<T> next;
  private Node<T> prev;

  /**
   * Constructor for Node.
   *
   * @param data the data of a node.
   */
  public Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  /**
   * Returns the value of the node.
   *
   * @return the value of node type T.
   */
  public T getData() {
    return this.data;
  }

  /**
   * Sets value of the node.
   *
   * @param data the data to set for the node.
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Returns the value of the next node in the list.
   *
   * @return the next node.
   */
  public Node<T> getNext() {
    return this.next;
  }

  /**
   * Set the value of the next node in the list.
   *
   * @param next the value to be set for the next node.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  /** Returns the value of the previous node in the list. */
  public Node<T> getPrev() {
    return prev;
  }

  /**
   * Set the value of the previous node in the list.
   *
   * @param prev the value to be set for the previous node.
   */
  public void setPrev(Node<T> prev) {
    this.prev = prev;
  }
}
