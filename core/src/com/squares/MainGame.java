package com.squares;

import GameState.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.minlog.Log;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	GameStateManager gsm;

	@Override
	public void create () {
		Log.set(Log.LEVEL_DEBUG);
		gsm = new GameStateManager();
		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
	}
}
