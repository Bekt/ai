import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Search {

    public static Point[] STEPS = {
            new Point(0, 1),
            new Point(0, -1),
            new Point(1, 0),
            new Point(-1, 0)
    };

    private Queue<Searchable> pq;
    private Map<Searchable, Integer> entries;
    private Set<Integer> removed;
    private boolean debug = false;

    public Search() {}

    public Search(boolean debug) {
        this.debug = debug;
    }

    public Result astar(Searchable start) {
        return search(start, true);
    }

    public Result bfs(Searchable start) {
        return search(start, false);
    }

    protected Result search(Searchable start, boolean astar) {
        reset();
        pq.add(start);
        Map<Searchable, Double> g = new HashMap<>();
        g.put(start, 0.);
        int i = 0;
        Searchable item;
        while ((item = pop()) != null) {
            i += 1;
            if (item.isDone()) {
                return new Result(item, g.get(item), i);
            }
            if (debug && i % 50000 == 0) {
                System.out.println(i + " " + item.getCost() + " " + pq.size());
            }
            for (Searchable move : item.getMoves()) {
                double newCost = g.get(item) + item.getDistance(move);
                if (!g.containsKey(move) || newCost < g.get(move)) {
                    double h = astar ? move.getHeuristic() : 0;
                    move.setCost(newCost + h);
                    g.put(move, newCost);
                    push(move);
                }
            }
        }
        return new Result(null, 0, i);
    }

    private void push(Searchable item) {
        if (entries.containsKey(item)) {
            int id = entries.remove(item);
            removed.add(id);
        }
        // Unique ID.
        entries.put(item, System.identityHashCode(item));
        pq.add(item);
    }

    private Searchable pop() {
        while (!pq.isEmpty()) {
            Searchable item = pq.poll();
            int id = System.identityHashCode(item);
            if (!removed.contains(id)) {
                entries.remove(item);
                return item;
            }
        }
        return null;
    }

    private void reset() {
        pq = new PriorityQueue<>();
        entries = new HashMap<>();
        removed = new HashSet<>();
    }

    /** Why can't Java have simple tuples... **/
    public class Result {
        public final Searchable goal;
        public final double cost;
        public final int iterations;

        public Result(Searchable goal, double cost, int iterations) {
            this.goal = goal;
            this.cost = cost;
            this.iterations = iterations;
        }
    }

}
