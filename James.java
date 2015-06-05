import processing.core.PImage;

import java.util.List;

/**
 * Created by Patrick on 6/4/2015.
 */
public class James
        extends AnimatedActor
{
    private static final int DEFAULT_DISTANCE = 1;
    private int resourceDistance;

    public James(String name, Point position, int rate, int resourceDistance, int animationRate,
                 List<PImage> imgs)
    {
        super(name, position, rate, animationRate, imgs);
        this.resourceDistance = resourceDistance;
    }
    public String toString()
    {
        return String.format("vein %s %d %d %d %d", this.getName(),
                this.getPosition().x, this.getPosition().y, this.getRate());
    }

    public Action createAction(WorldModel world, ImageStore imageStore)
    {
        Action[] action = { null };
        action[0] = ticks -> {
            removePendingAction(action[0]);

            Point openPt = findOpenAround(world, getPosition(), resourceDistance);
            if (openPt != null)
            {
                Meowth meowth = createMeowth(world, "meowth - " + getName() + " - " + ticks,
                        openPt, ticks, imageStore);
                world.addEntity(meowth);
            }

            scheduleAction(world, this, createAction(world, imageStore),
                    ticks + getRate());
        };
        return action[0];
    }

    private Meowth createMeowth(WorldModel world, String name, Point pt,
                                long ticks, ImageStore imageStore)
    {
        Meowth meowth = new Meowth(name, pt, 500, getAnimationRate(), imageStore.get("meowth"),world);
        meowth.schedule(world, ticks, imageStore);
        return meowth;
    }

    private Point findOpenAround(WorldModel world, Point pt, int distance) {
        Point bottomRight = new Point(pt.x + 4, pt.y + 4);
        if (world.withinBounds(bottomRight) && (!(world.isOccupied(bottomRight)))) {
            return bottomRight;
        }
        Point topLeft = new Point(pt.x, pt.y);
        if (world.withinBounds(topLeft) && (!(world.isOccupied(topLeft)))) {
            return topLeft;
        }
        Point bottomLeft = new Point(pt.x, pt.y + 4);
        if (world.withinBounds(bottomLeft) && (!(world.isOccupied(bottomLeft)))) {
            return bottomRight;
        }
        Point topRight = new Point(pt.x + 4, pt.y);
        if (world.withinBounds(topRight) && (!(world.isOccupied(topRight)))) {
            return bottomRight;
        }
        return null;
    }

}
