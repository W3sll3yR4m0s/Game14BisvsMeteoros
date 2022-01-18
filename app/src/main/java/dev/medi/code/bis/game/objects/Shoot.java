package dev.medi.code.bis.game.objects;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import dev.medi.code.bis.R;
import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.config.Runner;
import dev.medi.code.bis.game.interfaces.ShootEngineDelegate;

import static dev.medi.code.bis.config.DeviceSettings.screenResolution;

public class Shoot extends CCSprite {

	//***** Game General Settings *****//
//	private boolean enableCreateShoot = true;
	//*********************************//

	private ShootEngineDelegate delegate;

	// Game Status Data
	private int destroyedMeteors = 0;

	float positionX, positionY;
	float temporaryY;

	// Standard Class Constructor
	private Shoot() {

	}

	// Class Constructor
	public Shoot(float positionX, float positionY, int destroyedMeteors) {

	    super(Assets.SHOOT);

	    this.positionX = positionX;
	    this.positionY = positionY;
		this.destroyedMeteors = destroyedMeteors;

	    setPosition(this.positionX, this.positionY);

	    this.schedule("update");

    }

    public void update(float dt) {
		if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {
//			positionY += 2;

			positionY += setAccelerationRate();

			this.setPosition(screenResolution(CGPoint.ccp(positionX, positionY)));
		}
    }

    public void setDelegate(ShootEngineDelegate delegate) {

	    this.delegate = delegate;
    }

    public void start() {
	    System.out.println("Shoot moving..."); // Checker

		// Play Sound
		SoundEngine.sharedEngine().playEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.shoot
		);

    }

    // Hit
	public void explode() {

		// Remove from Game Array
		this.delegate.removeShoot(this);

		// Stop Shoot [Para o agendamento]
		this.unschedule("update");

		// Pop Actions [Cria efeitos]
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);

		// Call RemoveMe [Função a ser executada após efeito]
		CCCallFunc c1 = CCCallFunc.action(this, "removeMe");

		// Run actions!
		this.runAction(CCSequence.actions(s1, c1));

	}

	private float setAccelerationRate() {

//		// Se o número for "redondo" e uma dezena ímpar permitirá a entrada na condição
//		if ( (this.destroyedMeteors % 10) == 0 && (this.destroyedMeteors / 10) % 2 != 0 ) {
//
//			for (int i = 0; i == this.destroyedMeteors ; i += 10) {
//				temporaryY = ( 1f + (0.2f*(i / 10f)) );
//			}
//		} else if (this.destroyedMeteors < 10 && temporaryY != 1f) {
//			temporaryY = 1f;
//		}

		//########## MELHORAR ESSE CÓDIGO!! ##########//
		if (this.destroyedMeteors < 10) {
			temporaryY = 1f;
		} else if (this.destroyedMeteors < 30) {
			temporaryY = 1.2f;
		} else if (this.destroyedMeteors < 50) {
			temporaryY = 1.4f;
		} else if (this.destroyedMeteors < 70) {
			temporaryY = 1.6f;
		} else if (this.destroyedMeteors < 90) {
			temporaryY = 1.8f;
		} else if (this.destroyedMeteors < 100) {
			temporaryY = 2.0f;
		}
		//############################################//

		return temporaryY;
	}

//	// Enables or Disables the Create Shoot [Habilita ou Desabilita a criação de Shoot]
//	public void setEnableCreateShoot(boolean enableCreateShoot) {
//		this.enableCreateShoot = enableCreateShoot;
//	}

	public void removeMe() {
		this.removeFromParentAndCleanup(true);
	}
	
}
