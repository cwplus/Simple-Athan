package com.me.Azan2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Azan extends Game {
	public OrthographicCamera camera;
	public SpriteBatch batch;
	public Texture texture;
	public Splash splash;
	public BitmapFont font;
	public float w;
	public float h;
	
	@Override
	public void create() {		
		this.w = Gdx.graphics.getWidth();
		this.h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		splash = new Splash(this);
		
		this.setScreen(splash);
	
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		font.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
