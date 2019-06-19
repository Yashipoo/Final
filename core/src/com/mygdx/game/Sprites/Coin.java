package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;


public class Coin extends TileObject
{
    private static TiledMapTileSet set;

    private final int BLANK_COIN_INDEX = 28; 
    //index of the tile in the tileset

    public Coin(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        set = map.getTileSets().getTileSet("tileset");
        //where you got the tiles from 
        
        fixture.setUserData(this); 
        //so that we have access to this specific object, same for brick
        
        setCategoryFilter(MainPlayer.COIN_BIT);
        //What are we working with here. In this case, it is a coin
    }

    @Override
    public void onHeadHit()
    {
        Gdx.app.log("Coin", "Collision");
        //So that we know that a collision has occurred
        
        getCell().setTile(set.getTile(BLANK_COIN_INDEX));
        //this will tell us which coin block did we hit
        
        Hud.addScore(300);
        //since we know that a collision has occurred, add points to your score
    }
}
