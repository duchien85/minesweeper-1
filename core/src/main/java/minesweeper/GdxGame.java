package minesweeper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GdxGame extends ApplicationAdapter {
	// private static final int WIDTH = 30, HEIGHT = 16, NUM_MINES = 99;
	private static final int WIDTH = 30, HEIGHT = 16, NUM_MINES = 50;
	// private static final int WIDTH = 10000, HEIGHT = 10000, NUM_MINES = 20000000;

	private SpriteBatch batch;
	private Stage stage;
	private Skin skin;
	private Label label;

	@Override
	public void create() {
		Gdx.graphics.setContinuousRendering(false);

		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Texture background = new Texture("background.png");
		table.setBackground(new TextureRegionDrawable(new TextureRegion(background)));

		// Board board = Board.generate(WIDTH, HEIGHT, NUM_MINES);
		Board board = new FastBoardBuilder(WIDTH, HEIGHT, NUM_MINES).build();

		BoardRenderer boardRenderer = new BoardRenderer(stage, skin, board);
		table.add(boardRenderer).expand().fill().row();
		stage.setScrollFocus(boardRenderer);

		label = new Label("Hello", skin);
		table.add(label);

		table.pack();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		label.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		skin.dispose();
	}
}
