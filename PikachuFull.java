import processing.core.PImage;
import java.util.List;

public class PikachuFull
        extends Pikachu
{
   public PikachuFull(String name, Point position, int rate,
                      int animation_rate, int resource_limit, int resource_total, List<PImage> imgs,
                      WorldModel world)
   {
      super(name, position, rate, animation_rate, resource_limit,
              resource_limit, resource_total, Blacksmith.class, imgs);
   }

   protected Pikachu transform(WorldModel world)
   {
      return new PikachuNotFull(getName(), getPosition(), getRate(),
              getAnimationRate(),  getResourceLimit(), getResourceTotal(), getImages());
   }

   protected boolean move(WorldModel world, WorldEntity smith) {
      if (smith == null) {
         return false;
      }

      if (adjacent(getPosition(), smith.getPosition()))
      {
         setResourceCount(0);
         return true;
      }

      else
      {
         world.moveEntity(this, nextPosition(world, this.getPosition(), smith.getPosition()));
         return false;
      }
   }
}