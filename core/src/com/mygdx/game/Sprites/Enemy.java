package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite
{
    protected World world;
    protected PlayScreen screen;

    public Body body;

    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);

        defEnemy();

        velocity = new Vector2(10, 0);
        //speed of the enemy
        
        //body.setActive(false);
    }

    protected abstract void defEnemy();
    public abstract void hitOnHead();
    public abstract void update(float time);

    public void reverseVelocity(boolean x, boolean y)
    {
        if(x)
            //if true change x direction
            velocity.x = - velocity.x;
        if(y)
            //if true change y direction
            velocity.y = -velocity.y;
        //if an enemy collides with something, change its direction of movement
    }


}
