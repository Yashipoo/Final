package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

public class Brick extends TileObject
{
    public Brick(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MainPlayer.BRICK_BIT);
        //what object we are working with
    }

    @Override
    public void onHeadHit()
    {
        Gdx.app.log("Brick", "Collision");
        //so that a message is sent saying that a collision with a brick has occurred
        
        setCategoryFilter(MainPlayer.DESTROYED_BIT);
        //Since a collision has occurred we want the destroyed brick
        
        getCell().setTile(null);
        //by setting this to null we are saying that we want nothing in that cell, so the cell is empty and the brick is destroyed
        
        Hud.addScore(200);
        //you gett 200 points for breaking a brick
    }
}
