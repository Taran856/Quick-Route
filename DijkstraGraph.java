import java.util.PriorityQueue;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Arrays;
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
      if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
        throw new NoSuchElementException();
      }

      // Initialize priority queue and map of visited nodes
      PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>();
      MapADT<NodeType,NodeType> visitedNodes = new PlaceholderMap<NodeType,NodeType>();
      queue.add(new SearchNode(nodes.get(start), 0, null));

      // Removes SearchNodes from queue
      while (!queue.isEmpty()) {
        SearchNode shortestPath = queue.remove();
        // if (shortestPath.predecessor != null) {
        //   System.out.println("Node: "+shortestPath.node.data+" Cost: "+shortestPath.cost+" Predecessor: "+shortestPath.predecessor.node.data);
        // }
        if (shortestPath.node.equals(nodes.get(end))) {
          return shortestPath;
        }

        // Adds new branching out paths to queue
        if (!visitedNodes.containsKey(shortestPath.node.data)) {
          visitedNodes.put(shortestPath.node.data, shortestPath.node.data);
          for (Edge edge: shortestPath.node.edgesLeaving) {
            if (!visitedNodes.containsKey(edge.successor.data)) {
              queue.add(new SearchNode(edge.successor, shortestPath.cost + edge.data.doubleValue(), shortestPath));
            }
          }
        }
      }
      throw new NoSuchElementException();
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
      try {
        LinkedList<NodeType> path = new LinkedList<NodeType>();
        SearchNode node = computeShortestPath(start, end);
        while (node != null) {
          path.addFirst(node.node.data);
          node = node.predecessor;
        }
        return path;
      } catch (NoSuchElementException e) {
        return new LinkedList<NodeType>();
      }
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
      SearchNode node = computeShortestPath(start, end);
      return node.cost;
    }
    
    /**
     * Checks shortestPath methods when using the graph from lecture and finding a path between nodes A and E
     */
    @Test
    public void testOne() {
      DijkstraGraph<String,Integer> graph = new DijkstraGraph<>();
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");
      graph.insertEdge("A","B",4);
      graph.insertEdge("A","E",15);
      graph.insertEdge("A","C",2);
      graph.insertEdge("B","D",1);
      graph.insertEdge("B","E",10);
      graph.insertEdge("C","D",5);
      graph.insertEdge("C","D",5);
      graph.insertEdge("D","E",3);
      graph.insertEdge("D","F",0);
      graph.insertEdge("F","D",2);
      graph.insertEdge("F","H",4);
      graph.insertEdge("G","H",4);

      List<String> expectedList = Arrays.asList(new String[] {"A", "B", "D", "E"});
      List<String> actualList = graph.shortestPathData("A", "E");
      double expectedCost = 8;
      double actualCost = graph.shortestPathCost("A", "E");
      
      if (!expectedList.equals(actualList) || actualCost != expectedCost) {
        Assertions.fail();
      }
    }

    /**
     * Checks shortestPath methods when using the graph from lecture and finding a path between nodes C and E
     */
    @Test
    public void testTwo() {
      DijkstraGraph<String,Integer> graph = new DijkstraGraph<>();
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");
      graph.insertEdge("A","B",4);
      graph.insertEdge("A","E",15);
      graph.insertEdge("A","C",2);
      graph.insertEdge("B","D",1);
      graph.insertEdge("B","E",10);
      graph.insertEdge("C","D",5);
      graph.insertEdge("C","D",5);
      graph.insertEdge("D","E",3);
      graph.insertEdge("D","F",0);
      graph.insertEdge("F","D",2);
      graph.insertEdge("F","H",4);
      graph.insertEdge("G","H",4);

      List<String> expectedList = Arrays.asList(new String[] {"C", "D", "E"});
      List<String> actualList = graph.shortestPathData("C", "E");
      double expectedCost = 8;
      double actualCost = graph.shortestPathCost("C", "E");
      
      if (!expectedList.equals(actualList) || actualCost != expectedCost) {
        Assertions.fail();
      }
    }

    /**
     * Checks shortestPath methods when there is no sequence of directed edges that connects the two nodes
     */
    @Test
    public void testThree() {
      DijkstraGraph<String,Integer> graph = new DijkstraGraph<>();
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");
      graph.insertEdge("A","B",4);
      graph.insertEdge("A","E",15);
      graph.insertEdge("A","C",2);
      graph.insertEdge("B","D",1);
      graph.insertEdge("B","E",10);
      graph.insertEdge("C","D",5);
      graph.insertEdge("C","D",5);
      graph.insertEdge("D","E",3);
      graph.insertEdge("D","F",0);
      graph.insertEdge("F","D",2);
      graph.insertEdge("F","H",4);
      graph.insertEdge("G","H",4);

      try {
        graph.shortestPathData("A", "G");
        Assertions.fail();
      } catch (NoSuchElementException error) {

      } catch (Exception error) {
        Assertions.fail();
      }
      
      try {
        graph.shortestPathCost("A", "G");
        Assertions.fail();
      } catch (NoSuchElementException error) {

      } catch (Exception error) {
        Assertions.fail();
      }
    }
}
