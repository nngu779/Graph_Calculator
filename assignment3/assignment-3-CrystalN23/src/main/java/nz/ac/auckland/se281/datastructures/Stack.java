package nz.ac.auckland.se281.datastructures;

/**
 * Implementation of data structure Stack.
 *
 * @param <T> The type of each element in the stack.
 */
public class Stack<T> {

  private Node<T> top;
  private int size;

  /** Constructor for Stack. */
  public Stack() {
    top = null;
    size = 0;
  }

  /**
   * Adds a new node to the top of the stack.
   *
   * @param data the data of the new node.
   */
  public void push(T data) {
    Node<T> newNode = new Node<>(data);
    newNode.setNext(top);
    top = newNode;
    size++;
  }

  /**
   * Removes the node from the top of the stack.
   *
   * @return data of type T.
   */
  public T pop() {
    // Check for when the stack is empty
    if (isEmpty()) {
      throw new IllegalStateException("Cannot remove from an empty stack.");
    }
    // Save the node to be removed and set the top to the next node
    Node<T> nodeToRemove = top;
    top = top.getNext();
    nodeToRemove.setNext(null);
    // Decrement the size of the stack
    size--;
    return nodeToRemove.getData();
  }

  /**
   * Returns the number of nodes in the stack.
   *
   * @return the number of nodes in the stack.
   */
  public int size() {
    return size;
  }

  /**
   * Check if the stack is empty.
   *
   * @return true if stack is empty, false otherwise.
   */
  public boolean isEmpty() {
    return size == 0;
  }
}
