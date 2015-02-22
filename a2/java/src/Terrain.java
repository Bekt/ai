import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

public class Terrain implements Searchable, Comparable<Searchable> {

    protected BufferedImage img;
    protected Point current;
    protected Point goal;
    protected double cost;
    private int heur;

    public Terrain(BufferedImage img, Point current, Point goal) {
        this.img = img;
        this.current = current;
        this.goal = goal;
    }

    @Override
    public boolean isDone() {
        return current.equals(goal);
    }

    @Override
    public int getDistance(Searchable target) {
        Point p = ((Terrain) target).current;
        // Green pixel value.
        return (img.getRGB(p.x, p.y) >> 8) & 0xFF;
    }

    @Override
    public double getHeuristic() {
        if (heur == 0) {
            heur = 14 * (abs(current.x - goal.x) + abs(current.y - goal.y));
        }
        return heur;
    }

    @Override
    public List<Searchable> getMoves() {
        List<Searchable> moves = new ArrayList<>();
        for (Point p : Search.STEPS) {
            int dx = current.x + p.x;
            int dy = current.y + p.y;
            if ((dx >= 0 && dx < img.getWidth() && dy >= 0 && dy < img.getHeight())) {
                moves.add(new Terrain(img, new Point(dx, dy), goal));
            }
        }
        return moves;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        Point p = ((Terrain) obj).current;
        return current.equals(p);
    }

    @Override
    public int hashCode() {
        return current.hashCode();
    }

    @Override
    public int compareTo(Searchable o) {
        return Double.compare(getCost(), o.getCost());
    }

    public static void main(String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("terrain.png"));
        Searchable start = new Terrain(img, new Point(100, 100), new Point(400, 400));
        Search search = new Search();
        Search.Result res = search.bfs(start);
        System.out.println("bfs1=" + res.iterations);
        res = search.astar(start);
        System.out.println("astar1=" + res.iterations);
    }

}
