package dev.medi.code.bis.game.scenes;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
//import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.config.ConfigurationConstants;
import dev.medi.code.bis.game.calibrate.Accelerometer;
import dev.medi.code.bis.game.control.MenuButtons;
import dev.medi.code.bis.screens.ScreenBackground;

public class TitleScreen extends CCLayer {

    private ScreenBackground background;

//    // Settings
//    private boolean soundStatus = ConfigurationConstants.soundON;
//    private boolean effectsStatus;

    // Standard Class Constructor
    public TitleScreen() {

    }

    // Class Constructor
    public TitleScreen(boolean soundStatus) {

        //********** General Settings **********//
        // Enable Touch
        this.setIsTouchEnabled(true);

        Accelerometer.sharedAccelerometer().enableSensor(false);
        //**************************************//

        //********** Specific Configurations **********//
        // Set up Background
        this.background = new ScreenBackground(Assets.BACKGROUND);
        this.background.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2.0f,
                screenHeight() / 2.0f
        )));
        this.addChild(this.background);

        // Set LOGO Image
        CCSprite title = CCSprite.sprite(Assets.LOGO);
        title.setPosition(screenResolution(CGPoint.ccp(
                screenWidth() / 2.0f, screenHeight() - 110.0f
        )));
        this.addChild(title);

        // Add Options Layer
        MenuButtons menuLayer = new MenuButtons(soundStatus);
        this.addChild(menuLayer);
        //*********************************************//
    }

    public CCScene scene() {
        CCScene scene = CCScene.node();
        scene.addChild(this);

        return scene;
    }
}
