package dev.medi.code.bis.game.objects;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
//import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;
//import org.cocos2d.types.CGPoint;

import dev.medi.code.bis.game.scenes.GameScene;

public class Score extends CCLayer {

	private int score;
	private CCBitmapFontAtlas text;

	private GameScene delegate;

	// Game Status Data
//	private int destroyedMeteors = 0;

	public void setDelegate(GameScene delegate) {
	    this.delegate = delegate;
    }

	// Class Constructor
	public Score() {

	    this.score = 0;

	    this.text = CCBitmapFontAtlas.bitmapFontAtlas(
	            String.valueOf(this.score),
                "UniSansSemiBold_Numbers_240.fnt"
        );

	    this.text.setScale((float) 240 / (240*3));

	    this.setPosition(screenWidth() - 50, screenHeight() - 30);
	    this.addChild(this.text);
    }

    public void increase() {
	    score++;
	    this.text.setString(String.valueOf(this.score));

	    checkPlayerWin();
    }

    public void decrease() {
		score--;
		this.text.setString(String.valueOf(this.score));

		checkPlayerWin();
	}

	public void checkPlayerWin() {
		// Sets the score needed to win the game [Define a pontuação necessária para ganhar o jogo]
		if (score == 100) {
			this.delegate.startFinalScreen();
		}

		// Sets the score needed to Game Over
		if (score < 0) {
			this.delegate.startGameOverScreen();
		}
	}

    public int getScore() {
		return this.score;
	}

}
