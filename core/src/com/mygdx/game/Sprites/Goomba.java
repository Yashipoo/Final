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

        walkAnimation = new Animation(0.09f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 100, 100);

        setToDestroy = false;
        destroyed = false;
    }

    public void update(float time)
    {
        stateTime += time;

        if(setToDestroy && !destroyed)
        {
            destroyed = true;
            setPosition(body.getPosition().x - getWidth() * 2 / 3, body.getPosition().y - getWidth() * 8 / 9);
            setRegion(new TextureRegion(screen.getAtlas().findRegion("%2B1Goomb"), 1, 1, 100, 100));

            world.destroyBody(body);
        }


        else if(!destroyed)
        {
            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth() * 2 / 3, body.getPosition().y - getWidth() * 8 / 9);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    public void defEnemy()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() , getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);
        fixtureDef.filter.categoryBits = MainPlayer.ENEMY_BIT;
        fixtureDef.filter.maskBits = MainPlayer.ENEMY_BIT | MainPlayer.COIN_BIT | MainPlayer. BRICK_BIT
        | MainPlayer.ENEMY_BIT | MainPlayer.OBJECT_BIT | MainPlayer.MARIO_BIT | MainPlayer.GROUND_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        //creates the "head" of the goomba here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 10).scl(1);
        vertice[1] = new Vector2(5, 10).scl(1);
        vertice[2] = new Vector2(-3, 3).scl(1);
        vertice[3] = new Vector2(3, 3).scl(1);
        head.set(vertice);

        fixtureDef.shape = head;
        fixtureDef.restitution = 2f;
        fixtureDef.filter.categoryBits = MainPlayer.ENEMY_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        // so that we can have access to this class from our collision handling
    }

    @Override
    public void hitOnHead()
    {
        setToDestroy = true;
        Hud.addScore(100);
    }
}
