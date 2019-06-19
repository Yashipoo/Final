package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Goomba extends Enemy
{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    private boolean setToDestroy;
    private boolean destroyed;

    public Goomba(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("%2B1Goomb"), i * 54, 1, 100, 100));
        }
        //adds one texture to the arraylist

        walkAnimation = new Animation(0.09f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 100, 100);
        //Was created to do an animation between two images but loading one of the images caused a nullPointer so ignored that image

        setToDestroy = false;
        destroyed = false;
        //Is the goomba under destruction and is it already destroyed
    }

    public void update(float time)
    {
        stateTime += time;
        //adds how long its in a state
        
        if(setToDestroy && !destroyed)
        {
            destroyed = true;
            setPosition(body.getPosition().x - getWidth() * 2 / 3, body.getPosition().y - getWidth() * 8 / 9);
            setRegion(new TextureRegion(screen.getAtlas().findRegion("%2B1Goomb"), 1, 1, 100, 100));
            //If the images is getting destroyed and its not destroyed, get its location and destroy that body and therefore anything
            //on top of that body
        
            world.destroyBody(body);
            //destroy it
        }


        else if(!destroyed)
        {
            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth() * 2 / 3, body.getPosition().y - getWidth() * 8 / 9);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
            //if it is not destroyed, move
        }
    }

    @Override
    public void defEnemy()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() , getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //create the hypothetical body
        
        body = world.createBody(bodyDef);
        //add it to the world
        
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);
        //add a circle to it
        
        fixtureDef.filter.categoryBits = MainPlayer.ENEMY_BIT;
        fixtureDef.filter.maskBits = MainPlayer.ENEMY_BIT | MainPlayer.COIN_BIT | MainPlayer. BRICK_BIT
        | MainPlayer.ENEMY_BIT | MainPlayer.OBJECT_BIT | MainPlayer.PRANAY_BIT | MainPlayer.GROUND_BIT;
        //what an enemy can collide with

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
        //add that to the world

        //creates the "head" of the goomba here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 10).scl(1);
        vertice[1] = new Vector2(5, 10).scl(1);
        vertice[2] = new Vector2(-3, 3).scl(1);
        vertice[3] = new Vector2(3, 3).scl(1);
        head.set(vertice);
        //makes a trapeszoid where the longerside is on top
        
        fixtureDef.shape = head;
        fixtureDef.restitution = 2f;
        fixtureDef.filter.categoryBits = MainPlayer.ENEMY_HEAD_BIT;
        //this sets this trapezoid to a bit so that we can do collisons with it
        
        body.createFixture(fixtureDef).setUserData(this);
        // so that we can have access to this class from our collision handling
    }

    @Override
    public void hitOnHead()
    {
        setToDestroy = true;
        //because this enemy was hit on the head, we mark it for destruction
        
        Hud.addScore(100);
        //if you jump on top of the enemy, add 100 points to your score
    }
}
