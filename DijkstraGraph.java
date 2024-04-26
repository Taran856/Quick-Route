// === CS400 Spring 2024 File Header Information ===
// Name: Taran Priyakant Patel
// Email: tppatel4@wisc.edu
// Lecturer: Florian heimerl
// Notes to Grader: <optional extra notes>

import org.junit.Assert;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType, EdgeType>
    implements GraphADT<NodeType, EdgeType> {

  /**
   * While searching for the shortest path between two nodes, a SearchNode
   * contains data about one specific path between the start node and another
   * node in the graph. The final node in this path is stored in its node
   * field. The total cost of this path is stored in its cost field. And the
   * predecessor SearchNode within this path is referened by the predecessor
   * field (this field is null within the SearchNode containing the starting
   * node in its node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost
   * SearchNode has the highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double cost;
    public SearchNode predecessor;

    public SearchNode(Node node, double cost, SearchNode predecessor) {
      this.node = node;
      this.cost = cost;
      this.predecessor = predecessor;
    }

    public int compareTo(SearchNode other) {
      if (cost > other.cost)
        return +1;
      if (cost < other.cost)
        return -1;
      return 0;
    }
  }

  /**
   * Constructor that sets the map that the graph uses.
   */
  public DijkstraGraph() {
    super(new PlaceholderMap<>());
  }

  /**
   * This helper method creates a network of SearchNodes while computing the
   * shortest path between the provided start and end locations. The
   * SearchNode that is returned by this method is represents the end of the
   * shortest path that is found: it's cost is the cost of that shortest path,
   * and the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return SearchNode for the final end node within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *                                or when either start or end data do not
   *                                correspond to a graph node
   */
  protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    // Initializing PlaceholderMap and PriorityQueue.
    PlaceholderMap<NodeType, Node> placeholderMap = new PlaceholderMap<>();
    PriorityQueue<SearchNode> pq = new PriorityQueue<>();

    boolean foundEnd = false;

    // Initializing successor and cost.
    Node successor;
    double cost = 0;

    // Throws an error if the start and end data passed as arguments do not correspond to the data
    // held in any nodes within the graph
    if (nodes.get(start) == null || nodes.get(end) == null){
      throw new NoSuchElementException("Either of start or end data do not correspond to the data " +
          "held in any nodes within the graph");
    }
    Node start_node = nodes.get(start);

    // Initializing SearchNode and inserting it to the priority queue.
    SearchNode searchNode = new SearchNode(start_node, 0, null);
    pq.add(searchNode);

    // Looping until the priority queue is empty.
    while (!pq.isEmpty()) {
      // Removing a search node from the priority queue.
      SearchNode predecessor = pq.poll();
      // Checking if the removed search node was visited.
      if (!placeholderMap.containsKey(predecessor.node.data)) {
        // If it was unvisited marking it as visited by adding it to the placeholder map and storing
        // its cost.
        placeholderMap.put(predecessor.node.data, predecessor.node);
        cost = predecessor.cost;
        // Looping to add all the nodes connected to the current node to the priority queue.
        for (Edge edge : predecessor.node.edgesLeaving) {
          successor = edge.successor;
          searchNode = new SearchNode(successor, cost + edge.data.doubleValue(), predecessor);
          pq.add(searchNode);
        }
      }
      // If we reach to the end node then returning the current search node.
      if (predecessor.node.data == end){
        foundEnd = true;
        return predecessor;
      }


    }
    // If the end node was not found throwing NoSuchElementException.
    if (!foundEnd){
      throw new NoSuchElementException();
    }
    return null;
  }

  /**
   * Returns the list of data values from nodes along the shortest path
   * from the node with the provided start value through the node with the
   * provided end value. This list of data values starts with the start
   * value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shorteset path. This
   * method uses Dijkstra's shortest path algorithm to find this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return list of data item from node along this shortest path
   */
  public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    // Initialising a linked list.
    LinkedList<NodeType> list = new LinkedList<>();
    // Adding the end node to the list.
    list.add(end);
    // Initializing search node using computeShortestPath.
    SearchNode searchNode = computeShortestPath(start, end);
    // Creating a loop to add the whole path to the linked list.
    while (searchNode.node.data != start){
      searchNode = searchNode.predecessor;
      list.addFirst(searchNode.node.data);
    }
    return list;
  }

  /**
   * Returns the cost of the path (sum over edge weights) of the shortest
   * path freom the node containing the start data to the node containing the
   * end data. This method uses Dijkstra's shortest path algorithm to find
   * this solution.
   *
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return the cost of the shortest path between these nodes
   */
  public double shortestPathCost(NodeType start, NodeType end) {
    // Initializing cost to 0.
    double cost = 0;
    // Initializing search node using computeShortestPath.
    SearchNode searchNode = computeShortestPath(start, end);
    // Taking cost from the search node.
    cost = searchNode.cost;
    return cost;
  }


  private DijkstraGraph<String, Double> graph;

  /**
   * Setting up a graph to use in testers.
   */
  public void setUp() {
    graph = new DijkstraGraph<>();

    graph.insertNode("A");
    graph.insertNode("B");
    graph.insertNode("C");
    graph.insertNode("D");
    graph.insertNode("E");
    graph.insertNode("F");
    graph.insertNode("G");
    graph.insertNode("H");

    graph.insertEdge("A", "B", 4.0);
    graph.insertEdge("A", "C", 2.0);
    graph.insertEdge("A", "E", 15.0);
    graph.insertEdge("C", "D", 5.0);
    graph.insertEdge("B", "D", 1.0);
    graph.insertEdge("B", "E", 10.0);
    graph.insertEdge("D", "E", 3.0);
    graph.insertEdge("D", "F", 0.0);
    graph.insertEdge("F", "D", 2.0);
    graph.insertEdge("F", "H", 4.0);
    graph.insertEdge("G", "H", 4.0);
  }

  /**
   * Testing the shortest path from A to E.
   */
  @Test
  public void testShortestPath_A_to_E() {
    List<String> expectedPath = List.of("A", "B", "D", "E");
    setUp();
    double expectedCost = 8.0;

    Assert.assertEquals(expectedPath, graph.shortestPathData("A", "E"));
    Assert.assertEquals(expectedCost, graph.shortestPathCost("A", "E"), 0);
  }

  /**
   * Testing the shortest path from A to F.
   */
  @Test
  public void testShortestPath_A_to_F() {
    List<String> expectedPath = List.of("A", "B", "D", "F");
    setUp();
    double expectedCost = 5;

    Assert.assertEquals(expectedPath, graph.shortestPathData("A", "F"));
    Assert.assertEquals(expectedCost, graph.shortestPathCost("A", "F"), 0);
  }

  /**
   * Testing the shortest path from A to G.
   */
  @Test
  public void testNoPath_A_to_G() {
    setUp();
    Exception exception = Assert.assertThrows(NoSuchElementException.class, () -> {
      graph.shortestPathData("A", "G");
    });
  }

  /**
   * Testing a wrong input.
   */
  @Test
  public void testWrongInputs() {
    setUp();
    Exception exception = Assert.assertThrows(NoSuchElementException.class, () -> {
      graph.shortestPathData("A", "I");
    });
  }
}
