package dev.medi.code.bis.game.scenes;

//import androidx.core.graphics.PathUtils;

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
import org.cocos2d.types.CGRect;

//import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dev.medi.code.bis.R;
import dev.medi.code.bis.config.Assets;
import dev.medi.code.bis.config.ConfigurationConstants;
import dev.medi.code.bis.config.Runner;
import dev.medi.code.bis.game.calibrate.Accelerometer;
import dev.medi.code.bis.game.control.GameButtons;
//import dev.medi.code.bis.game.control.MenuButtons;
import dev.medi.code.bis.game.engines.MeteorsEngine;
import dev.medi.code.bis.game.interfaces.MeteorsEngineDelegate;
import dev.medi.code.bis.game.interfaces.PauseDelegate;
import dev.medi.code.bis.game.interfaces.PlayerEngineDelegate;
import dev.medi.code.bis.game.interfaces.ShootEngineDelegate;
import dev.medi.code.bis.game.objects.Ammunition;
import dev.medi.code.bis.game.objects.Meteor;
import dev.medi.code.bis.game.objects.Player;
import dev.medi.code.bis.game.objects.Score;
import dev.medi.code.bis.game.objects.Shoot;
import dev.medi.code.bis.screens.PauseScreen;
import dev.medi.code.bis.screens.ScreenBackground;

public class GameScene extends CCLayer implements MeteorsEngineDelegate,
        ShootEngineDelegate, PlayerEngineDelegate, PauseDelegate {

    // Layers
    private CCLayer meteorsLayer;
    private CCLayer ammunitionLayer;
    private CCLayer scoreLayer;
    private CCLayer playerLayer;
    private CCLayer shootsLayer;
    private CCLayer layerTop;

    // Engines
    private MeteorsEngine meteorsEngine;

    // Arrays
    @SuppressWarnings("rawtypes")
    private ArrayList meteorsArray;
    @SuppressWarnings("rawtypes")
    private ArrayList playersArray;
    @SuppressWarnings("rawtypes")
    private ArrayList shootsArray;

    // Screens
    private PauseScreen pauseScreen;

    // Game Objects
    private Player player;
    private Score score;
    private Ammunition ammunition;
    private boolean autoCalibration;
    private boolean enableStartEngines = true;
	private ScreenBackground background;

	// Settings
    private boolean soundStatus;
    private boolean effectStatus;

    // Game Status Data
    private int destroyedMeteors = 0;
//    private int quantityMeteorsPassed = 0;
    private boolean quantityMeteorsPassed;
    private float coordinateYFromMeteorPassed;

    public static CCScene createGame(boolean soundStatus) {

        // Create Scene
        GameScene layer = new GameScene(soundStatus);
        CCScene scene = CCScene.node();
        scene.addChild(layer);

        return scene;
    }

    // Standard Class Constructor
    private GameScene() {

    }

    // Class Constructor
	private GameScene(boolean soundStatus) {

        //********** General Settings **********//
        // Enable Touch
        this.setIsTouchEnabled(true);

        // Update calibration variable [Atualiza variável calibração do acelerômetro]
        Accelerometer.sharedAccelerometer().updateCalibration(0);
        // Enables the Accelerometer [Habilita o Acelerômetro]
        Accelerometer.sharedAccelerometer().enableSensor(true);
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

        // Create Layers
        this.meteorsLayer = CCLayer.node();
        this.playerLayer = CCLayer.node();
        this.ammunitionLayer = CCLayer.node();
        this.scoreLayer = CCLayer.node();

        this.addGameObjects();

        this.shootsLayer = CCLayer.node();
        this.layerTop = CCLayer.node();

        // Add Layers
        this.addChild(this.meteorsLayer);
        this.addChild(this.playerLayer);
        this.addChild(this.shootsLayer);
        this.addChild(this.ammunitionLayer);
        this.addChild(this.scoreLayer);
        this.addChild(this.layerTop);

        GameButtons gameButtonsLayer = GameButtons.gameButtons();
        gameButtonsLayer.setDelegate(this);
        this.addChild(gameButtonsLayer);

//        this.setIsTouchEnabled(true);
//
//        // Update calibration variable [Atualiza variável calibração do acelerômetro]
//        Accelerometer.sharedAccelerometer().updateCalibration(0);
//        // Enables the Accelerometer [Habilita o Acelerômetro]
//        Accelerometer.sharedAccelerometer().enableSensor(true);

        // Adds Music to the Game
        if (this.soundStatus == ConfigurationConstants.soundON) {
            SoundEngine.sharedEngine().playSound(
                    CCDirector.sharedDirector().getActivity(), R.raw.music, true
            );
        }
//        else if (this.soundStatus == ConfigurationConstants.soundOFF) {
//            SoundEngine.purgeSharedEngine();
//        }

//        // Catch Accelerometer [Captura o acelerômetro]
//        player.catchAccelerometer();

        preloadCache();
        //*********************************************//
    }

    public void preloadCache() {
        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.shoot
        );

        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.bang
        );

        SoundEngine.sharedEngine().preloadEffect(
                CCDirector.sharedDirector().getActivity(), R.raw.over
        );
    }

    private void addGameObjects() {

        // Enemy
        this.meteorsArray = new ArrayList();
        this.meteorsEngine = new MeteorsEngine(this.destroyedMeteors);

        // Player
        this.player = new Player();
        this.playerLayer.addChild(this.player);

        // Start Game
        this.playersArray = new ArrayList();
        this.playersArray.add(this.player);

        // Shoots
        this.shootsArray = new ArrayList();
        this.player.setDelegate(this);

        // Score [Placar]
        this.score = new Score();
        this.score.setDelegate(this);
        this.scoreLayer.addChild(this.score);

        // Ammunition [Munição]
        this.ammunition = new Ammunition();
        this.ammunition.setDelegate(this);
        this.ammunitionLayer.addChild(this.ammunition);
    }

    public void startGame() {

        // Set Game Status
        // PAUSE
        Runner.check().setGamePlaying(true);
        Runner.check().setIsGamePaused(false);

        // Catch Accelerometer [Captura o acelerômetro]
        player.catchAccelerometer();

        // pause
        SoundEngine.sharedEngine().setEffectsVolume(1f);
        SoundEngine.sharedEngine().setSoundVolume(1f);

        // StartGame
        this.schedule("checkEvents");

        if (enableStartEngines) {
            this.startEngines();
        }

    }

    @Override
    public void onEnter() {
        super.onEnter();

        // Start Game when transition did finish [Configura o Status do Jogo]
        if (!this.autoCalibration) {
            this.startGame();
        }

    }

    // StartGame
    // checkHits [Inicialmente nomeado]
    public void checkEvents(float dt) {

        // Check Meteor Hits
        this.checkRadiusHitsOfArray(
                this.meteorsArray, this.shootsArray, this, "meteoroHit"
        );

        // Check Player Hit
        this.checkRadiusHitsOfArray(
                this.meteorsArray, this.playersArray, this, "playerHit"
        );

//        // Check how many points to decrease
//        meteorPassed(checkPointsToDecrease());
        checkIfMeteorHasPassed(this.meteorsArray);

    }

    private void checkIfMeteorHasPassed(List<? extends CCSprite> array1) {

        for (int i = 0; i < array1.size(); i++) {
            // Checks y coordinate of each object in the array1
            if (array1.get(i).getPosition().y < -30) {
                meteorPassed(array1.get(i));
            }
        }
    }

    private boolean checkRadiusHitsOfArray(
            List<? extends CCSprite> array1,
            List<? extends CCSprite> array2,
            GameScene gameScene, String hit) {

        boolean result = false;

        for (int i = 0; i < array1.size(); i++) {
            // Get Object from First Array
            CGRect rect1 = getBoarders(array1.get(i));
//            coordinateYFromMeteorPassed = array1.get(i).getPosition().y;

            for (int j = 0; j < array2.size(); j++) {
                // Get Object from Second Array
                CGRect rect2 = getBoarders(array2.get(j));

                // Check Hit [Verifica Colisão]
                if (CGRect.intersects(rect1, rect2)) {
                    System.out.println("Collision Detected: " + hit);

                    result = true;

                    Method method;
                    try {
                        method = GameScene.class.getMethod(
                                hit, CCSprite.class, CCSprite.class
                        );

                        method.invoke(
                                gameScene, array1.get(i), array2.get(j)
                        );

                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    public CGRect getBoarders(CCSprite object) {
        CGRect rect = object.getBoundingBox();
        CGPoint GLpoint = rect.origin;
//        CGRect GLrect = CGRect.make(GLpoint.x, GLpoint.y, rect.size.width, rect.size.height);

        return CGRect.make(GLpoint.x, GLpoint.y, rect.size.width, rect.size.height);
    }

    private void startEngines() {
        this.addChild(this.meteorsEngine);
        this.meteorsEngine.setDelegate(this);
    }

    @Override
    public void createMeteor(Meteor meteor) {

        destroyedMeteors = score.getScore();

//        meteor.setDelegate(this);
        this.meteorsLayer.addChild(meteor);
        meteor.setDelegate(this);
        meteor.start(destroyedMeteors);
        this.meteorsArray.add(meteor);

    }

    public boolean shoot() {

        if (this.ammunition.getQuantity() > 0) {

            player.shoot(this.destroyedMeteors);

            this.ammunition.decrease();

            return true;
        }

        return false;
    }

    @Override
    public void createShoot(Shoot shoot) {

        this.shootsLayer.addChild(shoot);
        shoot.setDelegate(this);
        shoot.start();
        this.shootsArray.add(shoot);

    }

    public void  moveLeft() {
        player.moveLeft();
    }

    public void moveRight() {
        player.moveRight();
    }

//    // Chama o método responsável por reduzir o placar
//    public void meteorPassed(int quantityMeteorsPassed) {
//
//        for (int i = 1; i <= quantityMeteorsPassed; i++) {
//            this.score.decrease();
//        }
//    }

    // Chama os métodos responsáveis por animar os objetos após a colisão entre eles
    public void meteoroHit(CCSprite meteor, CCSprite shoot) {
        ((Meteor) meteor).shooted();
        ((Shoot) shoot).explode();

        this.score.increase();
    }

    public void meteorPassed(CCSprite meteor) {
        ((Meteor) meteor).shooted();

        this.score.decrease();
    }

    // Chama os métodos responsáveis por animar os objetos após a colisão entre eles
    public void playerHit (CCSprite meteor, CCSprite player) {
        this.setIsTouchEnabled(false);

        ((Meteor) meteor).shooted();
        ((Player) player).explode();
//        this.player.explode();

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }

//        synchronized (this.player) {
//            try{
//                System.out.println("Aguardando (player) completar...");
////                ((Player) player).explode();
//                this.player.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            CCDirector.sharedDirector().replaceScene(new GameOverScreen().scene());
//        }

        // Recarrega a cena trocando para Game Over Screen
        startGameOverScreen();
    }

    @Override
    public void removeMeteor(Meteor meteor) {
        this.meteorsArray.remove(meteor);
    }

    @Override
    public void removeShoot(Shoot shoot) {
        this.shootsArray.remove(shoot);
    }

    @Override
    public void removePlayer(Player player) {
        this.playersArray.remove(player);
    }

    // ========= //
    // = PAUSE = //
    // ========= //
    @Override
    public void pauseGameAndShowLayer() {

        if (Runner.check().isIsGamePlaying() && !Runner.check().isIsGamePaused()) {
            this.pauseGame();
        }

        if (Runner.check().isIsGamePaused() && Runner.check().isIsGamePlaying()
        && this.pauseScreen == null) {

            this.pauseScreen = new PauseScreen();
            this.layerTop.addChild(this.pauseScreen);
            this.pauseScreen.setDelegate(this);
        }

    }

    private void pauseGame() {
        if (!Runner.check().isIsGamePaused() && Runner.check().isIsGamePlaying()) {
            Runner.setIsGamePaused(true);
//            this.autoCalibration = false;
//            this.enableStartEngines = false;
        }
    }

    @Override
    public void resumeGame() {
        if (Runner.check().isIsGamePaused() || !Runner.check().isIsGamePlaying()) {

            // Resume Game [Continua o Jogo]
            this.pauseScreen = null;
            Runner.setIsGamePaused(false);
//            this.autoCalibration = true;
//            this.enableStartEngines = true;
            this.setIsTouchEnabled(true);
        }
    }

    @Override
    public void quitGame() {
//        SoundEngine.sharedEngine().setEffectsVolume(0f);
//        SoundEngine.sharedEngine().setSoundVolume(0f);
        SoundEngine.sharedEngine().pauseSound();
        SoundEngine.purgeSharedEngine();

        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(0.5f, new TitleScreen(this.soundStatus).scene())
        );

//        Runner.check().setGamePlaying(false);

//        this.removeFromParentAndCleanup(true);
    }

    public void startFinalScreen() {
        SoundEngine.sharedEngine().pauseSound();
        SoundEngine.purgeSharedEngine();

        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(0.5f, new FinalScreen(this.soundStatus).scene())
        );

//        this.removeFromParentAndCleanup(true);
    }

    public void startGameOverScreen() {

        SoundEngine.sharedEngine().pauseSound();
        SoundEngine.purgeSharedEngine();

        // Recarrega a cena trocando para Game Over Screen
        CCDirector.sharedDirector().replaceScene(
                CCFadeTransition.transition(1.5f, new GameOverScreen(this.soundStatus).scene())
        );

//        this.removeFromParentAndCleanup(true);
    }

}
