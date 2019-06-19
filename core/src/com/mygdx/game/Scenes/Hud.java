package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainPlayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;

public class Hud implements Disposable
{

    public Stage stage;
    //an empty box basically that you can put stuff in
    private Viewport viewport; //we want hud locked to screen so the world can move independently
    // a new camera for our hud and the world can move around independently on its own
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private Label countDownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;

    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MainPlayer.V_WIDTH, MainPlayer.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table(); //to provide organization for the labels inside our stage
        table.top(); //align at the top of the stage
        table.setFillParent(true); // table is the size of the stage


        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1 - 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("Mario", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //bitmapfont is just like a 8-bit type texture on the text
        
        table.add(marioLabel).expandX().padTop(10); // sticks the labels at the top of the screen
        //10 pixels in between each label that is what padTop does
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table); //adds this table to the stage
    }

    public void update(float time)
    {
        timeCount += time;
        if(timeCount >= 1)
        {
            worldTimer--;
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
            //decreases the time label by 1 every 1 second
        }
    }

    public static void addScore(int val)
    {
        score += val;
        scoreLabel.setText(String.format("%06d", score));
        //called so that when something happens it adds to the score
    }

    @Override
    public void dispose()
    {
        stage.dispose();
        //gets rid of the stage so that it doesn't take up extra space in memory
    }
}
