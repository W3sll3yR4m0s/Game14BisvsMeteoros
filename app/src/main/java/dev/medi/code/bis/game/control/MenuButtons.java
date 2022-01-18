package dev.medi.code.bis.game.control;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
//import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import dev.medi.code.bis.R;
import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.config.ConfigurationConstants;
import dev.medi.code.bis.game.scenes.GameScene;
//import dev.medi.code.bis.game.scenes.TitleScreen;

public class MenuButtons extends CCLayer implements ButtonDelegate {
	
	private Button playButton;
	private Button highscoredButton;
	private Button helpButton;
	private Button exitButton;
	private Button soundButton;

//	private boolean soundEnable;
//	private boolean effectsEnable;

    // Settings
	private boolean soundStatus;
	private boolean effectsStatus;

	// Standard Class Constructor
    public MenuButtons() {

    }

	// Class Constructor
	public MenuButtons(boolean soundStatus) {

        // Assigning Parameters Received
        this.soundStatus = soundStatus;

	    // Enable Touch
	    this.setIsTouchEnabled(true);

	    // Create Buttons
	    this.playButton       = new Button(Assets.PLAY);
	    this.highscoredButton = new Button(Assets.HIGHSCORE);
	    this.helpButton       = new Button(Assets.HELP);
	    this.exitButton       = new Button(Assets.EXIT);

	    if (this.soundStatus == ConfigurationConstants.soundON) {
            this.soundButton  = new Button(Assets.SOUND); // Icon: Sound Enable
        } else if (this.soundStatus == ConfigurationConstants.soundOFF) {
	        this.soundButton  = new Button(Assets.SOUNDOFF); // Icon: Sound Disable
        }

	    // Set Buttons Delegates
        this.playButton.setDelegate(this);
        this.highscoredButton.setDelegate(this);
        this.helpButton.setDelegate(this);
        this.exitButton.setDelegate(this);
        this.soundButton.setDelegate(this);

	    // Set Position - Coloca botões na posição correta
        setButtonspPosition();

        // Add Buttons to Screen
        addChild(playButton);
        addChild(highscoredButton);
        addChild(helpButton);
        addChild(exitButton);
        addChild(soundButton);
    }

    private void setButtonspPosition() {

	    // Buttons Positions
        playButton.setPosition(
                screenResolution(
                        CGPoint.ccp(
                                screenWidth() / 2, screenHeight() - 230
                        )));

        highscoredButton.setPosition(
                screenResolution(
                        CGPoint.ccp(
                                screenWidth() / 2, screenHeight() - 280
                        )));

        helpButton.setPosition(
                screenResolution(
                        CGPoint.ccp(
                                screenWidth() / 2, screenHeight() - 330
        )));

        exitButton.setPosition(
                screenResolution(
                        CGPoint.ccp(
                                screenWidth() / 2, screenHeight() - 380
        )));

        soundButton.setPosition(
                screenResolution(
                        CGPoint.ccp(
                                (screenWidth() / 2) - 100, screenHeight() - 430
        )));

    }

    @Override
    public void buttonClicked(Button sender) {

	    if (sender.equals(this.playButton)) {
            System.out.println("Button clicked: Play");
            CCDirector.sharedDirector().replaceScene(
                    CCFadeTransition.transition(
                            0.8f, GameScene.createGame(this.soundStatus)
                    ));
        }

	    if (sender.equals(this.highscoredButton)) {
	        System.out.println("Button clicked: Highscore");
        }

	    if (sender.equals(this.helpButton)) {
	        System.out.println("Button clicked: Help");
//	        System.exit(0); // Encerra o App [Está aqui temporariamente]
        }

	    if (sender.equals(this.exitButton)) {
	        System.out.println("Button clicked: Exit");
	        System.exit(0); // Encerra o App
        }

	    if (sender.equals(this.soundButton)) {
	        System.out.println("Button clicked: Sound");
	        // Stop Sound
	        if (this.soundStatus == ConfigurationConstants.soundON) {
//                SoundEngine.sharedEngine().pauseSound();
//                SoundEngine.purgeSharedEngine();
                System.out.println("Button clicked: Sound OFF");

//                this.soundEnable = false;
                replaceSoundButtonAndSoundStatus();

//                // Serve para substituir a Title Screen com a finalidade de trocar o Sound Icon
//                // Replace title screen [Substitui tela principal]
//                CCScene scene = new TitleScreen().scene();
//                CCDirector.sharedDirector().runWithScene(scene);

            }
	        // Play Sound
	        else if (this.soundStatus == ConfigurationConstants.soundOFF) {
//                SoundEngine.sharedEngine().playSound(
//                        CCDirector.sharedDirector().getActivity(), R.raw.music, true
//                );
                System.out.println("Button clicked: Sound ON");

//                this.soundEnable = true;
                replaceSoundButtonAndSoundStatus();

//                // Serve para substituir a Title Screen com a finalidade de trocar o Sound Icon
//                // Replace title screen [Substitui tela principal]
//                CCScene scene = new TitleScreen().scene();
//                CCDirector.sharedDirector().runWithScene(scene);
            }
        }
    }

    private void replaceSoundButtonAndSoundStatus() {

        this.setIsTouchEnabled(true);

	    if (this.soundStatus == ConfigurationConstants.soundOFF) {
	        // Remove the previous Sound Button
            this.soundButton.removeFromParentAndCleanup(true);

            // Create Button
            this.soundButton = new Button(Assets.SOUND);

            // Set Button Delegate
            this.soundButton.setDelegate(this);

            // Set Button Position
            setButtonspPosition();

            // Add Button to Screen
            addChild(this.soundButton);

            this.soundStatus = ConfigurationConstants.soundON;
        } else if (this.soundStatus == ConfigurationConstants.soundON) {
            // Remove the previous Sound Button
            this.soundButton.removeFromParentAndCleanup(true);

            // Create Button
            this.soundButton = new Button(Assets.SOUNDOFF);

            // Set Button Delegate
            this.soundButton.setDelegate(this);

            // Set Button Position
            setButtonspPosition();

            // Add Button to Screen
            addChild(this.soundButton);

            this.soundStatus = ConfigurationConstants.soundOFF;
        }
    }
}
