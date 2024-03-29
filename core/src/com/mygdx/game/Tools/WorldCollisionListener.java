package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.TileObject;

//A contact listener is what gets called when two things in box2d collide

public class WorldCollisionListener implements ContactListener
{

    @Override
    public void beginContact(Contact contact) //gets called when two things begin to collide
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //defines what the two things are that are colliding

        int collisionDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
        //either A or B

        if(fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head")
        {
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture obj = head == fixtureA ? fixtureB : fixtureA;

            if(obj.getUserData() != null && TileObject.class.isAssignableFrom(obj.getUserData().getClass()))
            {
            //returns true if user data is TileObject
                ((TileObject)obj.getUserData()).onHeadHit();
            }
        }

        switch (collisionDef)
        {
            case MainPlayer.ENEMY_HEAD_BIT | MainPlayer.PRANAY_BIT:
                if(fixtureA.getFilterData().categoryBits == MainPlayer.ENEMY_HEAD_BIT)
                    ((Enemy)fixtureA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixtureB.getUserData()).hitOnHead();
                break;
                //tests the collision between Pranay jumping on an enemy
                
            case MainPlayer.ENEMY_BIT | MainPlayer.OBJECT_BIT:
                if(fixtureA.getFilterData().categoryBits == MainPlayer.ENEMY_BIT)
                    ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);
                break;
                //Tests collisions between Pranay and all objects on the map
                
            case MainPlayer.PRANAY_BIT | MainPlayer.ENEMY_BIT:
                Gdx.app.log("Pranay", "Died");
                break;
                //If pranay collides with an enemy, and not their head, a message pops up saying that he died
                
            case MainPlayer.ENEMY_BIT | MainPlayer.ENEMY_BIT:
                ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);
                break;
                //If two enemies collide with each other, change their directions of movement
        }


    }

    @Override
    public void endContact(Contact contact) //when those two things begin to leave each others contact
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) //allows us to change the characteristics of that collision
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) //Gives us what happened due to that collision
    {

    }
}
