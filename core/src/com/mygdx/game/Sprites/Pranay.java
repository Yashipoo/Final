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
        //calls the pack, then findRegion finds the area that is encompassed by the pranaySprite
        
        
        this.world = screen.getWorld();
        defPranay();

        pranayStill = new TextureRegion(getTexture(), 817, 1, 83, 120);
        //tells us exactly where this region is in the pack
        
        setBounds(0, 0, 50, 50);
        //basically says how big do we want our sprite to be in the "hitbox"
        
        setRegion(pranayStill);
        //sets the region to the pranay image


    }

    public void defPranay()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 ,32 );
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //Pranay spawns at at that location on the map. A Dynamic Body allows the this "body" to interact with its surroundings
        //such as bounce off certain things

        body = world.createBody(bodyDef);
        //generates the body to the world
        
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15);
        //This is essentially the hitbox or image that the sprite will follow throughout the world. Think of it like we glue the sprite
        //to this circle and the circle is the thing that is moving
        
        fixtureDef.filter.categoryBits = MainPlayer.PRANAY_BIT;
        fixtureDef.filter.maskBits = MainPlayer.GROUND_BIT | MainPlayer.COIN_BIT | MainPlayer. BRICK_BIT
            | MainPlayer.ENEMY_BIT | MainPlayer.OBJECT_BIT | MainPlayer.ENEMY_HEAD_BIT;
        //this defines what exactly Pranay can collide with. 

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        //We have this hypothetical body and we have a circle. Paste the circle on the body. 
        
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2,15), new Vector2(2, 15));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        //Its creating a line over the sprite. I hit monitor for Pranays head. This is used for tracking if pranays head hit a block
        
        body.createFixture(fixtureDef).setUserData("head");
        //Put the line on the body
        
    }

   public void update(float time)
   {
       //What happened was that when the sprite was loaded to the screen, it was not on the ground. It was hovering over the ground. But
       //you could still move it, because the circle was on the ground.
       setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getWidth() * 2 / 3 );
       //What this does is that it sets the postion of the sprite on the circle, so that the sprite is "touching" the ground
   }


}
