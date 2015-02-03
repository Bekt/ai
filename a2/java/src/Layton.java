import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.lang.Math.abs;

public class Layton implements Searchable, Comparable<Searchable> {

    protected int[][] board;
    protected List<List<Point>> shapes;
    protected List<Point> current;
    protected List<Point> goal;
    protected double cost;
    private int hash;
    private int heur;

    public Layton(int[][] board, List<List<Point>> shapes, List<Point> current, List<Point> goal) {
        this.board = board;
        this.shapes = shapes;
        this.current = current;
        this.goal = goal;
    }

    @Override
    public boolean isDone() {
        return current.equals(goal);
    }

    @Override
    public int getDistance(Searchable target) {
        return 1;
    }

    @Override
    public double getHeuristic() {
        if (heur == 0) {
            Point p = current.get(0), q = goal.get(0);
            heur = abs(p.x - q.x) + abs(p.y - q.y);
            // Micro-optimization: (3, 5) is the black triangle's bottom-left corner.
            if (p.x < 3 && p.y < 5) {
                heur += (5 - p.y);
            }
        }
        return heur;
    }

    @Override
    public List<Searchable> getMoves() {
        List<Searchable> moves = new ArrayList<>();
        for (int i = 0; i < shapes.size(); i++) {
            for (Point dir : Search.STEPS) {
                Layton m = getMove(i, dir);
                if (m != null) {
                    moves.add(m);
                }
            }
        }
        return moves;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object obj) {
        int[][] b = ((Layton) obj).board;
        return Arrays.deepEquals(board, b);
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Arrays.deepHashCode(board);
        }
        return hash;
    }

    @Override
    public int compareTo(Searchable o) {
        return Double.compare(getCost(), o.getCost());
    }

    public void print() {
        for (int[] row : board) {
            System.out.println(Arrays.toString(row));
        }
    }

    private Layton getMove(int ind, Point dir) {
        List<Point> shape = shapes.get(ind);
        Point p = new Point();
        for (Point coor : shape) {
            p.setLocation(coor.x + dir.x, coor.y + dir.y);
            if (board[p.y][p.x] != -1 && !shape.contains(p)) {
                return null;
            }
        }
        int[][] nboard = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            nboard[i] = Arrays.copyOf(board[i], board[i].length);
        }
        List<List<Point>> nshapes = new ArrayList<>(shapes);
        List<Point> nshape = new ArrayList<>();
        Set<Point> moved = new HashSet<>();
        for (Point coor : shape) {
            p = new Point(coor.x + dir.x, coor.y + dir.y);
            if (!moved.contains(coor)) {
                nboard[coor.y][coor.x] = -1;
            }
            nboard[p.y][p.x] = board[coor.y][coor.x];
            nshape.add(p);
            moved.add(p);
        }
        nshapes.set(ind, nshape);
        return new Layton(nboard, nshapes, shape.equals(current) ? nshape : current, goal);
    }

    private static Layton getStart() {
        int[][] board = {
                {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                {-2, -2, -2, -1, -1, 10, 10, -2, -2, -2},
                {-2, -2, -1, -1, -1, 10, 9, -1, -2, -2},
                {-2, 0, 0, -1, -2, 9, 9, -1, -1, -2},
                {-2, 0, 0, -2, -2, 6, 7, -1, -1, -2},
                {-2, 1, 2, 2, 6, 6, 7, 7, 8, -2},
                {-2, 1, 1, 2, -1, 6, 7, 8, 8, -2},
                {-2, -2, -1, 5, 3, 3, 4, 4, -2, -2},
                {-2, -2, -2, 5, 5, 3, 4, -2, -2, -2},
                {-2, -2, -2, -2, -2, -2, -2, -2, -2, -2}
        };
        final List<Point> start = Arrays.asList(new Point(2, 3), new Point(1, 3), new Point(2, 4), new Point(1, 4));
        final List<Point> goal = Arrays.asList(new Point(6, 1), new Point(5, 1), new Point(6, 2), new Point(5, 2));
        List<List<Point>> shapes = new ArrayList<List<Point>>(){{
            add(start);
            add(Arrays.asList(new Point(6, 1), new Point(5, 1), new Point(5, 2)));
            add(Arrays.asList(new Point(6, 2), new Point(5, 3), new Point(6, 3)));
            add(Arrays.asList(new Point(1, 5), new Point(1, 6), new Point(2, 6)));
            add(Arrays.asList(new Point(3, 5), new Point(2, 5), new Point(3, 6)));
            add(Arrays.asList(new Point(5, 4), new Point(5, 5), new Point(5, 6), new Point(4, 5)));
            add(Arrays.asList(new Point(6, 4), new Point(6, 5), new Point(6, 6), new Point(7, 5)));
            add(Arrays.asList(new Point(8, 5), new Point(8, 6), new Point(7, 6)));
            add(Arrays.asList(new Point(3, 7), new Point(3, 8), new Point(4, 8)));
            add(Arrays.asList(new Point(5, 7), new Point(4, 7), new Point(5, 8)));
            add(Arrays.asList(new Point(7, 7), new Point(6, 7), new Point(6, 8)));
        }};
        return new Layton(board, shapes, start, goal);
    }

    public static void main(String[] args) {
        List<String> params = Arrays.asList(args);
        int bfs2 = 3615884;
        int astar2 = 2841892;
        // Run only if explicitly specified.
        if (params.contains("run")) {
            boolean debug = params.contains("debug");
            Searchable start = getStart();
            Search search = new Search(debug);
            // BFS takes 3x longer than astar.
            Search.Result resBfs = search.bfs(start);
            Search.Result resAstar = search.astar(start);
            bfs2 = resBfs.iterations;
            astar2 = resAstar.iterations;
            if (debug) {
                ((Layton) resAstar.goal).print();
                System.out.println("bfs2-cost=" + resBfs.cost);
                System.out.println("astar2-cost=" + resAstar.cost);
            }
        }
        System.out.println("bfs2=" + bfs2);
        System.out.println("astar2=" + astar2);
    }

}
