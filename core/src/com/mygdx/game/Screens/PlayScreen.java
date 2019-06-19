package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainPlayer;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Goomba;
import com.mygdx.game.Sprites.Pranay;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldCollisionListener;

public class PlayScreen implements Screen
{
    private Pranay pranay;

    private MainPlayer game;

    private OrthographicCamera gameCam; // follows along with the world and what the viewport actually displays
    private Viewport gamePort; //
    private Hud hud;

    private TmxMapLoader mapLoader; //loads our map into the game
    private TiledMap map;  //reference to the map itself
    private OrthogonalTiledMapRenderer renderer; // what renders the map to the screen

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer; //gives us a graphical representation of items in this "box2d world"
    private B2WorldCreator creator;

    private TextureAtlas atlas;

    public PlayScreen(MainPlayer game)
    {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MainPlayer.V_WIDTH, MainPlayer.V_HEIGHT, gameCam);
        // adds black bars to the sides of the screen to maintain the aspect ratio of whatever device we
        //are on, so height maxes out and then the width does too based on ratio, and if space remains on the sides black boxes are added
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true); //vector for gravity, sleep objects at rest
        box2DDebugRenderer = new Box2DDebugRenderer();

        atlas = new TextureAtlas("Real_Stuff.pack");

        world.setContactListener(new WorldCollisionListener());


    }

    public void initializePranay()
    {
        pranay = new Pranay( this);
    }

    public void intializeCreator()
    {
        creator = new B2WorldCreator(this);
    }

    public TextureAtlas getAtlas()
    {
       return atlas;
    }
    @Override
    public void show()
    {

    }

    public void handleInput(float time)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            pranay.body.applyLinearImpulse(new Vector2(0, 15f), pranay.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && pranay.body.getLinearVelocity().x <= 15)
            pranay.body.applyLinearImpulse(new Vector2(1f, 0), pranay.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && pranay.body.getLinearVelocity().x >= -15)
            pranay.body.applyLinearImpulse(new Vector2(-1f, 0), pranay.body.getWorldCenter(), true);
            //handles keyinput stuff
   }

    public void update(float time)
   {
       handleInput(time);

       world.step(1/10f, 6, 2);
       //resolution speed

       pranay.update(time);\
       //calls the update method in pranay

       for(Enemy enemy: creator.getGoombas())
       {
            enemy.update(time);
       }
       //adds enemies to the world

       hud.update(time);
       //updates the world

       gameCam.position.x = pranay.body.getPosition().x;
       //sets the position of the camera relative to the center of the body or Pranay you can say

       gameCam.update();
       //updates the camera
       
       renderer.setView(gameCam);
       //renders the map to the screen
    }

    @Override
    public void render(float delta)
    {
      update(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1); //takes out the color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // actually clears the screen

      renderer.render();
      //actually displays the map
       
      box2DDebugRenderer.render(world, gameCam.combined);

      game.batch.setProjectionMatrix(gameCam.combined); //only what the game can see
      game.batch.begin();
      pranay.draw(game.batch);
      //draws pranay to the screen

        for(Enemy enemy: creator.getGoombas())
        {
            enemy.draw(game.batch);
            //activates the goomba when you are a certain distance from that goomba
            //that is why setActive is false in the Enemy class
            if(enemy.getX() < pranay.getX() + 224)
                enemy.body.setActive(true);
        }
        //actually puts the goombas on the screen

      game.batch.end();

      game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
      //tell the batch where our camera is on the game world and only render what the camera can see
      
      hud.stage.draw();
      //draws the hud 
    }
    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
        //changes the size of the viewPort when screen changes
    }

    public TiledMap getMap()
    {
        return map;
    }

    public World getWorld()
    {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
        //dispose all this stuff to save memory
    }
}
