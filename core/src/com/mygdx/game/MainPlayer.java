package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

//The main game class, what it does is that it keeps the render loop over and over.
//it doesn't handle the whats inside, it delegates that action to a screen
//A screen will execute logic and draws things to the screen

public class MainPlayer extends Game
{
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208; // virtual width and height for our game
    public SpriteBatch batch; //Container that holds our images, very memory intensive so we only need one in the game
    // public static final float PPM = 10;


    public static final short GROUND_BIT = 1;
    public static final short MARIO_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short ENEMY_HEAD_BIT = 128;

    private PlayScreen playScreen;


    @Override
    public void create ()
    {
        batch = new SpriteBatch();
        playScreen = new PlayScreen(this);
        playScreen.initializePranay(); 
        //did this to resolve a nullpointer that was thrown when trying to draw pranay to the screen
        
        playScreen.intializeCreator();
        //did this to resolve a nullpointer that was thrown when trying to load all the goombas to the map
        
        setScreen(playScreen);// Pass the game itself so that it can set screens in the future

    }
    @Override
    public void render ()
    {
        super.render();
        // delegate the render method to the PlayScreen or whatever screen is active
    }
    @Override
    public void dispose ()
    {
        batch.dispose();
        //gets rid of the batch as a batch is very, very memory intensive
    }
}
