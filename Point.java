public class Point
{
   public final int x;
   public final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }
   public double distanceTo (Point goal)
   {
      return Math.sqrt(((this.x - goal.x) * (this.x - goal.x)) + ((this.y - goal.y) * (this.y - goal.y)));
   }
   public boolean equals(Object obj)
   {
      if(obj instanceof Point)
      {
         if(obj == this)
         {
            return true;
         }
         else
         {
            Point other = (Point) obj;
            return x == other.x && y == other.y;
         }
      }
      else
      {
         return false;
      }
   }
}

