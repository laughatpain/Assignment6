import processing.core.PImage;
import java.util.List;
/**
 * Created by Patrick on 6/4/2015.
 */
public class Thunderbolt
extends AnimatedActor{
    private static final int LIGHTNING_DURATION = 1100;
   private static final int QUAKE_STEPS = 10;

   public Thunderbolt(String name, Point position, int animation_rate,
      List<PImage> imgs)
   {
      super(name, position, LIGHTNING_DURATION, animation_rate, imgs);
   }

   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);
         remove(world);
      };
      return action[0];
   }

   protected void scheduleAnimation(WorldModel world)
   {
      Actor.scheduleAction(world, this,
         createAnimationAction(world, QUAKE_STEPS), getAnimationRate());
   }
    public void lightningRemove ()
    {

    }
}
