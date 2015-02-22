import java.util.List;

public interface Searchable {

    public boolean isDone();
    public int getDistance(Searchable target);
    public double getHeuristic();
    public List<Searchable> getMoves();
    public void setCost(double cost);
    public double getCost();

}
