package nz.ac.auckland.se281.datastructures;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of data structure Queue.
 *
 * @param <T> The type of each element in the queue.
 */
public class Queue<T> {

  private Node<T> head;
  private Node<T> tail;
  private Set<T> nodes;

  /** Constructor for Queue. */
  public Queue() {
    this.head = null;
    this.tail = null;
    this.nodes = new HashSet<>();
  }

  /**
   * Add an element to the back of the queue.
   *
   * @param data the data of the new node.
   */
  public void enqueue(T data) {
    Node<T> newNode = new Node<>(data);
    // Check if the queue is empty and set the head and tail to the new node
    if (isEmpty()) {
      head = newNode;
      tail = newNode;
    } else {
      // If the queue is not empty, set the next node of the current tail to the new node and
      tail.setNext(newNode);
      newNode.setPrev(tail);
      tail = newNode;
    }
  }

  /**
   * Remove the element from the front of the queue.
   *
   * @return data of type T.
   */
  public T dequeue() {
    if (isEmpty()) {
      // Cannot dequeue from an empty queue
      throw new IndexOutOfBoundsException();
    } else {
      // Get the data of the head node
      T data = head.getData();
      // Set the head to the next node
      head = head.getNext();
      // If the head is null, the queue is empty so set the tail to null
      if (head == null) {
        tail = null;
      }
      return data;
    }
  }

  /** Returns true if the queue is empty, false otherwise. */
  public boolean isEmpty() {
    return head == null;
  }

  /**
   * Check if a node is in the queue.
   *
   * @param node the node to check.
   * @return true if the queue contains the node, false otherwise.
   */
  public boolean contains(T node) {
    return nodes.contains(node);
  }
}
