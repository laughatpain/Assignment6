import processing.core.PImage;
import java.util.List;

public class PikachuNotFull
        extends Pikachu
{
   public PikachuNotFull(String name, Point position, int rate,
                         int animation_rate, int resource_limit, int resource_total, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, resource_limit,
              0, resource_total, RareCandy.class, imgs);
   }

   public String toString()
   {
      return String.format("miner %s %d %d %d %d %d", getName(),
              getPosition().x, getPosition().y, getResourceLimit(),
              getRate(), getAnimationRate());
   }

   protected Pikachu transform(WorldModel world)
   {
      if (getResourceCount() < getResourceLimit())
      {
         return this;
      }
      else
      {
         return new PikachuFull(getName(), getPosition(), getRate(),
                 getAnimationRate(), getResourceLimit(), getResourceTotal(), getImages(), world);
      }
   }

   protected boolean move(WorldModel world, WorldEntity ore)
   {
      if (ore == null)
      {
         return false;
      }

      if (adjacent(getPosition(), ore.getPosition()))
      {
         setResourceTotal(getResourceTotal() + 1);
         setResourceCount(getResourceCount() + 1);
         ore.remove(world);
         return true;
      }

      else
      {
         world.moveEntity(this, nextPosition(world, this.getPosition(), ore.getPosition()));
         return false;
      }
   }
}