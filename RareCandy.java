import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class RareCandy
        extends Actor
{
   private static final int DEFAULT_RATE = 5000;
   private static final int BLOB_RATE_SCALE = 4;
   private static final int BLOB_ANIMATION_RATE_SCALE = 50;
   private static final int BLOB_ANIMATION_MIN = 1;
   private static final int BLOB_ANIMATION_MAX = 3;

   private static final Random rand = new Random();

   public RareCandy(String name, Point position, int rate, List<PImage> imgs)
   {
      super(name, position, rate, imgs);
   }

   public String toString()
   {
      return String.format("ore %s %d %d %d", this.getName(),
              this.getPosition().x, this.getPosition().y, this.getRate());
   }

   public Action createAction(WorldModel world, ImageStore imageStore)
   {
      Action[] action = { null };
      action[0] = ticks -> {
         removePendingAction(action[0]);
         Mew blob = createBlob(world, getName() + " -- blob",
                 getPosition(), getRate() / BLOB_RATE_SCALE, ticks, imageStore);

         remove(world);
         world.addEntity(blob);
      };
      return action[0];
   }

   private static Mew createBlob(WorldModel world, String name,
                                     Point pt, int rate, long ticks, ImageStore imageStore)
   {
      Mew blob = new Mew(name, pt, rate,
              BLOB_ANIMATION_RATE_SCALE * (BLOB_ANIMATION_MIN +
                      rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN)),
              imageStore.get("blob"), world);
      blob.schedule(world, ticks, imageStore);
      return blob;
   }
}