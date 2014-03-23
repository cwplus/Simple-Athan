package com.me.Azan2;

import java.util.Calendar;
import java.util.Locale;

import tweens.ActorAccessor;
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
//import com.google.gson.Gson;

public class PrayerTimes implements Screen
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
	private Calendar calendar = Calendar.getInstance();
	private Locale locale = Locale.getDefault();
	
	private Label today;
	private Label yesterday;
	private Label tomorrow;
	private TodayPrayerList tpl;
	//private Gson gson = new Gson();
	
	private String[] salaatTimes = {};
	
	
	
	public PrayerTimes(Azan azan) 
	{
		
		this.azan = azan;
		this.font = azan.font;
		this.batch = azan.batch;
		this.camera = new OrthographicCamera();
		this.font.setColor(Color.BLACK);
		this.font.setScale(3.0f);
		
		backgroundGradient = new ShapeRenderer();
		
		
	}

	/**
	 * View
	 */
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		update();
		
		batch.begin();
		
		
		batch.end();
		
		backgroundGradient.begin(ShapeType.Filled);
		backgroundGradient.rect(0, 0, azan.w, azan.h, Color.valueOf("234567"), Color.BLACK, Color.CYAN, Color.BLACK);
		backgroundGradient.end();
		
		table.debug();
		table.debugTable();
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
		
		
		if(Gdx.input.isTouched())
		{
			azan.setScreen(new PrayerTimes(azan));
		}
		
		tweenManager.update(delta);
	}

	/**
	 * Model
	 */
	private void update() 
	{
		today.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale));
//		yesterday.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale));
//		tomorrow.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale));
		
	}

	@Override
	public void resize(int width, int height) 
	{
		
		
	}

	@Override
	public void show() 
	{	
		stage = new Stage();

		//getting the salaat times for today
		try {
			tpl = new TodayPrayerList(String.valueOf(Calendar.DAY_OF_WEEK_IN_MONTH));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		salaatTimes = tpl.getSalaatTimes();
		
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

		table = new Table(skin);
		table.setFillParent(true);
	
		// creating heading
		Label heading = new Label("Prayer Time", skin);
		
		yesterday = new Label("one", skin, "default");
		today = new Label("two", skin, "default");
		tomorrow = new Label("thr", skin, "default");
		
		today.setFontScale(2f);
		
		Label labelFajr = new Label("Fajr", skin, "default");
		Label timeFajr = new Label(salaatTimes[0], skin, "default");
		
//		Label labelSunrise = new Label("Sunrise", skin, "default");
//		Label sunriseTime = new Label("50 min", skin, "default");
		
		Label labelDhuhr = new Label("Dhuhr", skin, "default");
		Label timeDhuhr = new Label(salaatTimes[1], skin, "default");
		
		Label labelAsr = new Label("Asr", skin, "default");
		Label timeAsr = new Label(salaatTimes[2], skin, "default");
		
//		Label labelSunset = new Label("Sunset", skin, "default");
//		Label sunsetTime = new Label("50 min", skin, "default");
		
		Label labelMagrib = new Label("Maghrib", skin, "default");
		Label timeMaghrib = new Label(salaatTimes[3], skin, "default");
		
		Label labelIsha = new Label("Isha", skin, "default");
		Label timeIsha = new Label(salaatTimes[4], skin, "default");
		
		Label donate = new Label("Donate", skin, "default");
		

		

		// putting stuff together
		table.size(azan.w, azan.h);
		table.add(heading).center().top().expandY();
		table.row();
		
		//table.add(yesterday);
		table.add(today).center().expandY();
		//table.add(tomorrow);
		
		table.row();
		
		table.add(labelFajr).left().expandX();
		table.add(timeFajr).right();
		table.row();
		table.add(labelDhuhr).left();
		table.add(timeDhuhr).right();
		table.row();
		table.add(labelAsr).left();
		table.add(timeAsr).right();
		table.row();
		table.add(labelMagrib).left();
		table.add(timeMaghrib).right();
		table.row();
		table.add(labelIsha).left();
		table.add(timeIsha).right();
		table.row();
		
		
		
		
		table.add(donate).center().bottom().expandY();
	

		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		// heading and buttons fade-in
//		Timeline.createSequence().beginSequence()
//		.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
//		.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, .75f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

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
