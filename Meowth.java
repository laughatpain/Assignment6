import processing.core.PImage;

import java.util.List;

/**
 * Created by Patrick on 6/3/2015.
 */
public class Meowth extends MobileAnimatedActor
{
    private static final int QUAKE_ANIMATION_RATE = 100;
    public Meowth(String name, Point position, int rate, int animation_rate,
                   List<PImage> imgs, WorldModel world)
    {
        super(name, position, rate, animation_rate, imgs);
    }

    protected boolean canPassThrough(WorldModel world, Point pt)
    {
        return !world.isOccupied(pt);
    }

    private int setRate (int rate)
    {
        return this.rate = rate;
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
            world.moveEntity(this, nextPosition(world, this.getPosition(), target.getPosition()));
            return false;
        }
    }

    public Action createAction(WorldModel world, ImageStore imageStore)
    {
        Action[] action = { null };
        action[0] = ticks -> {
            removePendingAction(action[0]);

            WorldEntity target = world.findNearest(getPosition(), Pikachu.class);
            WorldEntity teamRocket = world.findNearest(getPosition(), Jesse.class);
            WorldEntity teamRocket2 = world.findNearest(getPosition(), James.class);
            long nextTime = ticks + getRate();

            if (target != null )
                {
                    Point tPt = target.getPosition();
                    if (move(world, target))
                    {

                        Quake quake = createQuake(world, tPt, ticks, imageStore);
                        world.addEntity(quake);
                        nextTime = nextTime + getRate();
                    }
                }
            if (teamRocket != null || teamRocket2 != null)
            {
                speedUp(world, teamRocket, teamRocket2);
            }

            scheduleAction(world, this, createAction(world, imageStore),
                    nextTime);
        };
        return action[0];
    }

    public Quake createQuake(WorldModel world, Point pt, long ticks,
                              ImageStore imageStore)
    {
        Quake quake = new Quake("quake", pt, QUAKE_ANIMATION_RATE,
                imageStore.get("quake"));
        quake.schedule(world, ticks, imageStore);
        return quake;
    }

    public void speedUp (WorldModel world, WorldEntity jesse, WorldEntity james) {

        if (jesse != null) {
            if (getPosition().distanceTo(jesse.getPosition()) < 5) {
                setRate(1200);
            }
        }
        if (james != null)
        {

            if (getPosition().distanceTo(james.getPosition()) < 5)
            {
                setRate(1200);
            }
        }
        if (james != null  && jesse != null)
        {
            if (getPosition().distanceTo(jesse.getPosition()) < 5 || getPosition().distanceTo(james.getPosition()) < 5) {
                setRate(1200);
            }
            if (getPosition().distanceTo(james.getPosition()) < 8 && getPosition().distanceTo(jesse.getPosition()) < 8) {
                setRate(800);
            } else {
                setRate(1500);
            }
        }
        setRate(1500);
    }
}