package dev.medi.code.bis.game.objects;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
//import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
import static dev.medi.code.bis.config.DeviceSettings.screenWidth;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

//import java.security.PublicKey;

import dev.medi.code.bis.R;
import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.config.ConfigurationConstants;
import dev.medi.code.bis.config.Runner;
import dev.medi.code.bis.game.calibrate.Accelerometer;
import dev.medi.code.bis.game.calibrate.AccelerometerDelegate;
//import dev.medi.code.bis.game.interfaces.PlayerEngineDelegate;
import dev.medi.code.bis.game.interfaces.ShootEngineDelegate;
//import dev.medi.code.bis.game.scenes.GameOverScreen;

public class Player extends CCSprite implements AccelerometerDelegate {

    private static final double NOISE = 1;

    private ShootEngineDelegate delegate;
//    private PlayerEngineDelegate playerDelegate;

	float positionX = screenWidth() / 2;
	float positionY = 105;

	private Accelerometer accelerometer;

	private float currentAccelX;
	private float currentAccelY;

//	private int quantityAmmunition = 300; // [Inicializa por aqui temporariamente]

//    // Game Status Data
//    private int destroyedMeteors = 0;

//    // Standard Class Constructor
//    public Player() {
//
//    }

	// Class Constructor
	public Player() {
	    super(Assets.NAVE);
	    setPosition(positionX, positionY);
	    this.schedule("update");
    }

    public void shoot(int destroyedMeteors) {

        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {
            delegate.createShoot(new Shoot(positionX, positionY, destroyedMeteors));
        }
	}

    public void setDelegate(ShootEngineDelegate delegate) {
        this.delegate = delegate;
    }

//    public void setPlayerDelegate(PlayerEngineDelegate playerDelegate) {
//	    this.playerDelegate = playerDelegate;
//    }

    public void moveLeft() {
        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {

            if (positionX > 30) {
//			positionX -= 10;
                positionX -= screenWidth() / 30;
            }

            setPosition(positionX, positionY);
        }
	}

	public void moveRight() {
        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {

            if (positionX < screenWidth() - 30) {
//			positionX += 10;
                positionX += screenWidth() / 30;
            }
            setPosition(positionX, positionY);

        }
	}

	// Anima o Player quando o Meteoro colide com ele
	public void explode() {

//	    synchronized (this) {
            // Play explosion
            SoundEngine.sharedEngine().playEffect(
                    CCDirector.sharedDirector().getActivity(), R.raw.over);

            // Background music ends
            SoundEngine.sharedEngine().pauseSound();

            // // Remove from Game Array
//            this.playerDelegate.removePlayer(this);

            // Stop Shoot [Para o agendamento]
            this.unschedule("update");

            // Pop Actions [Cria efeitos]
            float dt = 0.2f;
            CCScaleBy a1 = CCScaleBy.action(dt, 2f);
            CCFadeOut a2 = CCFadeOut.action(dt);
            CCSpawn s1 = CCSpawn.actions(a1, a2);

             // Call RemoveMe [Função a ser executada após efeito]
            CCCallFunc c1 = CCCallFunc.action(this, "removeMe");

            // Run actions! [Roda os efeitos]
//            this.runAction(CCSequence.actions(s1));
            this.runAction(CCSequence.actions(s1, c1));
//        }

//        notify();
    }

    public void removeMe() {
	    this.removeFromParentAndCleanup(true);
    }

    public void catchAccelerometer() {
		Accelerometer.sharedAccelerometer().catchAccelerometer();
		this.accelerometer = Accelerometer.sharedAccelerometer();
		this.accelerometer.setDelegate(this);
	}

	@Override
	public void accelerometerDidAccelerate(float x, float y) {
        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {

            System.out.println("X: " + x);
            System.out.println("Y: " + y);

            // Read acceleration [Leitura da aceleração]
            this.currentAccelX = x;
            this.currentAccelY = y;

        }
	}

	public void update(float dt) {
        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {

            // [Fazer primeiro com todos os valores iguais a 0, depois colocar essa constante (NOISE)]
            if (this.currentAccelX < -NOISE) {
//			this.positionX++;
                if (this.positionX < screenWidth() - 30) {
                    this.positionX += 2;
                }
            }

            if (this.currentAccelX > NOISE) {
//			this.positionX--;
                if (this.positionX > 30) {
                    this.positionX -= 2;
                }
            }

            if (this.currentAccelY < -NOISE) {
//			this.positionY++;
                if (this.positionY < screenHeight() - 40) {
                    this.positionY += 2;
                }
            }

            if (this.currentAccelY > NOISE) {
//			this.positionY--;
                if (this.positionY > 75) {
                    this.positionY -= 2;
                }
            }

            // Update Player Position [Configura posição do avião]
            this.setPosition(CGPoint.ccp(this.positionX, this.positionY));

        }
	}

}
