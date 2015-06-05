import processing.core.PImage;
import java.util.List;

public class Mew
        extends MobileAnimatedActor
{
   private static final int QUAKE_ANIMATION_RATE = 100;
   public Mew(String name, Point position, int rate, int animation_rate,
              List<PImage> imgs, WorldModel world)
   {
      super(name, position, rate, animation_rate, imgs);
   }

   protected boolean canPassThrough(WorldModel world, Point pt)
   {
      return !world.isOccupied(pt) || world.getTileOccupant(pt) instanceof RareCandy;
   }

   private boolean move(WorldModel world, WorldEntity target)
   {
      if (target == null)
      {
         return false;
      }

      if (adjacent(getPosition(), target.getPosition()))
      {
         target.remove(world);
         return true;
      }
      else
      {
         Point new_pt = nextPosition(world, this.getPosition(), target.getPosition());
         WorldEntity old_entity = world.getTileOccupant(new_pt);
         if (old_entity != null && old_entity != this)
         {
            old_entity.remove(world);
         }
         world.moveEntity(this, new_pt);
         return false;
      }
   }

   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);

         WorldEntity target = world.findNearest(getPosition(), Meowth.class);
         long nextTime = ticks + getRate();

         if (target != null)
         {
            Point tPt = target.getPosition();

            if (move(world, target))
            {
               Quake quake = createQuake(world, tPt, ticks, imageStore);
               world.addEntity(quake);
               nextTime = nextTime + getRate();
            }
         }

         scheduleAction(world, this, createAction(world, imageStore),
                 nextTime);

      };
      return action[0];
   }

   private Quake createQuake(WorldModel world, Point pt, long ticks,
                             ImageStore imageStore)
   {
      Quake quake = new Quake("quake", pt, QUAKE_ANIMATION_RATE,
              imageStore.get("quake"));
      quake.schedule(world, ticks, imageStore);
      return quake;
   }
}