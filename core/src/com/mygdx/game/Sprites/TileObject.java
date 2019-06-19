package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.PlayScreen;

public abstract class TileObject
{
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public TileObject(PlayScreen screen, Rectangle bounds)
    {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        //creates this hypothetical body

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
        //This is saying that the we want blocks to stay still. We do not want them moving around otherwise the map would be destroyed

        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth() / 2, bounds.getHeight() / 2);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        //Sets boxes around all ground objects which will allow us to do collision detection with ground objects, the ground itself, 
        //Pranay and enemies
        
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short bit)
    {
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
        //what bit, or what type of collision is happening
    }

    public TiledMapTileLayer.Cell getCell()
    {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        //You have layer is Tiled. Layer 1 is the graphics layer
        //6 - goomba object layer
        //5 - bricks object layer
        //4 - coins object layer
        //3 - pipes object layer
        //2 - ground object layer
        //1 - graphics tile layer
        //0 - background tile layer
        
        return layer.getCell((int) (body.getPosition().x / 16), (int) (body.getPosition().y / 16));
        //The application tiled allows you to put tiles into these squares called cells. This is returning what cell the player is in
    }


}
