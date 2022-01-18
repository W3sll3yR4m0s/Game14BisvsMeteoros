package dev.medi.code.bis.game.objects;

import static dev.medi.code.bis.config.DeviceSettings.screenHeight;
import static dev.medi.code.bis.config.DeviceSettings.screenResolution;
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

import java.util.Random;

import dev.medi.code.bis.R;
import dev.medi.code.bis.config.Runner;
import dev.medi.code.bis.game.interfaces.MeteorsEngineDelegate;

public class Meteor extends CCSprite {

    //***** Game General Settings *****//
//    private boolean enableCreateMeteor = true;
    //*********************************//

    private MeteorsEngineDelegate delegate;
    private float x, y;
    private float temporaryY;

    // Game Status Data
    private int destroyedMeteors = 0;

    // Standard Class Constructor
    public Meteor() {

    }

//    // Second Class Constructor
//    public Meteor(String image) {
//
//    }

    // First Class Constructor
    public Meteor(String image) {
        super(image);
        x = new Random().nextInt(Math.round(screenWidth()));
        y = screenHeight();
    }

    public void start(int destroyedMeteors) {

        this.destroyedMeteors = destroyedMeteors;
        this.schedule("update");
    }

    public void update(float dt) {

        // Pause
        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {

//            //########## MELHORAR ESSE CÓDIGO!! ##########//
//            switch (this.destroyedMeteors) {
//                case 10: {
//                    y -= 1;
//                    break;
//                }
//                case 20: {
//                    y -= 1.2;
//                    break;
//                }
//                case 30: {
//                    y -= 1.4;
//                    break;
//                }
//                case 40: {
//                    y -= 1.6;
//                    break;
//                }
//                case 50: {
//                    y -= 1.8;
//                    break;
//                }
//                case 60: {
//                    y -= 2.0;
//                    break;
//                }
//                case 70: {
//                    y -= 2.2;
//                    break;
//                }
//                case 80: {
//                    y -= 2.4;
//                    break;
//                }
//                case 90: {
//                    y -= 2.6;
//                    break;
//                }
//                default: break;
//            }
//            //############################################//
//
//            //########## MELHORAR ESSE CÓDIGO!! ##########//
//            if (this.destroyedMeteors < 10) {
//                y -= 0.5;
//            } else if (this.destroyedMeteors < 20) {
//                y -= 0.7;
//            } else if (this.destroyedMeteors < 30) {
//                y -= 0.9;
//            } else if (this.destroyedMeteors < 40) {
//                y -= 1.1;
//            } else if (this.destroyedMeteors < 50) {
//                y -= 1.3;
//            } else if (this.destroyedMeteors < 60) {
//                y -= 1.5;
//            } else if (this.destroyedMeteors < 70) {
//                y -= 1.6;
//            } else if (this.destroyedMeteors < 80) {
//                y -= 1.7;
//            } else if (this.destroyedMeteors < 90) {
//                y -= 1.8;
//            } else if (this.destroyedMeteors < 100) {
//                y -= 2.0;
//            }
//            //############################################//

//            //############################################//
//            Score score = new Score();
//
//            if (score.getScore() % 10 != 0) {
//                for (int i = 0; i < score.getScore() ; i += 10) {
//                    temporaryY = ( 0.5f + (0.2f*(i / 10f)) );
//                }
//
//                System.out.println("VERTICAL ACCELERATION: " + temporaryY); // Check
//                y -= temporaryY;
//            } else if (score.getScore() % 10 == 0) {
//                for (int i = 0; i <= score.getScore() ; i += 10) {
//                    temporaryY = (0.8f + 0.2f*(i / 10f));
//                }
//
//                System.out.println("VERTICAL ACCELERATION: " + temporaryY); // Check
//                y -= temporaryY;
//            }
//
//            score.removeFromParentAndCleanup(true);
//            //############################################//

            // Calls the function responsible for assigning the acceleration to the meteor
            y -= setAccelerationRate();

            if (x >= 0 && x <= 30) {
//                this.setPosition(screenResolution(CGPoint.ccp(x + 30, y)));
                x = 31; // O número explícito tem a ver com a largura da imagem.
            } else if (x <= screenWidth() && x >= screenWidth() - 30) {
//                this.setPosition(screenResolution(CGPoint.ccp(x - 30, y)));
                x = screenWidth() - 29; // O número explícito tem a ver com a largura da imagem.
            }

            this.setPosition(screenResolution(CGPoint.ccp(x,y)));
        }

    }

    public void setDelegate(MeteorsEngineDelegate delegate) {
        this.delegate = delegate;
    }

    public void shooted() {

        // Play explosion
        SoundEngine.sharedEngine().playEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.bang
        );

        // Remove from Game Array
        this.delegate.removeMeteor(this);

        // Stop Shoot [Para de ficar chamando o "update"]
        this.unschedule("update");

        // Pop Actions
        float dt = 0.2f;
        CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
        CCFadeOut a2 = CCFadeOut.action(dt);
        CCSpawn s1 = CCSpawn.actions(a1, a2);

        // Call RemoveMe
        CCCallFunc c1 = CCCallFunc.action(this, "removeMe");

        // Run actions!
        this.runAction(CCSequence.actions(s1, c1));

    }

    // Assign the Acceleration Rate [Atribui a taxa de aceleração]
    private float setAccelerationRate() {

        if (this.destroyedMeteors % 10 != 0 || this.destroyedMeteors == 0) {
            for (int i = 0; i <= this.destroyedMeteors ; i += 10) {
                temporaryY = ( 0.5f + (0.2f*(i / 10f)) );
            }

            System.out.println("VERTICAL ACCELERATION: " + temporaryY); // Check
        } else if (this.destroyedMeteors % 10 == 0) {
            for (int i = 0; i <= this.destroyedMeteors ; i += 10) {
                temporaryY = (0.8f + 0.2f*(i / 10f));
            }

            System.out.println("VERTICAL ACCELERATION: " + temporaryY); // Check
        }

        return temporaryY;
    }

//    // Enables or Disables the Create Meteor [Habilita ou Desabilita a criação de Meteoro]
//    public void setEnableCreateMeteor(boolean enableCreateMeteor) {
//        this.enableCreateMeteor = enableCreateMeteor;
//    }

    public void removeMe() {
        this.removeFromParentAndCleanup(true);
    }

}
