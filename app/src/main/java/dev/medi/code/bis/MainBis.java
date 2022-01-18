package dev.medi.code.bis;

import android.annotation.SuppressLint;
//import android.app.ActionBar;
//import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
//import android.os.Build;
import android.os.Bundle;
//import android.view.View;
import android.view.Window;
import android.view.WindowManager;

//import androidx.annotation.RequiresApi;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import dev.medi.code.bis.config.ConfigurationConstants;
import dev.medi.code.bis.config.DeviceSettings;
import dev.medi.code.bis.game.scenes.TitleScreen;

public class MainBis extends Activity {

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // // Portrait Orientation [Definindo Orientação como "Orientação Vertical"]
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

//        uiOptionsMaintains(); // Resolve parcialmente, a ActionBar reaparece ao toque na tela.
//        getActionBar().hide(); // Não resolve, App não inicia corretamente.

//        setContentView(R.layout.activity_main);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // View [Configura Tela]
        CCGLSurfaceView glSurfaceView = new CCGLSurfaceView(this);
//        glSurfaceView = (CCGLSurfaceView) getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        glSurfaceView.setSystemUiVisibility(uiOptions);
        setContentView(glSurfaceView);

//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

//        getActionBar().hide();

        CCDirector.sharedDirector().attachInView(glSurfaceView);

        // Sensor Manager [Configura Sensor]
        configSensorManager();

        // Configure CCDirector [Configura CCDirector]
        CCDirector.sharedDirector().setScreenSize(320, 480);
//        CCDirector.sharedDirector().setDeviceOrientation(CCDirector.kCCDeviceOrientationPortrait);

        // Starts title screen [Abre tela principal]
        CCScene scene = new TitleScreen(ConfigurationConstants.soundON).scene();
        CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        uiOptionsMaintains();
//        getActionBar().hide(); // Não resolve, App não inicia corretamente.
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void configSensorManager() {
        // Create Sensor Manager [Cria gerenciador de sensor]
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // [Passa o gerenciador de sensor para a Classe DeviceSettings]
        DeviceSettings.setSensorManager(sensorManager);
    }

//    private void uiOptionsMaintains() {
//
//        // Mantém as opções de interface de usuário
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//
//    }

}
