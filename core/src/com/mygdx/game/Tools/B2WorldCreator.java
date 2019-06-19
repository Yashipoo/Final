package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Brick;
import com.mygdx.game.Sprites.Coin;
import com.mygdx.game.Sprites.Goomba;
import com.mygdx.game.Sprites.Pranay;

public class B2WorldCreator
{
    private Array<Goomba> goombas;

    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //Ground
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            //tells us what object layer we are working with
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
            //creates a box around each ground object in box2D
            
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
            //create it and add it to the world
        }

        //Pipes
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = MainPlayer.OBJECT_BIT;
            body.createFixture(fixtureDef);
        }

        //Brick
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(screen, rect);
        }

        //Coin
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(screen, rect);
        }

        //goombas
        goombas = new Array<Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX(), rect.getY()));
        }
    }

    public Array<Goomba> getGoombas()
    {
        return goombas;
    }
}
