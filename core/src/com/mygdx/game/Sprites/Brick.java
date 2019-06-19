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
    }

    @Override
    public void onHeadHit()
    {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(MainPlayer.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}
