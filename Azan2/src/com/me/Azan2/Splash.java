package com.me.Azan2;

import tweens.ActorAccessor;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Splash implements Screen
{

	Azan azan;
	BitmapFont font;
	SpriteBatch batch;
	OrthographicCamera camera;
	
	private Stage stage;
	private Skin skin;
	private Table table;
	private TweenManager tweenManager;
	private ShapeRenderer backgroundGradient;
	
	public Splash(Azan azan) 
	{
		this.azan = azan;
		this.font = azan.font;
		this.batch = azan.batch;
		this.camera = new OrthographicCamera();
		this.font.setColor(Color.BLACK);
		this.font.setScale(3.0f);
		
		backgroundGradient = new ShapeRenderer();
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		
		batch.end();
		
		backgroundGradient.begin(ShapeType.Filled);
		backgroundGradient.rect(0, 0, azan.w, azan.h, Color.WHITE, Color.WHITE, Color.MAGENTA, Color.MAGENTA);
		backgroundGradient.end();
		
		stage.act(delta);
		stage.draw();
		
		if(Gdx.input.isTouched())
		{
			azan.setScreen(new PrayerTimes(azan));
		}
		
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() 
	{	
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

		table = new Table(skin);
		table.setFillParent(true);
	
		// creating heading
		Label heading = new Label("AZAN", skin, "default");

		

		// putting stuff together
		table.add(heading).spaceBottom(100).row();
	

		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
		.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
		.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, 4f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, 4f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());


	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}

}
