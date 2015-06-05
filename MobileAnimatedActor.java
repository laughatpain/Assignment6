import processing.core.PImage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
   private AStarComponent[][] node;
   private ArrayList <Point> path;
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
      this.path = new ArrayList<>();
   }

   public ArrayList<Point> getPath()
   {
      return this.path;
   }

   protected Point nextPosition(WorldModel world, Point entity_pt, Point dest_pt)
   {
      aStar(world, entity_pt, dest_pt);

      Point newPoint = entity_pt;

      if (!getPath().isEmpty())
      {
         newPoint = getPath().get(getPath().size() - 2);
      }

      return newPoint;
   }
   public void aStar (WorldModel world, Point start, Point dest)
   {
      List<AStarComponent> open_set = new ArrayList<>();
      List<AStarComponent> closed_set = new LinkedList<>();

      node = new AStarComponent[world.getNumRows()][world.getNumCols()];

      for (int i = 0; i < world.getNumCols(); i++)
      {
         for (int j = 0; j < world.getNumRows(); j++)
         {
            node[j][i] = new AStarComponent(new Point(i,j));
         }
      }
      path = new ArrayList<>();

      node[start.y][start.x].setgScore(0);
      node[start.y][start.x].setfScore(node[start.y][start.x].getgScore() +
              start.distanceTo(dest));
      open_set.add(node[start.y][start.x]);

      while (open_set.size() != 0)
      {
         int index = lowestFOpenSet(open_set);
         AStarComponent current = open_set.remove(index);
         if (current.getPt().equals(dest))
         {
            reconstruct_path(current);
            return;
         }

         open_set.remove(current);
         closed_set.add (current);
         for(AStarComponent neighbor : getNeighbors (current, world, dest))
         {
            if (closed_set.contains(neighbor))
            {
               continue;
            }
            int tentativegScore = current.getgScore() + 1;

            if (!open_set.contains(neighbor) || tentativegScore < neighbor.getgScore())
            {
               neighbor.setPastNode(current);
               neighbor.setgScore(tentativegScore);
               neighbor.setfScore(neighbor.getgScore() + neighbor.getPt().distanceTo(dest));
               if (!open_set.contains(neighbor))
               {
                  open_set.add(neighbor);
               }
            }
         }
         path = new ArrayList<>();
      }
   }
   private ArrayList <AStarComponent> getNeighbors (AStarComponent current, WorldModel world, Point goal)
   {
      ArrayList<AStarComponent> neighbors = new ArrayList<>();

      Point current_pt = current.getPt();
      int currentPtX = current_pt.x;
      int currentPtY = current_pt.y;
      Point up = new Point (currentPtX, currentPtY - 1);
      Point down = new Point (currentPtX, currentPtY + 1);
      Point right = new Point (currentPtX + 1, currentPtY);
      Point left = new Point (currentPtX - 1, currentPtY);

      if (world.withinBounds(up) && canPassThrough(world, up)|| up.equals(goal) )
      {
         neighbors.add(node[up.y][up.x]);
      }
      if (world.withinBounds(down) && canPassThrough(world, down) || down.equals(goal))
      {
         neighbors.add(node[down.y][down.x]);
      }
      if (world.withinBounds(right) && canPassThrough(world, right) || right.equals(goal))
      {
         neighbors.add(node[right.y][right.x]);
      }
      if (world.withinBounds(left) && canPassThrough(world, left) || left.equals(goal))
      {
         neighbors.add(node[left.y][left.x]);
      }
      return neighbors;
   }

   private static int lowestFOpenSet(List <AStarComponent> open_set)
   {
      int lowest = 0;
      for (int i = 1; i < open_set.size(); i++)
      {
         AStarComponent current = open_set.get(i);
         if (open_set.get(lowest).getfScore() > current.getfScore())
         {
            lowest = i;
         }
      }
      return lowest;
   }

   public void reconstruct_path (AStarComponent current)
   {
      ArrayList <Point> total_path = new ArrayList<>();
      total_path.add(current.getPt());

      while (current.getpastNode() != null)
      {
         total_path.add(current.getpastNode().getPt());
         current = current.getpastNode();
      }
      path = total_path;
   }

   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt);
}
