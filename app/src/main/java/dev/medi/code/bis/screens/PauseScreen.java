package dev.medi.code.bis.screens;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
//import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import dev.medi.code.bis.config.Assets;
//import dev.medi.code.bis.config.Runner;
//import dev.medi.code.bis.game.calibrate.Accelerometer;
import dev.medi.code.bis.game.control.Button;
import dev.medi.code.bis.game.control.ButtonDelegate;
import dev.medi.code.bis.game.interfaces.PauseDelegate;
//import dev.medi.code.bis.game.objects.Meteor;

public class PauseScreen extends CCLayer implements ButtonDelegate {

    private Button resumeButton;
    private Button quitButton;

    private PauseDelegate delegate;

    private CCColorLayer background;

    // Class Constructor
    public PauseScreen() {

        //********** General Settings **********//
        // Enable Touch
        this.setIsTouchEnabled(true);

//        Accelerometer.sharedAccelerometer().enableSensor(false);
        //**************************************//

        //********** Specific Configurations **********//
        // Adds background
        this.background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175),
                screenWidth(), screenHeight());
        this.addChild(this.background);

        // Adds Logo
        CCSprite title = CCSprite.sprite(Assets.LOGO);
        title.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2, screenHeight() - 130)));
        this.addChild(title);

        // Add Buttons
        this.resumeButton = new Button(Assets.PLAY);
        this.quitButton = new Button(Assets.EXIT);
        this.resumeButton.setDelegate(this);
        this.quitButton.setDelegate(this);
        this.addChild(this.resumeButton);
        this.addChild(this.quitButton);

        // Position Buttons
        this.resumeButton.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2, screenHeight() - 250)));
        this.quitButton.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2, screenHeight() - 300)));
        //*********************************************//

    }

    public void setDelegate(PauseDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void buttonClicked(Button sender) {

        // Check Resume Button touched [Verifica se o botão foi pressionado]
        if (sender.equals(resumeButton)) {
//            Accelerometer.sharedAccelerometer().enableSensor(true);
            this.delegate.resumeGame();
            this.removeFromParentAndCleanup(true);
        }

        // Check Quit Button touched [Verifica se o botão foi pressionado]
        if (sender == this.quitButton) {
//            SoundEngine.purgeSharedEngine();
            this.delegate.quitGame();
//            this.removeFromParentAndCleanup(true);
        }

    }

}
