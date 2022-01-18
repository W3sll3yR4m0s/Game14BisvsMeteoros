package dev.medi.code.bis.game.control;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.game.scenes.GameScene;

public class GameButtons extends CCLayer implements ButtonDelegate {

    private Button leftControl;
    private Button rightControl;
    private Button shootButton;
    private Button pauseButton;

    private GameScene delegate;

    public static GameButtons gameButtons() {
        return new GameButtons();
    }

    // Class Constructor
    public GameButtons() {

        // Enable Touch [Habilita o toque na tela]
        this.setIsTouchEnabled(true);

        // Create Buttons
        this.leftControl  =  new Button(Assets.LEFTCONTROL);
        this.rightControl =  new Button(Assets.RIGHTCONTROL);
        this.shootButton  =  new Button(Assets.SHOOTBUTTON);
        this.pauseButton  =  new Button(Assets.PAUSE);

        // Set Buttons Delegates [Configura as Delegações]
        this.leftControl.setDelegate(this);
        this.rightControl.setDelegate(this);
        this.shootButton.setDelegate(this);
        this.pauseButton.setDelegate(this);

        // Set Position [Configura Posições]
        setButtonspPosition();

        // Add Buttons to Screen
//        addChild(leftControl);
//        addChild(rightControl);
        addChild(shootButton);
        addChild(pauseButton);

    }

    private void setButtonspPosition() {

        // Buttons Position [Posição dos Botões]
//        leftControl.setPosition(screenResolution(CGPoint.ccp(40, 40)));
//        rightControl.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 40, 40)));
        shootButton.setPosition(screenResolution(
                CGPoint.ccp(screenWidth() - 40, 40)
        )); // x: screenWidth() - 100 with Buttons

        pauseButton.setPosition(screenResolution(CGPoint.ccp(40, screenHeight() - 30)));

    }

    @Override
    public void buttonClicked(Button sender) {

        if (sender.equals(this.leftControl)) {
            System.out.println("Button clicked: Left"); // Checker
            this.delegate.moveLeft();
        }

        if (sender.equals(this.rightControl)) {
            System.out.println("Button clicked: Right"); // Checker
            this.delegate.moveRight();
        }

        if (sender.equals(this.shootButton)) {
            System.out.println("Button clicked: Shooting!"); // Checker
            this.delegate.shoot();
        }

        if (sender.equals(this.pauseButton)) {
            System.out.println("Button clicked: Pause!!"); // Checker
            this.delegate.pauseGameAndShowLayer();
        }

    }

    public void setDelegate(GameScene gameScene) {
        this.delegate = gameScene;
    }
}
