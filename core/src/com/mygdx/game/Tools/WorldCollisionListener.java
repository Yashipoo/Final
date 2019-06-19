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

        int collisionDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;


        if(fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head")
        {
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture obj = head == fixtureA ? fixtureB : fixtureA;

            if(obj.getUserData() != null && TileObject.class.isAssignableFrom(obj.getUserData().getClass()))
            {//returns true if user data is TileObject
                ((TileObject)obj.getUserData()).onHeadHit();
            }
        }

        switch (collisionDef)
        {
            case MainPlayer.ENEMY_HEAD_BIT | MainPlayer.MARIO_BIT:
                if(fixtureA.getFilterData().categoryBits == MainPlayer.ENEMY_HEAD_BIT)
                    ((Enemy)fixtureA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixtureB.getUserData()).hitOnHead();
                break;
            case MainPlayer.ENEMY_BIT | MainPlayer.OBJECT_BIT:
                if(fixtureA.getFilterData().categoryBits == MainPlayer.ENEMY_BIT)
                    ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);
                break;
            case MainPlayer.MARIO_BIT | MainPlayer.ENEMY_BIT:
                Gdx.app.log("Mario", "Died");
                break;
            case MainPlayer.ENEMY_BIT | MainPlayer.ENEMY_BIT:
                ((Enemy)fixtureA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixtureB.getUserData()).reverseVelocity(true, false);
                break;
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
