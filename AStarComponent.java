/**
 * Created by James on 5/27/2015.
 */
public class AStarComponent
{
    public int gScore;
    public double fScore;
    public Point pt;
    public AStarComponent pastNode;

    public AStarComponent(Point pt)
    {
        this.pt = pt;
    }

    public int getgScore()
    {
        return gScore;
    }

    public void setgScore(int newgScore)
    {
        gScore = newgScore;
    }

    public double getfScore()
    {
        return fScore;
    }

    public void setfScore(double newfScore)
    {
        fScore = newfScore;
    }

    public Point getPt()
    {
        return pt;
    }
    public AStarComponent getpastNode ()
    {
        return pastNode;
    }
    public void setPastNode (AStarComponent newNode)
    {
        pastNode = newNode;
    }
}
