package dev.medi.code.bis.game.scenes;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.game.calibrate.Accelerometer;
import dev.medi.code.bis.game.control.Button;
import dev.medi.code.bis.game.control.ButtonDelegate;
import dev.medi.code.bis.game.objects.Meteor;
import dev.medi.code.bis.screens.ScreenBackground;

public class GameOverScreen extends CCLayer implements ButtonDelegate {

    private ScreenBackground background;
    private Button beginButton;

    // Settings
    private boolean soundStatus;
    private boolean effectStatus;

    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);

        return scene;
    }

    // Standard Class Constructor
    public GameOverScreen() {

    }

    // Class Constructor
    public GameOverScreen(boolean soundStatus) {

        //********** General Settings **********//
        // Enable Touch
        this.setIsTouchEnabled(true);

        Accelerometer.sharedAccelerometer().enableSensor(false);
        //**************************************//

        //********** Specific Configurations **********//
        // Assigning Parameters Received
        this.soundStatus = soundStatus;

        // Background
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2.0f, screenHeight() / 2.0f
        )));

        this.addChild(this.background);

        // Image
        CCSprite title = CCSprite.sprite(Assets.GAMEOVER);
        title.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2, screenHeight() - 130
        )));

        this.addChild(title);

//        // Enable Touch
//        this.setIsTouchEnabled(true);

        this.beginButton = new Button(Assets.PLAY);
        this.beginButton.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2, screenHeight() - 300
        )));

        this.beginButton.setDelegate(this);
        addChild(this.beginButton);
        //*********************************************//
    }

    @Override
    public void buttonClicked(Button sender) {
        if (sender.equals(this.beginButton)) {
            SoundEngine.sharedEngine().pauseSound();
            SoundEngine.purgeSharedEngine();

            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(0.8f, new TitleScreen(this.soundStatus).scene())
            );
        }
    }
}
