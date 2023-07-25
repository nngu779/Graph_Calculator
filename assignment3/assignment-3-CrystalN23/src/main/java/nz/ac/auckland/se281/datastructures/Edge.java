package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  /**
   * Constructor a new edge with given source and destination.
   *
   * @param source the source of an edge.
   * @param destination the destination of an edge.
   */
  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Getter for source of an Edge.
   *
   * @return source of type T.
   */
  public T getSource() {
    return source;
  }

  /**
   * Getter for destination of an Edge.
   *
   * @return destination of type T.
   */
  public T getDestination() {
    return destination;
  }

  /**
   * Computes the hashcode of an edge.
   *
   * @return hashcode value of type int.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    return result;
  }

  /**
   * Compares this edge to a specified object for equality.
   *
   * @param obj the object to compare
   * @return true if the two are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    // Check if obj is null, if so return false
    if (obj == null) {
      return false;
    }
    // Check if obj is an instance of Edge, if not then return false
    if (getClass() != obj.getClass()) {
      return false;
    }
    // Cast obj to Edge
    Edge<T> other = (Edge<T>) obj;
    // Check if source is null or not equal, if so return false
    if (source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!source.equals(other.source)) {
      return false;
    }
    // Check if destination is null or not equal, if so return false
    if (destination == null) {
      if (other.destination != null) {
        return false;
      }
    } else if (!destination.equals(other.destination)) {
      return false;
    }
    return true;
  }
}
