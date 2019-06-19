package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Screens.PlayScreen;


public class Pranay extends Sprite
{
    public World world;
    public Body body;
    private TextureRegion pranayStill;



    public Pranay( PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("pranaySprite"));

        this.world = screen.getWorld();
        defPranay();

        pranayStill = new TextureRegion(getTexture(), 817, 1, 83, 120);
        setBounds(0, 0, 50, 50);
        setRegion(pranayStill);


    }

    public void defPranay()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 ,32 );
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15);
        fixtureDef.filter.categoryBits = MainPlayer.MARIO_BIT;
        fixtureDef.filter.maskBits = MainPlayer.GROUND_BIT | MainPlayer.COIN_BIT | MainPlayer. BRICK_BIT
            | MainPlayer.ENEMY_BIT | MainPlayer.OBJECT_BIT | MainPlayer.ENEMY_HEAD_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2,15), new Vector2(2, 15));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("head");
    }

   public void update(float time)
   {
       setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getWidth() * 2 / 3 );
   }


}
