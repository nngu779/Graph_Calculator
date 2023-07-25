package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private Set<T> verticies;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.verticies = verticies;
    this.edges = edges;
  }

  /**
   * Method returns the root(s) of a graph.
   *
   * @return the roots to a graph sorted in numeric order.
   */
  public Set<T> getRoots() {
    List<T> roots = new ArrayList<>();
    for (T vertex : verticies) {
      // If the graph is equivalence, then the roots are the first element of each equivalence class
      if (isEquivalence()) {
        roots.add(getEquivalenceClass(vertex).iterator().next());
      } else {
        // If the graph is not equivalence, then the roots are the vertices that have no incoming
        // except for itself.
        int count = 0;
        for (Edge<T> edge : edges) {
          if (edge.getDestination().equals(vertex)) {
            count++;
          }
        }
        if (count == 0) {
          roots.add(vertex);
        }
      }
    }

    // Sort roots
    roots = sortedList(roots);

    // Create a new linkedHashSet and save the sorted roots into it
    Set<T> sortedRoots = new LinkedHashSet<>(roots);

    return sortedRoots;
  }

  /**
   * Sorting a list in ascending order using bubble sort.
   *
   * @param list the list to be sorted.
   * @return the sorted list.
   */
  public List<T> sortedList(List<T> list) {
    // Bubble sort
    for (int i = 0; i < list.size() - 1; i++) {
      for (int j = 0; j < list.size() - i - 1; j++) {
        // Swap if the element is greater than the next element
        if (Integer.parseInt(list.get(j).toString())
            > Integer.parseInt(list.get(j + 1).toString())) {
          T temp = list.get(j);
          list.set(j, list.get(j + 1));
          list.set(j + 1, temp);
        }
      }
    }
    return list;
  }

  /**
   * For each vertex, check if there is an edge from the vertex to itself. If there is not, then the
   * graph is not reflexive. The graph is reflexive if and only if for all vertices v, (v, v) is an
   * edge.
   */
  public boolean isReflexive() {
    int count = 0;
    for (T vertex : verticies) {
      for (Edge<T> edge : edges) {
        // Check if there is an edge from the vertex to itself
        if (edge.getSource().equals(vertex) && edge.getDestination().equals(vertex)) {
          count++;
        }
      }
    }
    // Check if this is the case for all vertices
    if (count == verticies.size()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * The graph is symmetric if and only if for all (v1, v2) is an edge, (v2, v1) is also an edge.
   */
  public boolean isSymmetric() {
    for (Edge<T> edge : edges) {
      if (!edges.contains(new Edge<T>(edge.getDestination(), edge.getSource()))) {
        return false;
      }
    }
    return true;
  }

  /**
   * The graph is transitive if and only if for all (v1,v2) is an edge, and (v2,v3) is an edge, then
   * (v1,v3) is also an edge.
   */
  public boolean isTransitive() {
    for (Edge<T> edge : edges) {
      T source1 = edge.getSource();
      T destination1 = edge.getDestination();
      // Check if there is an edge from the source to the destination
      for (Edge<T> edge2 : edges) {
        T source2 = edge2.getSource();
        T destination2 = edge2.getDestination();
        if (destination1.equals(source2)) {
          if (!edges.contains(new Edge<T>(source1, destination2))) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * The graph is anti-symmetric if and only if for all (v1,v2) is an edge, and (v2,v1) is an edge,
   * then v1 = v2.
   */
  public boolean isAntiSymmetric() {
    // Save the source and destination of a given edge
    for (Edge<T> edge : edges) {
      T source = edge.getSource();
      T destination = edge.getDestination();
      // Check if there is an edge from the destination to the source
      if (edges.contains(new Edge<T>(destination, source))) {
        if (!source.equals(destination)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * For a graph to have equivalence satisfied, it needs to be symmetric, reflexive and transitive.
   */
  public boolean isEquivalence() {
    return (isSymmetric() && isReflexive() && isTransitive());
  }

  /**
   * This method calculates the equivalence class of the given vertex.
   *
   * @param vertex the vertex to calculate the equivalence class of if the graph is equivalent.
   * @return the equivalence class which is an array of the vertex's destinations if it's
   *     equivalent.
   */
  public Set<T> getEquivalenceClass(T vertex) {
    // Initialise a new arraylist to store the equivalence class
    Set<T> equivalenceClass = new HashSet<>();
    if (isEquivalence()) {
      // If a vertex is equivalent, the equivalence class contains all vertices that are connected
      // to it.
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex)) {
          equivalenceClass.add(edge.getDestination());
        }
      }
    }
    return equivalenceClass;
  }

  /**
   * Returns a sorted ist of verticies that are adjacent to the given vertex.
   *
   * @param vertex the vertex to get the adjacent verticies of.
   * @return the list of adjacent verticies.
   */
  public List<T> getAdjacents(T vertex) {
    List<T> adjacents = new ArrayList<>();
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(vertex)) {
        adjacents.add(edge.getDestination());
      }
    }
    // Sort the list of adjacents in ascending order
    adjacents = sortedList(adjacents);

    return adjacents;
  }

  /**
   * Breadth First Search explores all vertices of a graph at the same level before moving to the
   * next level. It will use a queue to explore all the verticies that are reachable from the
   * source.
   *
   * @return a list of verticies in the order they were visited.
   */
  public List<T> iterativeBreadthFirstSearch() {
    Queue<T> breadthFirst = new Queue<T>();
    Set<T> visited = new HashSet<T>();
    List<T> list = new ArrayList<>();

    // If there are no roots, return an empty list
    Set<T> roots = getRoots();
    if (roots.size() == 0) {
      return list;
    }

    for (T vertex : roots) {
      breadthFirst.enqueue(vertex);
      visited.add(vertex);

      while (!breadthFirst.isEmpty()) {
        // Dequeue node from front of the queue and add it into list
        T node = breadthFirst.dequeue();
        list.add(node);
        // Add all adjacent nodes of the dequeued node to the queue and visited set as they are
        // processed
        for (T adjacent : getAdjacents(node)) {
          if (!visited.contains(adjacent)) {
            breadthFirst.enqueue(adjacent);
            visited.add(adjacent);
          }
        }
      }
    }

    return list;
  }

  /**
   * Depth First Search explores all the vertices of a graph by going down a path until it reaches a
   * dead end. It will use a stack to explore all the verticies that are reachable from the source.
   *
   * @return a list type T of verticies.
   */
  public List<T> iterativeDepthFirstSearch() {
    Stack<T> depthFirst = new Stack<T>();
    Set<T> visited = new HashSet<T>();
    List<T> list = new ArrayList<>();

    // If there are no roots, return an empty list
    Set<T> roots = getRoots();
    if (roots.size() == 0) {
      return list;
    }

    for (T vertex : roots) {
      depthFirst.push(vertex);

      // While the stack is not empty, pop the top element and add it to the list
      while (!depthFirst.isEmpty()) {
        T currentVertex = depthFirst.pop();

        // If the current vertex has not been visited, add it to the visited set and the list
        if (!visited.contains(currentVertex)) {
          visited.add(currentVertex);
          list.add(currentVertex);

          // Add all adjacent nodes of the current vertex to the stack
          List<T> adjacents = getAdjacents(currentVertex);
          for (int i = adjacents.size() - 1; i >= 0; i--) {
            if (!visited.contains(adjacents.get(i))) {
              depthFirst.push(adjacents.get(i));
            }
          }
        }
      }
    }

    return list;
  }

  /**
   * Breadth First Search explores all vertices of a graph at the same level before moving to the
   * next level. It will use a queue to explore all the verticies that are reachable from the
   * source.
   *
   * @return a list of verticies in the order they were visited.
   */
  public List<T> recursiveBreadthFirstSearch() {
    Queue<T> breadthFirst = new Queue<T>();
    List<T> visited = new ArrayList<T>();
    List<T> list = new ArrayList<>();

    Set<T> roots = getRoots();
    for (T vertex : roots) {
      breadthFirst.enqueue(vertex);
      visited.add(vertex);
      // Call the recuresive helper method
      recursiveBreadthFirstSearchHelper(breadthFirst, visited, list);
    }
    return list;
  }

  /**
   * Recursion method for breadth first search.
   *
   * @param breadthFirst queue type T.
   * @param visited array list of type T.
   * @param list array list of type T.
   */
  public void recursiveBreadthFirstSearchHelper(
      Queue<T> breadthFirst, List<T> visited, List<T> list) {
    // Base case
    if (breadthFirst.isEmpty()) {
      return;
    }
    // Dequeue node from front of the queue and add it into list
    T vertex = breadthFirst.dequeue();
    list.add(vertex);

    // Add all sorted adjacent nodes of the dequeued node to the queue and visited set as they are
    List<T> adjacents = getAdjacents(vertex);
    adjacents = sortedList(adjacents);
    for (T adjacent : adjacents) {
      if (!visited.contains(adjacent)) {
        breadthFirst.enqueue(adjacent);
        visited.add(adjacent);
      }
    }
    // Call the recursive method
    recursiveBreadthFirstSearchHelper(breadthFirst, visited, list);
  }

  /**
   * Depth First Search explores all the vertices of a graph by going down a path until it reaches a
   * dead end. It will use a stack to explore all the verticies that are reachable from the source.
   *
   * @return a list of vertices in the DFS order.
   */
  public List<T> recursiveDepthFirstSearch() {
    Set<T> visited = new HashSet<T>();
    List<T> list = new ArrayList<>();

    // Call the recursive helper method
    Set<T> roots = getRoots();
    for (T vertex : roots) {
      if (!visited.contains(vertex)) {
        recursiveDepthFirstSearchHelper(visited, list, vertex);
      }
    }
    return list;
  }

  /**
   * Recursion method for depth first search.
   *
   * @param visited set of type T.
   * @param list array list of type T.
   * @param vertex of type T.
   */
  public void recursiveDepthFirstSearchHelper(Set<T> visited, List<T> list, T vertex) {
    visited.add(vertex);
    list.add(vertex);

    // Recursively call the method for all adjacent nodes of the current vertex
    List<T> adjacents = getAdjacents(vertex);
    for (T adjacent : adjacents) {
      if (!visited.contains(adjacent)) {
        recursiveDepthFirstSearchHelper(visited, list, adjacent);
      }
    }
  }
}
